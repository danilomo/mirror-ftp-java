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
public class RemoteMkDirCommand extends FileCommand{

    public RemoteMkDirCommand(String file, FTPClient cli) {
        super(file, cli);
    }

    public void execute() {
        client.mkdir(fileName);
    }

    public void undo() {
        client.rmdir(fileName);
    }

    
}
