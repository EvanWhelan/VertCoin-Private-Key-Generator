import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PrivateKeyGenerator {
//	public static String myKey = "80" + privateKeyGenerator();
	
	public static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
	
	


	public static void main(String[] args) {

        // Generate a random 64 character hexadecimal string and append 80 to the front of it
		String myKey = "80" + privateKeyGenerator();
        String hashedMyKey = "";
        
        // Double hash the string and store it in hashedMyKey

		try {
			 hashedMyKey = SHA256.sha256(SHA256.sha256(myKey));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
        }
        
        // Take out the first 8 digits of the hashed string
		
        String first8 = hashedMyKey.substring(0,8);
        // Add these digits to the original 64 character hex string
		myKey += first8;
        //System.out.println(myKey);
        
        // Store the string in a byte array and calculate the base 58 encoding of it
        byte[] byteArray = SHA256.hexStringToByteArray(myKey);
        String finalKey = encode(byteArray); // --This is your private key--
		
		
	}
	
	
	static String encode(byte[] array){
		BigInteger base58 = BigInteger.valueOf(58);
		BigInteger x = new BigInteger(1,array);
        
        // Repeatedly modulo the number by 58, store the remander and then divide by 58 until you can't do this anymore
		String s = "";
		while(x.compareTo(BigInteger.ZERO)>0) {
			int remainder = x.mod(base58).intValueExact();
			s = ALPHABET[remainder] + s;
			x = x.divide(base58);
		}
		return s;
	}

	
	public static String privateKeyGenerator(){
		
		String hexChars = "0123456789ABCDEF";
		String returnKey = "";
		int min = 0;
		int max = hexChars.length()-1;
		Random rn = new Random();
		
        // Generate a random 64 character hexadecimal string
        
		while(returnKey.length() != 64) {
			returnKey = returnKey + "" + hexChars.charAt(rn.nextInt(max-min+1)+min);
		}
		
		
		return returnKey;
		
		
	}
	
}

