import java.io.*;
import java.net.*;
import java.nio.charset.Charset;


/**
 * Thread generated when a client connects. It first recieves the length of a message, and then receives the message. The seed passed by OTPServer is fed into
 * Secure Random to generate a pad identical to the client if seeded the same. The message is then received and
 * decrypted with the one-time-pad. Various information is printed about the client and the incoming message.
 */
public class OTPServerThread extends Thread {

    /**
     * Charset to be used for encoding and decoding
     */
    final static Charset WINDOWS_CHARSET = Charset.forName("windows-1252");
    private Socket socket = null;
    private SocketAddress clientAddress = null;
    String hexStringseed;

    /**
     * Pass information about socket, client address, and seed from OTPServer class to OTPServerThread
     * @param socket
     * @param address
     * @param inhexStringSeed
     */
    public OTPServerThread(Socket socket, SocketAddress address, String inhexStringSeed) {
        super("OTPServerThread");
        this.socket = socket;
        this.clientAddress = address;
        this.hexStringseed = inhexStringSeed;
    }

    /**
     * Performs handling of functionality described in class doc.
     */
    public void run() {
        String inputLine = null;
        System.out.println("New message!");
        try {
            DataInputStream lengthin = new DataInputStream(socket.getInputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int length = 1;
            try {
                length = lengthin.readInt();
                System.out.println("Encrypted message length is " + length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                char [] buf = new char[length];
                in.read(buf, 0, length);
                String encryptedmessage = new String(buf);
                System.out.println("Encrypted message " + OTPCrypt.ByteArrayToHexString(encryptedmessage.getBytes()) + " from " + clientAddress);
                System.out.println("Decrypted message follows");
                System.out.println(OTPCrypt.decrypt(encryptedmessage.getBytes(), OTPCrypt.HexStringToByteArray(hexStringseed)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            in.close();
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
        System.out.println("Done");
    }
}
