/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mirrorftp.command;

import ftp.FTPClient;
import java.io.File;

/**
 *
 * @author danilo
 */
public class DownloadFileCommand extends FileCommand{

    public DownloadFileCommand(String file, FTPClient cli) {
        super(file, cli);
    }

    public void execute() {
        client.get(fileName);      
    }

    public void undo() {
        File f = new File(fileName);
        f.delete();
    }

    @Override
    public String toString() {
        return "Download do arquivo " + fileName;
    }



}
