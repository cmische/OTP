import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Carlin on 10/11/15.
 */
public class OTPServer {

    @Option(name = "-p", usage = "port to connect to")
    int portnumber = 9090;
    @Option(name = "-k", usage = "32bits/8hex/16char(0-F) string to seed SecureRandom for a one-time-pad", required = true)
    String hexStringSeed;

    public static void main(String[] args) throws IOException, CmdLineException {
        new OTPServer().doMain(args);
    }

    public void doMain(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            // parse the arguments.
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            System.err.println(e.getMessage());
            System.err.println("java OTPClient [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();
            // print option sample. This is useful some time
            //System.err.println("  Example: java SampleMain" + parser.printExample(ALL));
            return;
        }
        ServerSocket server = null;
        boolean serverRunning = true;

        try {
            server = new ServerSocket(portnumber);
            System.out.println("Started Server Listening to Port: " + portnumber);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + portnumber);
            System.exit(-1);
        }

        while (serverRunning) {
            Socket acceptedSocket = server.accept();
            new OTPServerThread(acceptedSocket, acceptedSocket.getRemoteSocketAddress(), hexStringSeed).start();
        }
        server.close();
    }
}
