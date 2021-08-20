package main.java.com.mbj.server;

import main.java.com.mbj.util.CLI;

public class CacheUnitServerDriver {
    public CacheUnitServerDriver() {}
    public static void main(String[] args) {
        CLI cli = new CLI(System.in, System.out);
        Server server = new Server();
        cli.addPropertyChangeListener(server);
        new Thread(cli).start();
    }
}
