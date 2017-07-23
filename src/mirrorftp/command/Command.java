/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mirrorftp.command;

/**
 *
 * @author danilo
 */
public interface Command {

    public void execute();

    public void undo();

}
