package client;

import server.Server;

public class ClientMain {
    public static void main(String[] args) {
        String serverUrl = "http://localhost:8040";
        Server server = new Server();
        var port = server.run(0);
        if(args.length == 1){
            serverUrl = args[0];
        }else{
            System.out.println("Started test HTTP server on " + port);
            serverUrl = "http://localhost:" + port;
        }
        new Repl(serverUrl).run();
        server.stop();
    }
}
//start repl