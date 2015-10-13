import static org.kohsuke.args4j.ExampleMode.ALL;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.net.*;
import java.io.*;

/**
 * Created by Carlin on 10/11/15.
 */


public class OTPClient {

    @Option(name = "-p", usage = "port to connect to")
    private int portnumber = 9090;
    @Option(name = "-t", usage = "ip or hostname to connect to", required = true)
    private InetAddress serverIPAddress;
    @Option(name = "-k", usage = "32bits/8hexpair (0-F) string to seed SecureRandom for a one-time-pad", required = true)
    private String hexStringSeed;
    @Option(name = "-s", usage = "message to encrypt and send", required = true)
    private String message;

    public static void main(String[] args) throws IOException, CmdLineException {
        new OTPClient().doMain(args);
    }

    public void doMain(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        String encryptedMessage;
        System.out.println();
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
            System.err.println("  Example: java SampleMain" + parser.printExample(ALL));
            return;
        }
        System.out.println("Starting connection to server with message: " + message);
        encryptedMessage = OTPCrypt.encrypt(message.getBytes(), OTPCrypt.HexStringToByteArray(hexStringSeed));
        System.out.println("Encrypted message is: " + encryptedMessage);
        System.out.println("Sending encrypted message.");
        Socket serverSocket = new Socket(serverIPAddress, portnumber);
        BufferedWriter out = new BufferedWriter( new OutputStreamWriter(serverSocket.getOutputStream()));
        DataOutputStream lengthout = new DataOutputStream(serverSocket.getOutputStream());
        lengthout.writeInt(encryptedMessage.length());
        out.write(encryptedMessage);
        out.close();
    }
}