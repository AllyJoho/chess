package handler;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class ConnectionManager {
    public final ConcurrentHashMap<Integer, Set<Session>> connections = new ConcurrentHashMap<>();

    public void addPlayer(Session session, int gameID) {
        connections.computeIfAbsent(gameID, k -> new CopyOnWriteArraySet<>())
                .add(session);
    }

    public void removePlayer(int gameID, Session session) {
        Set<Session> players = connections.get(gameID);
        if (players != null) {
            players.remove(session);
        }
    }

    public Set<Session> getPlayers(int gameID) {
        return connections.getOrDefault(gameID, Collections.emptySet());
    }

    public void broadcast(int gameID, Session excludeSession, ServerMessage notification) {
        String msg = new Gson().toJson(notification);
        Set<Session> players = connections.get(gameID);

        if (players != null) {
            for (Session s : players) {
                if (s.isOpen() && !s.equals(excludeSession)) {
                    try {
                        s.getRemote().sendString(msg);
                    } catch (IOException e) {
                        System.out.println("Error: websocket");
                    }
                }
            }
        }
    }
}