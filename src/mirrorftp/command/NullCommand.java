/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mirrorftp.command;

/**
 *
 * @author danilo
 */
public class NullCommand implements Command {

    private static Command command = new NullCommand();

    public static Command getInstance() {
        return command;
    }

    private NullCommand() {
    }

    public void execute() {
    }

    public void undo() {
    }
}
