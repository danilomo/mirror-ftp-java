/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import ftp.FTPClient;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import mirrorftp.LocalDirectory;
import mirrorftp.MirrorFTP;
import mirrorftp.RemoteDirectory;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danilo
 */
public class MirrorFTPTest extends junit.framework.TestCase {

    private MirrorFTP mirror;


    @Override
    public void tearDown() {


        FTPClient cli = mirror.getFTPClient();

        cli.del("fig1.png");
        cli.del("apostila.doc");

        localDel("FileSize.class");
        localDel("FileSize.java");
        localDel("FileTime.class");
        localDel("FileTime.java");

        mirror.closeSection();
    }


    @Override
    public void setUp() {
        mirror = new MirrorFTP("localhost", "teste", "teste");
    }

    //AQUI A COBRA VAI FUMAR!
    @Test
    public void testUpdate() throws IOException {

        mirror.update();

        List<String> arquivosLocal = new ArrayList<String>();
        List<String> arquivosRemoto = new ArrayList<String>();
        List<String> arquivosComp = getArquivosComp3();

        Map<String, Long> localMap = (new LocalDirectory(mirror.getFTPClient())).getFileList();

        getArquivos(localMap, arquivosLocal);

        Map<String, Long> remoteMap = (new RemoteDirectory(mirror.getFTPClient())).getFileList();
       
        getArquivos(remoteMap, arquivosRemoto);


        Object[] arr1 = arquivosLocal.toArray();
        Object[] arr2 = arquivosRemoto.toArray();
        Object[] arr3 = arquivosComp.toArray();

        assertArrayEquals(arr1, arr2);
        assertArrayEquals(arr2, arr3);
    }

    private void getArquivos(Map<String, Long> map, List<String> arquivos) {
        for (String key : map.keySet()) {
            arquivos.add(key);
        }
        Collections.sort(arquivos);
        for (String arq : arquivos) {
            System.out.println(arq);
        }
    }

    private List<String> getArquivosComp() {
        List<String> list = new ArrayList<String>();

        list.add("./apostila.doc");
        list.add("./fig1.png");


        return list;
    }

    private List<String> getArquivosComp2() {
        List<String> list = new ArrayList<String>();

        list.add("./FileSize.class");
        list.add("./FileSize.java");
        list.add("./FileTime.class");
        list.add("./FileTime.java");


        return list;
    }

    private List<String> getArquivosComp3() {
        List<String> l1 = getArquivosComp();
        List<String> l2 = getArquivosComp2();

        List<String> list = new ArrayList<String>();

        list.addAll(l1);
        list.addAll(l2);

        Collections.sort(list);

        return list;
    }

    private void localDel(String string) {
        File f = new File(string);
        f.delete();
    }
}
