/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mirrorftp;

import java.io.Console;
import java.util.Arrays;

/**
 *
 * @author danilo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar mirro-ftp.jar <host> <user>");
            System.exit(1);
        }

        Console con = System.console();
        System.out.print("Password: ");
        char[] pass = con.readPassword();
        StringBuilder buff = new StringBuilder();
        for (char c : pass) {
            buff.append(c);
        }

        MirrorFTP mirror = new MirrorFTP( args[0], args[1], buff.toString() );

        mirror.run();




    }
}
