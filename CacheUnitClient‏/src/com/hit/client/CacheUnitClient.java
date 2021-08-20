package com.hit.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class CacheUnitClient {


    public CacheUnitClient() {
    }

    public String send(String request) {
        String responseFromServer = "No Response Yet";
        Socket socket = null;
        try {
            socket = new Socket("localhost", 12345);
            try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                try (Scanner sc = new Scanner(new InputStreamReader(socket.getInputStream()))) {
                    pw.println(request);
                    pw.flush();
                    responseFromServer = sc.nextLine();
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseFromServer;
    }

}
