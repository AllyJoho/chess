package client;

public record EvalResponse(String message, int status, String authToken, int gameId, String user) {
}
