/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mirrorftp.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author danilo
 */
public class MacroCommand implements Command{

    List<Command> commands;

    public MacroCommand(){
        commands = new ArrayList<Command>();
    }

    public MacroCommand(Command ... clist ){
        this();
        commands.addAll(Arrays.asList(clist));
    }

    public void execute() {
        for(Command c: commands){
            c.execute();
        }
    }

    public void undo() {

        Collections.reverse(commands);

        for(Command c: commands){
            c.undo();
        }

        Collections.reverse(commands);
    }

    public void addCommand(Command command) {
        commands.add(command);
    }


}
