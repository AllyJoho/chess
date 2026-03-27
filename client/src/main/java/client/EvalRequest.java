package client;

public record EvalRequest(String line, String authToken, int gameId, String user) {
}
