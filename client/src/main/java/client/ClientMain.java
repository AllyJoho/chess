package client;

public class ClientMain {
    public static void main(String[] args) {
        String serverUrl = "http://localhost:8081";
        if(args.length == 1){
            serverUrl = args[0];
        }
        try {
            new Repl(serverUrl).run();
        } catch (Exception e) {
            System.out.println("Something went wrong with websocket probably");
        }
    }
}
//start repl