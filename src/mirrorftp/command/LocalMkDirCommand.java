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
public class LocalMkDirCommand extends FileCommand {

    public LocalMkDirCommand(String file, FTPClient ftp) {
        super(file, ftp);
    }

    public void execute() {
        File f = new File(fileName);
        f.mkdir();
    }

    public void undo() {
        File f = new File(fileName);
        f.delete();
    }
}
