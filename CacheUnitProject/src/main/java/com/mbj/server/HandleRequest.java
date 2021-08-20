package main.java.com.mbj.server;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.mbj.dm.DataModel;
import main.java.com.mbj.services.CacheUnitController;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class HandleRequest<T> implements Runnable {

    private final Socket socket;
    private final CacheUnitController<T> controller;

    private Map<String, String> header;

    public HandleRequest(Socket s, CacheUnitController<T> controller) {
        this.socket = s;
        this.controller = controller;
    }

    @Override
    public void run() {
        try (Scanner reader = new Scanner(new InputStreamReader(socket.getInputStream()))) {
            try (PrintWriter pw = new PrintWriter (new OutputStreamWriter(socket.getOutputStream()))) {
                String response="";
                String req = reader.nextLine();
                Type ref = new TypeToken<Request<DataModel<T>[]>>() {}.getType();
                Request<DataModel<T>[]> request = new Gson().fromJson(req, ref);
                header = request.getHeaders();
                boolean success = false;
                switch (header.get("action")) {

                    case "GET": {
                        try {
                            controller.get(request.getBody());
                            if (controller != null) {
                                success = true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    case "DELETE": {
                        try {
                            success = controller.delete(request.getBody());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    case "UPDATE": {
                        success = controller.update(request.getBody());
                        break;
                    }

                    case "SHOWSTATS":{
                        response = controller.getStats(); //handleReqest -> controller -> service
                        break;
                    }

                    default:
                        break;
                }

                if(!header.get("action").equals("SHOWSTATS"))
                    response = success ? "Succeeded": "Failed";
                pw.println(response);
                pw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
