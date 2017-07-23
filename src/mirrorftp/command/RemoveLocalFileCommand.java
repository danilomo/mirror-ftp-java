/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mirrorftp.command;

import java.io.File;

/**
 *
 * @author danilo
 */
public class RemoveLocalFileCommand implements Command{

    private String fileName;

    public RemoveLocalFileCommand(String file) {
        this.fileName = file;
    }


    public void execute() {
        File f = new File(fileName);
        f.delete();
    }

    public void undo() {
        // Como eu vou fazer para restaurar um objeto deletado?
        //Talvez criando um cache para os arquivos...
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
