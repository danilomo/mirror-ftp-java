/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author danilo
 */
class DataTransferAgent {

    FTPAgent agent;
    private BufferedReader dataConnectionInText;
    private BufferedInputStream dataConnectionIn;
    private BufferedOutputStream dataConnectionOut;
    private Socket socketDados;

    public DataTransferAgent(FTPAgent agent) {
        this.agent = agent;
    }

    /**
     * @return the dataConnectionInText
     */
    public BufferedReader getDataConnectionInText() {
        return dataConnectionInText;
    }

    /**
     * @param dataConnectionInText the dataConnectionInText to set
     */
    public void setDataConnectionInText(BufferedReader dataConnectionInText) {
        this.dataConnectionInText = dataConnectionInText;
    }

    /**
     * @return the dataConnectionIn
     */
    public BufferedInputStream getDataConnectionIn() {
        return dataConnectionIn;
    }

    /**
     * @param dataConnectionIn the dataConnectionIn to set
     */
    public void setDataConnectionIn(BufferedInputStream dataConnectionIn) {
        this.dataConnectionIn = dataConnectionIn;
    }

    /**
     * @return the dataConnectionOut
     */
    public BufferedOutputStream getDataConnectionOut() {
        return dataConnectionOut;
    }

    /**
     * @param dataConnectionOut the dataConnectionOut to set
     */
    public void setDataConnectionOut(BufferedOutputStream dataConnectionOut) {
        this.dataConnectionOut = dataConnectionOut;
    }

    public void writeFileOnDataConnection(String arg) {
        BufferedInputStream buff = null;

        try {
            buff = new BufferedInputStream(new FileInputStream(arg));

            File f = new File(arg);
            int filesize = (int) f.length();
            int left = filesize;
            int block = 1024;

            while (left > 0) {

                if (left > block) {
                    left = left - block;
                } else {
                    block = left;
                    left = 0;
                }

                byte[] b = new byte[block];

                buff.read(b);
                getDataConnectionOut().write(b);
                getDataConnectionOut().flush();


            }

            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readFileFromDataConnection(String arg, int len) {
        File f = new File(arg);
        f.delete();

        BufferedOutputStream bos = null;

        try {
            bos = new BufferedOutputStream(new FileOutputStream(arg));
            int available = len;

            while (available > 0) {
                int block = getDataConnectionIn().available();

                if (block == 0) {
                    continue;
                }

                if (available > block) {
                    available = available - block;
                } else {
                    block = available;
                    available = 0;
                }

                byte[] b = new byte[block];
                getDataConnectionIn().read(b);


                bos.write(b);
                bos.flush();
            }

            bos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the socketDados
     */
    public Socket getDataSocket() {
        return socketDados;
    }

    /**
     * @param socketDados the socketDados to set
     */
    public void setDataSocket(Socket socketDados) {
        this.socketDados = socketDados;
    }

    public void openDataConnection() {

        try {

            agent.pasv();

            setDataSocket(new Socket(agent.getHost(), agent.getPort()));

            setDataConnectionInText(new BufferedReader(new InputStreamReader(getDataSocket().getInputStream())));

            setDataConnectionIn(new BufferedInputStream(getDataSocket().getInputStream()));
            setDataConnectionOut(new BufferedOutputStream(getDataSocket().getOutputStream()));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void closeDataConnection() {
        try {
            getDataSocket().close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getStringValueFromDataConnection() throws Exception {
        StringBuffer buffer = new StringBuffer();

        String s = "";

        while (s != null) {
            s = getDataConnectionInText().readLine();
            if (s != null) {
                buffer.append(s + "\n");
            }
        }

        return buffer.toString();
    }
}
