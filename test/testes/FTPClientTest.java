/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package testes;

import ftp.FTPClient;
import java.io.File;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danilo
 */
public class FTPClientTest {

    FTPClient client;

    @Before
    public void setUp(){
        client = new FTPClient("localhost", "teste", "teste");
    }

    @After
    public void tearDown(){
        client.quit();
    }



    @Test
    public void testPut() {
    }

    @Test
    public void testGet() {
        client.get("fig1.png");

        File file = new File("fig1.png");

        assertEquals( file.length(), 86471 );
    }

    @Test
    public void testNist(){
        String[] arr = client.nlist(".");

        assertEquals(arr.length, 6);

        Arrays.sort(arr);

        assertEquals(arr[0], "FileSize.class");

        System.out.println();
    }

    @Test
    public void testMkRmDir(){
        boolean b = client.mkdir("teste_dir");
        assertTrue(b);

        boolean b2 = client.mkdir("fig1.png");
        assertFalse(b2);

        b = client.rmdir("teste_dir");

        assertTrue(b);

        b2 = client.rmdir("auehuaheuhe");
        assertFalse(b2);
    }






}