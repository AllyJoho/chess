package server;

import com.google.gson.Gson;
import exception.ResponseException;
import request.*;
import result.*;

import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public RegisterResult register(RegisterRequest request) throws ResponseException {
        HttpRequest httpRequest = buildRequest("POST", "/user", request);
        HttpResponse<String> response = sendRequest(httpRequest);
        return handleResponse(response, RegisterResult.class);
    }

    public LoginResult login(LoginRequest request) throws ResponseException {
        HttpRequest httpRequest = buildRequest("POST", "/session", request);
        HttpResponse<String> response = sendRequest(httpRequest);
        return handleResponse(response, LoginResult.class);
    }

    public void logout(LogoutRequest request) throws ResponseException {
        HttpRequest httpRequest = buildRequest("DELETE", "/session", request);
        sendRequest(httpRequest);
    }

    public ListGameResult listGames() throws ResponseException {
        HttpRequest httpRequest = buildRequest("GET", "/game", null);
        HttpResponse<String> response = sendRequest(httpRequest);
        return handleResponse(response, ListGameResult.class);
    }

    public CreateGameResult createGame(CreateGameRequest request) throws ResponseException {
        HttpRequest httpRequest = buildRequest("POST", "/game", request);
        HttpResponse<String> response = sendRequest(httpRequest);
        return handleResponse(response, CreateGameResult.class);
    }

    public void joinGame(JoinGameRequest request) throws ResponseException {
        HttpRequest httpRequest = buildRequest("PUT", "/game", request);
        sendRequest(httpRequest);
    }

    public void clear() throws ResponseException {
        HttpRequest httpRequest = buildRequest("DELETE", "/db", null);
        sendRequest(httpRequest);
    }

    private HttpRequest buildRequest(String method, String path, Object body) {
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-Type", "application/json");
        }
        return request.build();
    }

    private BodyPublisher makeRequestBody(Object request) {
        if (request != null) {
            return BodyPublishers.ofString(new Gson().toJson(request));
        } else {
            return BodyPublishers.noBody();
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws ResponseException {
        try {
            return client.send(request, BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws ResponseException {
        int status = response.statusCode();
        if (!isSuccessful(status)) {
            String body = response.body();
            if (body != null) {
                throw ResponseException.fromJson(body);
            }
            throw new ResponseException(ResponseException.fromHttpStatusCode(status), "other failure: " + status);
        }
        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }
        return null;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
