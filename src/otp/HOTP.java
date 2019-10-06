package otp;

import utils.Crypto;

public class HOTP
{
	private HOTP()
    {
    }
    
    public static String generateHOTP(byte[] key, long counter, String crypto, int digits)
    {
    	String result = null;
        byte[] text = new byte[8];
        
        for (int i = text.length - 1; i >= 0; i--)
        {
            text[i] = (byte) (counter & 0xff);
            counter >>= 8;
        }

        byte[] hash = Crypto.hmac_sha(crypto, key, text);
        int offset = hash[hash.length - 1] & 0xf;
        int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16) | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);
        int otp = (int) (binary % Math.pow(10, digits));

        result = Integer.toString(otp);
        while (result.length() < digits)
        	result = "0" + result;
        return result;
    }
}