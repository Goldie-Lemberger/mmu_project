package main.java.com.mbj.server;

import main.java.com.mbj.services.CacheUnitController;

import java.beans.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.EventListener;
import java.util.concurrent.*;

public class Server implements PropertyChangeListener, Runnable, EventListener {
    private ServerSocket server;
    private Executor executor;
    private CacheUnitController<String>controller;
    private boolean power; //On/Off

    public Server() {
        server = null;
        executor = null;
        controller =  new CacheUnitController<String>();
        power = false;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(12345);//port 12345
            executor = Executors.newFixedThreadPool(3);
            while(power){
                Socket socket = server.accept();
                executor.execute(new HandleRequest<String>(socket,controller));
            }
        }catch(SocketException e) { }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(server != null)

                    server.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String action =(String) evt.getNewValue();

        switch(action) {
            case "start":
                if(!power) {
                    power = true;
                    new Thread(this).start();
                    break;
                }
                else
                    System.out.println("Server is already ON\n");
                break;
            case "stop":
                if(!power)
                    System.out.println("Server is already OFF\n");
                else {
                    try {
                        power=false;
                        controller.saveCacheToIDao();
                        server.close();

                    }catch(IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                System.out.println("Not a valid command");
                break;
        }
    }
}
