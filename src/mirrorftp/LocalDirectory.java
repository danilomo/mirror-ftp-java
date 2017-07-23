/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mirrorftp;

import ftp.FTPClient;
import java.io.File;
import java.util.TreeSet;
import mirrorftp.command.Command;
import mirrorftp.command.DownloadFileCommand;
import mirrorftp.command.LocalMkDirCommand;
import mirrorftp.command.UploadFileCommand;

/**
 *
 * @author danilo
 */
public final class LocalDirectory extends Directory {

    private FTPClient client;


    public LocalDirectory(FTPClient client) {
        super();
        this.client = client;

        getLocalTree(".", 0);
    }



    protected void getLocalTree(String arg, int nivel) {

        File diretorio = new File(arg);

        if (getFolderList().size() <= nivel) {
            getFolderList().add(new TreeSet<String>());
        }

        getFolderList().get(nivel).add(diretorio.getPath());

        for (File f : diretorio.listFiles()) {
            if (f.isDirectory()) {
                System.out.println(diretorio.getPath() + "/" + f.getName());
                getLocalTree(diretorio.getPath() + "/" + f.getName(), nivel + 1);
            } else {
                getFileList().put(diretorio.getPath() + "/" + f.getName(), f.lastModified());
            }
        }

    }

    @Override
    protected Command createDirectory(String s) {
        return new LocalMkDirCommand(s, client);
    }


    @Override
    protected Command getFile(String s) {
        return new UploadFileCommand(s, client);
    }

    @Override
    protected Command putFile(String s) {
        return new DownloadFileCommand(s, client);
    }
}
