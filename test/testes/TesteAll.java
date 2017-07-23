/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package testes;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author danilo
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({testes.FTPAgentTest.class,testes.MirrorFTPTest.class,testes.FTPClientTest.class})
public class TesteAll {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

}