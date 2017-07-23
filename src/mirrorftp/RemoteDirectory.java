/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mirrorftp;

import ftp.FTPClient;
import java.util.TreeSet;
import mirrorftp.command.Command;
import mirrorftp.command.DownloadFileCommand;
import mirrorftp.command.RemoteMkDirCommand;
import mirrorftp.command.UploadFileCommand;

/**
 *
 * @author danilo
 */
public class RemoteDirectory extends Directory {

    private FTPClient client;

    /**
     * @return the client
     */
    public FTPClient getClient() {
        return client;
    }

    public RemoteDirectory(FTPClient cli) {
        super();
        client = cli;

        getRemoteTree(".", 0);
    }

    private void getRemoteTree(String arg, int nivel) {

        String[] arr = getClient().nlist(".");

        if (arr == null) {
            getClient().cd("..");
            return;
        }


        if (getFolderList().size() <= nivel) {
            getFolderList().add(new TreeSet<String>());
        }

        getFolderList().get(nivel).add(arg);

        for (String s : arr) {

            if (getClient().cd(s)) {
                getRemoteTree(arg + "/" + s, nivel + 1);
            } else {
                getFileList().put(arg + "/" + s, getClient().modTime(s));
            }

        }

        getClient().cd("..");

    }

    @Override
    public Command syncDirs(Directory directory) {
        client.reconnect();
        return super.syncDirs(directory);
    }

    @Override
    protected Command createDirectory(String s) {
        return new RemoteMkDirCommand(s, client);

    }

    @Override
    protected Command getFile(String s) {
        return new DownloadFileCommand(s, client);
    }

    @Override
    protected Command putFile(String s) {
        return new UploadFileCommand(s, client);
    }
}
