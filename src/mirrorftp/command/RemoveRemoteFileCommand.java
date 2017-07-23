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
public class RemoveRemoteFileCommand implements Command {

    private String fileName;
    private FTPClient client;

    public RemoveRemoteFileCommand(String file, FTPClient cli) {
        this.fileName = file;
        this.client = cli;
    }

    public void execute() {
        client.del(fileName);
    }

    public void undo() {
        //Como eu vou fazer para restaurar um objeto deletado?
        //Talvez criando um cache para os arquivos...
        throw new UnsupportedOperationException("Not supported yet.");
    }    


}
