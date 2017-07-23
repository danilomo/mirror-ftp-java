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
public abstract class FileCommand implements Command {

    protected String fileName;
    protected FTPClient client;

    public FileCommand(String fileName, FTPClient client) {
        this.fileName = fileName;
        this.client = client;
    }

}
