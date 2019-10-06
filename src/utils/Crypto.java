package utils;

import java.lang.reflect.UndeclaredThrowableException;
import java.security.GeneralSecurityException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Crypto
{
	private Crypto()
	{
	}
	
	public static byte[] hmac_sha(String crypto, byte[] keyBytes, byte[] text)
    {
        try
        {
            Mac 		  hmac = Mac.getInstance(crypto);
            SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
            
            hmac.init(macKey);
            return hmac.doFinal(text);
        }
        catch (GeneralSecurityException gse)
        {
            throw new UndeclaredThrowableException(gse);
        }
    }
}
