/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mirrorftp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mirrorftp.command.Command;
import mirrorftp.command.MacroCommand;

/**
 *
 * @author danilo
 */
public abstract class Directory {

    private Map<String, Long> fileList;
    private List<Set<String>> folderList;

    public Directory() {
        fileList = new HashMap<String, Long>();
        folderList = new ArrayList<Set<String>>();
    }

    /**
     * @return the fileList
     */
    public Map<String, Long> getFileList() {
        return fileList;
    }

    /**
     * @return the folderList
     */
    public List<Set<String>> getFolderList() {
        return folderList;
    }

    public Command syncDirs(Directory directory) {

        MacroCommand command = new MacroCommand();

        for (int i = 0; i < directory.getFolderList().size(); i++) {

            for (String s : directory.getFolderList().get(i)) {
                
                if( (i >= getFolderList().size()) || !getFolderList().get(i).contains(s)) {
                    command.addCommand(createDirectory(s.substring(2)));
                }

            }
        }

        return command;

    }

    public void syncFiles(Directory dir, Map<String, Command> mapCommands) {

        for (String s : getFileList().keySet()) {           

            Command command = null;

            if( !dir.getFileList().containsKey(s)){
                // user doesn't have the file
                command = dir.putFile(s);                
            }else if (dir.getFileList().get(s) > getFileList().get(s)) {
                // remote file is more recent
                command = dir.getFile(s);
            } else {
                // local file is more recent
                command = dir.putFile(s);
            }

            mapCommands.put(s, command);
        }

    }

    public static Command synchronizeFiles( Directory d1, Directory d2 ){
        Map<String, Command> map = new HashMap<String, Command>();

        d1.syncFiles(d2, map);
        d2.syncFiles(d1, map);

        MacroCommand command = new MacroCommand();

        for(String key: map.keySet()){
            System.out.println(map.get(key));
            command.addCommand(map.get(key));
        }

        return command;
    }

    public static Command synchronizeDirectories( Directory d1, Directory d2 ){
        Command c1 = d1.syncDirs(d2);
        Command c2 = d2.syncDirs(d1);

        return new MacroCommand(c1, c2);
    }

    protected abstract Command createDirectory(String s);

    protected abstract Command getFile(String s);

    protected abstract Command putFile(String s);
}
