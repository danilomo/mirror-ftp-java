/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mirrorftp.command;

import ftp.FTPClient;

/**
 *
 * @author danilo
 */
public class UploadFileCommand extends FileCommand{

    public UploadFileCommand(String fileName, FTPClient client) {
        super(fileName, client);
    }

    public void execute() {
        client.put(fileName);
    }

    public void undo() {
        client.del(fileName);
    }

    @Override
    public String toString() {
        return "Upload do arquivo " + fileName;
    }



}
