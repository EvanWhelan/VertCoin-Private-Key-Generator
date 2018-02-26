import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PrivateKeyGenerator {
//	public static String myKey = "80" + privateKeyGenerator();
	
	public static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
	
	


	public static void main(String[] args) {
        
        //  Generate a random 64 digit hexadecimal string
        //  and append "80" to the front of it
    
		String myKey = "80" + privateKeyGenerator();
        String hashedMyKey = "";
        
        //Double hash myKey and store in hashedMyKey (implents the SHA256 Hashing Algorithm)
		try {
			 hashedMyKey = SHA256.sha256(SHA256.sha256(myKey));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
        String first8 = hashedMyKey.substring(0,8);
        // Isolate the first 8 digits of the hashed string and append to the end of the original myeKey
		myKey += first8;
        System.out.println(myKey);
        
        // Put this final string into a byte array
        byte[] byteArray = SHA256.hexStringToByteArray(myKey);
        // Calculate the base58 encoding of the string
        String yourPrivateKey = encode(byteArray);
        System.out.print(yourPrivateKey);  // -- This is the private key that will be used --
		
	}
	
	
	static String encode(byte[] array){
		BigInteger base58 = BigInteger.valueOf(58);
		BigInteger x = new BigInteger(1,array);
		
        String s = "";
        
        // Repeatedly mod the number by 58, keep track of the remainder and then divide by 58
        // Do this until the number is 0

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
		
		// Randomly select 64 digits in hex and store them in a string called returnKey
		
		while(returnKey.length() != 64) {
			returnKey = returnKey + "" + hexChars.charAt(rn.nextInt(max-min+1)+min);
		}
		
		
		return returnKey;
		
		
	}
	
}

