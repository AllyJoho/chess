package client;

import server.ServerFacade;

public class ClientMain {
    public static void main(String[] args) {
        String serverUrl = "http://localhost:8040";
        if(args.length == 1){
            serverUrl = args[0];
//        }else{
//            Server server = new Server();
//            var port = server.run(0);
//            System.out.println("Started test HTTP server on " + port);
//            serverUrl = "http://localhost:" + port;
        }
        new Repl(serverUrl).run();
    }
}
//start repl