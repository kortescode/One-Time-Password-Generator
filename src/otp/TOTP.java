package otp;

import utils.Crypto;
import java.math.BigInteger;

public class TOTP
{
	private TOTP()
	{
	}

    private static byte[] hexStr2Bytes(String hex)
    {
        byte[] bArray = new BigInteger("10" + hex,16).toByteArray();
        byte[] ret = new byte[bArray.length - 1];
        
        for (int i = 0; i < ret.length; i++)
            ret[i] = bArray[i+1];
        return ret;
    }

    public static String generateTOTP(byte[] key, long time, String crypto, int digits)
    {
    	String result = null;
    	String timeString = Long.toHexString(Math.round(Math.floor(time / 30))).toUpperCase();
    	
        while (timeString.length() < 16)
            timeString = "0" + timeString;
      
        byte[] msg = hexStr2Bytes(timeString);
        
        byte[] hash = Crypto.hmac_sha(crypto, key, msg);
        int offset = hash[hash.length - 1] & 0xf;
        int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16) | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);
        int otp = (int) (binary % Math.pow(10, digits));
        
        result = Integer.toString(otp);
        while (result.length() < digits)
            result = "0" + result;
        return result;
    }
}