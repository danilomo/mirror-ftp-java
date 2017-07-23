/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mirrorftp;

import ftp.FTPClient;
import java.util.HashMap;
import java.util.Map;
import mirrorftp.command.Command;
import mirrorftp.command.MacroCommand;

/**
 *
 * @author danilo
 */
public class MirrorFTP {

    private FTPClient client;
    private Command lastCommand;
    
    public MirrorFTP(String host, String user, String pwd) {
        client = new FTPClient(host, user, pwd, true);
    }

    public void run() {

        while (true) {

            System.out.println("Starting update...");

            update();

            System.out.println("Update finished! (waiting five minutes till the next update)");

            //TODO - remove magic number
            try {
                Thread.sleep(6000 * 5);
            } catch (InterruptedException ex) {
            }

        }


    }

    public void update() {

        Directory localDirectory = new LocalDirectory(client);
        Directory remoteDirectory = new RemoteDirectory(client);

        
        Command updateDirs = Directory.synchronizeDirectories(localDirectory, remoteDirectory);

        Command updateFiles = Directory.synchronizeFiles(localDirectory, remoteDirectory);

        lastCommand = new MacroCommand(updateDirs, updateFiles);
        
        lastCommand.execute();
        
    }

    public void undo(){
        lastCommand.undo();
    }



    public static Map<String, Long> map = new HashMap<String, Long>(); 

    public static void main(String[] args) {

        MirrorFTP mirror = new MirrorFTP("localhost", "teste", "teste");

        mirror.update();

        mirror.undo();

        mirror.closeSection();

    } 

    public void closeSection() {
        client.quit();
    }

    public FTPClient getFTPClient() {
        return client;
    }


}
