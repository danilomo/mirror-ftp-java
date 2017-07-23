package ftp;

import java.util.Calendar;

/**
 *
 * @author danilo
 */
public class FTPClient {

    private FTPAgent agent;
    private char typeCod;
    private String host;
    private String user;
    private String password;
    private boolean debug;

    public FTPClient(String host, String user, String pwd) {
        this(host, user, pwd, false);
    }

    public FTPClient(String host, String user, String pwd, boolean debug) {
        agent = new FTPAgent(host, debug);
        agent.connect(user, pwd);

        this.host = host;
        this.password = pwd;
        this.user = user;
        this.debug = debug;

        System.out.println("User is connected.");

        typeCod = 'A';
        agent.command("TYPE", "A");
    }

    public String list(String arg) {
        String ls = null;

        setTextTransfer();

        agent.openDataConnection();

        agent.command("LIST", arg);

        try {
            ls = agent.getStringValueFromDataConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        agent.closeDataConnection();

        agent.getResponseFromControlConnection();

        return ls;
    }

    public String[] nlist(String arg) {
        String ls = null;

        setTextTransfer();

        agent.openDataConnection();
        agent.command("NLST", arg);

        try {
            ls = agent.getStringValueFromDataConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        agent.closeDataConnection();

        agent.getResponseFromControlConnection();


        if (ls.length() == 0) {
            return null;
        }

        return ls.split("\n");
    }

    private int extractLength(String s) throws NumberFormatException {
        s = s.substring(s.indexOf("(") + 1);
        s = s.substring(0, s.indexOf(" "));
        int tam = Integer.parseInt(s);
        return tam;
    }

    private long getTimeInMillis(String resp) throws NumberFormatException {
        resp = resp.substring(4);
        int year = Integer.parseInt(resp.substring(0, 4));
        int month = Integer.parseInt(resp.substring(4, 6));
        int date = Integer.parseInt(resp.substring(6, 8));
        int hrs = Integer.parseInt(resp.substring(8, 10));
        int min = Integer.parseInt(resp.substring(10, 12));
        int sec = Integer.parseInt(resp.substring(12, 14));
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, date, hrs, min, sec);
        return c.getTimeInMillis();
    }

    private void setBinaryTransfer() {
        if (typeCod != 'I') {
            typeCod = 'I';
            agent.command("TYPE", "I");
        }
    }

    private void setTextTransfer() {
        if (typeCod != 'A') {
            typeCod = 'A';
            agent.command("TYPE", "A");
        }
    }

    public boolean cd(String arg) {
        String resp = agent.command("CWD", arg);

        return resp.substring(0, 1).equals("2");
    }

    public void get(String arg) {

        System.out.println("Starting download: " + arg);

        setBinaryTransfer();

        agent.openDataConnection();

        String resp = agent.command("RETR", arg);

        int length = extractLength(resp);

        agent.readFileFromDataConnection(arg, length);

        agent.closeDataConnection();

        agent.getResponseFromControlConnection();

        System.out.println("Download finished!");
    }

    public void put(String arg) {

        System.out.println("Starting upload: " + arg);

        setBinaryTransfer();

        agent.openDataConnection();

        agent.command("STOR", arg);

        agent.writeFileOnDataConnection(arg);

        agent.closeDataConnection();

        agent.getResponseFromControlConnection();

        System.out.println("Upload finished!");
    }

    private boolean successfulCommand(String resp) {
        return resp.substring(0, 1).equals("2");
    }

    public boolean mkdir(String arg) {
        String resp = agent.command("MKD", arg);

        return successfulCommand(resp);
    }

    public boolean rmdir(String arg) {
        String resp = agent.command("RMD", arg);

        return successfulCommand(resp);
    }

    public long modTime(String s) {

        String resp = agent.command("MDTM", s);

        return getTimeInMillis(resp);
    }

    public static void main(String args[]) {
        FTPClient cli = new FTPClient("localhost", "teste", "teste", true);

        cli.mkdir("test");

        cli.quit();

    }

    public void quit() {
        agent.command("QUIT", "");
    }

    public void del(String file) {
        agent.command("DELE", file);
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    public void reconnect() {
        quit();

        agent = new FTPAgent(host, debug);
        agent.connect(user, password);

        System.out.println("User is connected.");

        typeCod = 'A';
        agent.command("TYPE", "A");
    }
}
