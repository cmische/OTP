import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;
import java.security.SecureRandom;

/**
 * Utility class mostly used by the one-time-pad server and client for encrypting and decrypting messages.
 */
public class OTPCrypt {

    /**
     * Charset to be used for encoding and decoding
     */
    final static Charset WINDOWS_CHARSET = Charset.forName("windows-1252");

    /**
     *
     * @param secret A byte array of the secret message you would like to encrypt, easily accomplished by string.getbytes()
     * @param keyHexSeed A byte array of your seed, easily accomplished by calling OTPCrypt.HexStringToByteArray(HexString)
     * @return
     */
    public static String encrypt(byte[] secret, byte[] keyHexSeed) {
        CheckDefaultCharsetWarning();

        //Converts secret text to encrypted byte array
        //Instantiate encoded byte array
        final byte[] encoded = new byte[secret.length];
        //Generate random key
        final byte[] key = new byte[secret.length];
        new SecureRandom(keyHexSeed).nextBytes(key);
        //Encrypt
        for (int i = 0; i < secret.length; i++) {
            //xors each byte of the secret with the key
            encoded[i] = (byte) (secret[i] ^ key[i]);
        }
        //String accepts String charsetName as a parameter for converting bytes to chars
        String StringEncoded = new String(encoded, WINDOWS_CHARSET);
        return StringEncoded;
    }

    /**
     *
     * @param encryptedMessage A byte array of the encrypted message you would like to decrypt, easily accomplished by
     *                         string.getbytes()
     * @param keyHexSeed A byte array of your seed, easily accomplished by calling OTPCrypt.HexStringToByteArray(HexString)
     * @return
     */
    public static String decrypt(byte[] encryptedMessage, byte[] keyHexSeed) {
        CheckDefaultCharsetWarning();

        //Convert encrypted byte array to decoded byte array
        //Instantiate decoded byte array
        final byte[] decoded = new byte[encryptedMessage.length];
        //Generate random key
        final byte[] key = new byte[encryptedMessage.length];
        new SecureRandom(keyHexSeed).nextBytes(key);
        //Decrypt
        for (int i = 0; i < encryptedMessage.length; i++) {
            //xors each encoded byte with the key
            decoded[i] = (byte) (encryptedMessage[i] ^ key[i]);
        }
        //String accepts String charsetName as a parameter for converting bytes to chars
        String StringDecoded = new String(decoded, WINDOWS_CHARSET);
        return StringDecoded;
    }

    /**
     * @param s A string to be converted into a byte array
     * @return
     */
    public static byte[] HexStringToByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }

    /**
     * @param b A byte array to be converted to a hexadecimal string
     * @return
     */
    public static String ByteArrayToHexString(byte[] b) {
        return DatatypeConverter.printHexBinary(b);
    }

    /**
     * Simply checks to see if the user's default charset is windows, and if it isn't alerts them that it is the charset
     * being used for encoding and decoding.
     */
    public static void CheckDefaultCharsetWarning() {
        //If default charset isn't windows, alert the client that a windows charset is being used instead
        if (!Charset.defaultCharset().equals(WINDOWS_CHARSET)) {
            System.out.println("Your default Charset is " + Charset.defaultCharset() + ", desired charset is windows-1252");
            System.out.println("Using windows-1252 charset instead for decoding");
        }
    }
}
