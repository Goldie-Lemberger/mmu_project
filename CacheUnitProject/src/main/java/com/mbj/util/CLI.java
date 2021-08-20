package main.java.com.mbj.util;

import java.beans.*;
import java.io.*;
import java.util.Scanner;

/**
 * This department will be responsible for the interface with the client
 * in order to activate the server and stop its operation if necessary
 */
public class CLI implements Runnable {
    Scanner input;
    PrintWriter output;
    PropertyChangeSupport changes = new PropertyChangeSupport(this);
    String oldInput;
    String clientInput;

    public CLI(InputStream in, OutputStream out) {
        input = new Scanner(in);
        output = new PrintWriter(out);
        oldInput = null;
        clientInput = "";
    }

    @Override
    public void run() {


        System.out.println("Please enter your command: ");
        oldInput = clientInput;

        String clientInput = input.nextLine();
        boolean running = true;
        while (running) {
            switch (clientInput) {
                case "start": {
                    write("Starting server...");
                    changes.firePropertyChange("clientInput", this.clientInput, clientInput);
                    this.clientInput = clientInput;
                    break;
                }
                case "stop": {
                    write("Shutdown server");
                    changes.firePropertyChange("clientInput", this.clientInput, clientInput);
                    this.clientInput = clientInput;

                    break;
                }
                default: {
                    write("Not a valid command");
                    break;
                }

            }
            if (running) {
                clientInput = input.nextLine();
            }
        }
    }


    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        changes.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        changes.removePropertyChangeListener(pcl);
    }

    public void write(String string) {

        output.println(string);
        output.flush();
    }

}
