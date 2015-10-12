import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

/**
 * Created by Carlin on 10/11/15.
 */
public class OTPServerThread extends Thread {

    final static Charset WINDOWS_CHARSET = Charset.forName("windows-1252");
    private Socket socket = null;
    private SocketAddress clientAddress = null;
    String hexStringseed;

    public OTPServerThread(Socket socket, SocketAddress address, String inhexStringSeed) {
        super("OTPServerThread");
        this.socket = socket;
        this.clientAddress = address;
        this.hexStringseed = inhexStringSeed;
    }


    public void run() {
        String inputLine = "";

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                try {
                    inputLine = in.readLine();
                    System.out.println("Encrypted message " + inputLine + " from " + clientAddress);
                    System.out.println(OTPCrypt.decrypt(inputLine.getBytes(WINDOWS_CHARSET), OTPCrypt.HexStringToByteArray(hexStringseed)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            in.close();
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}

/**
 * public void run() {
 * //get input from user
 * //send request to server
 * //get response from server
 * //send response to user
 * <p>
 * try {
 * DataOutputStream out =
 * new DataOutputStream(socket.getOutputStream());
 * BufferedReader in = new BufferedReader(
 * new InputStreamReader(socket.getInputStream()));
 * <p>
 * String inputLine, outputLine;
 * int cnt = 0;
 * String urlToCall = "";
 * ///////////////////////////////////
 * //begin get request from client
 * while ((inputLine = in.readLine()) != null) {
 * try {
 * StringTokenizer tok = new StringTokenizer(inputLine);
 * tok.nextToken();
 * } catch (Exception e) {
 * break;
 * }
 * //parse the first line of the request to find the url
 * if (cnt == 0) {
 * String[] tokens = inputLine.split(" ");
 * urlToCall = tokens[1];
 * //can redirect this to output log
 * System.out.println("Request for : " + urlToCall);
 * }
 * <p>
 * cnt++;
 * }
 * //end get request from client
 * ///////////////////////////////////
 * <p>
 * <p>
 * BufferedReader rd = null;
 * try {
 * //System.out.println("sending request
 * //to real server for url: "
 * //        + urlToCall);
 * ///////////////////////////////////
 * //begin send request to server, get response from server
 * URL url = new URL(urlToCall);
 * URLConnection conn = url.openConnection();
 * conn.setDoInput(true);
 * //not doing HTTP posts
 * conn.setDoOutput(false);
 * //System.out.println("Type is: "
 * //+ conn.getContentType());
 * //System.out.println("content length: "
 * //+ conn.getContentLength());
 * //System.out.println("allowed user interaction: "
 * //+ conn.getAllowUserInteraction());
 * //System.out.println("content encoding: "
 * //+ conn.getContentEncoding());
 * //System.out.println("content type: "
 * //+ conn.getContentType());
 * <p>
 * // Get the response
 * InputStream is = null;
 * HttpURLConnection huc = (HttpURLConnection)conn;
 * if (conn.getContentLength() > 0) {
 * try {
 * is = conn.getInputStream();
 * rd = new BufferedReader(new InputStreamReader(is));
 * } catch (IOException ioe) {
 * System.out.println(
 * "********* IO EXCEPTION **********: " + ioe);
 * }
 * }
 * //end send request to server, get response from server
 * ///////////////////////////////////
 * <p>
 * ///////////////////////////////////
 * //begin send response to client
 * byte by[] = new byte[ BUFFER_SIZE ];
 * int index = is.read( by, 0, BUFFER_SIZE );
 * while ( index != -1 )
 * {
 * out.write( by, 0, index );
 * index = is.read( by, 0, BUFFER_SIZE );
 * }
 * out.flush();
 * <p>
 * //end send response to client
 * ///////////////////////////////////
 * } catch (Exception e) {
 * //can redirect this to error log
 * System.err.println("Encountered exception: " + e);
 * //encountered error - just send nothing back, so
 * //processing can continue
 * out.writeBytes("");
 * }
 * <p>
 * //close out all resources
 * if (rd != null) {
 * rd.close();
 * }
 * if (out != null) {
 * out.close();
 * }
 * if (in != null) {
 * in.close();
 * }
 * if (socket != null) {
 * socket.close();
 * }
 * <p>
 * } catch (IOException e) {
 * e.printStackTrace();
 * }
 * }
 * }
 * <p>
 * public class WorkerRunnable implements Runnable{
 * <p>
 * protected Socket clientSocket = null;
 * protected String serverText   = null;
 * <p>
 * public WorkerRunnable(Socket clientSocket, String serverText) {
 * this.clientSocket = clientSocket;
 * this.serverText   = serverText;
 * }
 * <p>
 * public void run() {
 * try {
 * inputstream input  = clientsocket.getinputstream();
 * outputstream output = clientsocket.getoutputstream();
 * long time = system.currenttimemillis();
 * output.write(("http/1.1 200 ok\n\nworkerrunnable: " +
 * this.servertext + " - " +
 * time +
 * "").getbytes());
 * output.close();
 * input.close();
 * system.out.println("request processed: " + time);
 * } catch (ioexception e) {
 * //report exception somewhere.
 * e.printstacktrace();
 * }
 * }
 * }
 * <p>
 * <p>
 * try {
 * // Print out Client
 * System.out.println("Client Connected: " + clientAddress.toString());
 * <p>
 * // Set up data streams
 * OutputStream outToClient = socket.getOutputStream();
 * InputStream inFromClient = socket.getInputStream();
 * <p>
 * <p>
 * ByteArrayOutputStream buffer = new ByteArrayOutputStream();
 * byte[] data = new byte[1024];
 * boolean dataOver = false;
 * while (!dataOver) {
 * int length = inFromClient.read(data, 0, data.length);
 * if (length != -1) {
 * buffer.write(data, 0, length);
 * buffer.flush();
 * }
 * if (length == -1 || inFromClient.available() == 0) {
 * dataOver = true;
 * }
 * }
 * } catch (IOException e) {
 * e.printStackTrace();
 * }
 */