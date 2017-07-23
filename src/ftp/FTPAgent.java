/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ftp;

import java.net.*;
import java.io.*;

/**
 *
 * @author danilo
 */
public class FTPAgent {

    private Socket controlSocket;
    private String host;
    private int port;
    private BufferedReader controlConnectionIn;
    private BufferedWriter controlConnectiouOut;
    private boolean debug;
    private DataTransferAgent dataTransferAgent;

    public FTPAgent(String host) {

        try {
            controlSocket = new Socket(host, 21);
            this.host = host;


            controlConnectionIn = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));
            controlConnectiouOut = new BufferedWriter(new OutputStreamWriter(controlSocket.getOutputStream()));

            dataTransferAgent = new DataTransferAgent(this);

            String s = controlConnectionIn.readLine();
            System.out.println("--> " + s);

        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        debug = false;
    }

    public FTPAgent(String host, boolean debug) {
        this(host);
        this.debug = debug;
    }

    public String command(String command, String args) {
        try {
            if (debug) {
                System.out.println("=====> Comando: " + command + " " + args);
            }

            controlConnectiouOut.write(command + " " + args + "\r\n");
            controlConnectiouOut.flush();

            String s = controlConnectionIn.readLine();

            if (debug) {
                System.out.println("-----> Resposta: " + s);
            }

            return s;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void connect(String user, String pwd) {
        command("USER", user);
        command("PASS", pwd);
    }

    public String getStringValueFromDataConnection() throws Exception {
        return dataTransferAgent.getStringValueFromDataConnection();
    }

    public String getResponseFromControlConnection() {

        String st = null;
        try {
            st = controlConnectionIn.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        return st;
    }

    void pasv() {

        String str = command("PASV", "");

        String[] s = str.split(",");

        int p1 = Integer.parseInt(s[4]);

        s[5] = s[5].substring(0, s[5].indexOf(")"));

        int p2 = Integer.parseInt(s[5]);
        port = p1 * 256 + p2;

    }

    public void openDataConnection() {
        dataTransferAgent.openDataConnection();
    }

    public void closeDataConnection() {
        dataTransferAgent.closeDataConnection();
    }

    public void readFileFromDataConnection(String arg, int tam) {
        dataTransferAgent.readFileFromDataConnection(arg, tam);
    }

    public void writeFileOnDataConnection(String arg) {
        dataTransferAgent.writeFileOnDataConnection(arg);
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }
}
