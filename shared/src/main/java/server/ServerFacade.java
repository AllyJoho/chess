package server;

import com.google.gson.Gson;
//import exception.ResponseException;
import request.*;
import result.*;

import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;
    private String authToken = null;


    public ServerFacade(String url) {
        serverUrl = url;
    }

    public RegisterResult register(RegisterRequest request) throws Exception {
        HttpRequest httpRequest = buildRequest("POST", "/user", request);
        HttpResponse<String> response = sendRequest(httpRequest);
        RegisterResult result = handleResponse(response, RegisterResult.class);
        if (result != null) {
            this.authToken = result.authToken();
        }
        return result;
    }

    public LoginResult login(LoginRequest request) throws Exception {
        HttpRequest httpRequest = buildRequest("POST", "/session", request);
        HttpResponse<String> response = sendRequest(httpRequest);
        LoginResult result = handleResponse(response, LoginResult.class);
        if (result != null) {
            this.authToken = result.authToken();
        }
        return result;
    }

    public void logout(LogoutRequest request) throws Exception {
        HttpRequest httpRequest = buildRequest("DELETE", "/session", request);
        sendRequest(httpRequest);
        this.authToken = null;
    }

    public ListGameResult listGames() throws Exception {
        HttpRequest httpRequest = buildRequest("GET", "/game", null);
        HttpResponse<String> response = sendRequest(httpRequest);
        return handleResponse(response, ListGameResult.class);
    }

    public CreateGameResult createGame(CreateGameRequest request) throws Exception {
        HttpRequest httpRequest = buildRequest("POST", "/game", request);
        HttpResponse<String> response = sendRequest(httpRequest);
        return handleResponse(response, CreateGameResult.class);
    }

    public void joinGame(JoinGameRequest request) throws Exception {
        HttpRequest httpRequest = buildRequest("PUT", "/game", request);
        sendRequest(httpRequest);
    }

    public void clear() throws Exception {
        HttpRequest httpRequest = buildRequest("DELETE", "/db", null);
        sendRequest(httpRequest);
        authToken = null;
    }

    private HttpRequest buildRequest(String method, String path, Object body) {
        HttpRequest.Builder request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (authToken != null) {
            request.setHeader("Authorization", authToken);
        }
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

    private HttpResponse<String> sendRequest(HttpRequest request) throws Exception {
        try {
            return client.send(request, BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws Exception {
        int status = response.statusCode();
        if (!isSuccessful(status)) {
            String body = response.body();
            if (body != null) {
                HashMap map = new Gson().fromJson(body, HashMap.class);
//                String status = map.get("status").toString();
                String message = map.get("message").toString();
                throw new Exception("Error: " + status + ": " + message);
            }
            throw new Exception("other failure");
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