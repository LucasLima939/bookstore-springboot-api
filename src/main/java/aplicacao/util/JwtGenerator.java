package aplicacao.util;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;  
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;  
import io.jsonwebtoken.ExpiredJwtException;

public class JwtGenerator {
	
	static String signatureKey = "Bq6qyEBdGfeumWWdWvKrXxquSLT9eWxMJK05FSjBdUCVpX+SJJAnZm9ptVJHtBgXUJkDsDBcfo/u6jmoOJAHgVL8zG57wAZ0Cl1xPRQlCjBkJEmqBErRNbHaLzLaE/0Yhl5Oymxw9H+/7MVxbVhvlfgcvifc+yXDhHPCguHuBAc=";
	
	
	static String generate(String userLogin){	
		 try {
		  
		@SuppressWarnings("deprecation")
		String jwt = Jwts.builder().setIssuer("http://trustyapp.com/")  
		    .setSubject(userLogin)  
		    .setExpiration(new Date(System.currentTimeMillis() + (3600000 * 72)))
		    .claim("scope", "self api/buy")
		    .signWith(SignatureAlgorithm.HS256,Base64.decodeBase64(signatureKey))  
		    .compact();
		
		return jwt;
	 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	static String checkIsValid(String jwt, String userLogin){
		try {  
		    @SuppressWarnings({ "rawtypes", "deprecation" })
			Jws jwtClaims = Jwts.parser().setSigningKey(Base64.decodeBase64(signatureKey)).parseClaimsJws(jwt);  
		    Object body = jwtClaims.getBody();
		    return jwt;
		} 
		catch (Exception e) {
			if(e instanceof ExpiredJwtException) {
				return generate(userLogin);
			} else {
			    return null;				
			}
		}
	}
	/*
	private static byte[] getSignatureKey() {
		 KeyPair keyPair;
		try {
			keyPair = getKeyPair();
		
		  
         // data to be updated
         byte[] data = "test".getBytes("UTF8");

         // creating the object of Signature
         Signature sr = Signature.getInstance("SHA1WithRSA");

         // initializing the signature object with key pair
         // for signing
         sr.initSign(keyPair.getPrivate());

         // updating the data
         sr.update(data);

         // getting the signature byte
         // of an signing operation
         // by using method sign()
         byte[] bytes = sr.sign();
         return bytes;
         
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	static byte[] stringToByte(String strByte) {
		String[] byteValues = strByte.substring(1, strByte.length() - 1).split(",");
		byte[] bytes = new byte[byteValues.length];

		for (int i=0, len=bytes.length; i<len; i++) {
		   bytes[i] = Byte.parseByte(byteValues[i].trim());     
		}
		return bytes;		
	}
	
	private static KeyPair getKeyPair() throws NoSuchAlgorithmException
    {
  
        // creating the object of KeyPairGenerator
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
  
        // initializing with 1024
        kpg.initialize(1024);
  
        // returning the key pairs
        return kpg.genKeyPair();
    }
	*/

}
