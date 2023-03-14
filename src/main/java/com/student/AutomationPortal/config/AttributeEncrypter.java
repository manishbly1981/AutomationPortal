package com.student.AutomationPortal.config;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;

public class AttributeEncrypter implements AttributeConverter<String, String>{

	private static final String AES= "AES";
	private static final String SECRECT= "secret-key-12345";
	private final Key key;
	private final Cipher cipher;
	

	public AttributeEncrypter() throws NoSuchAlgorithmException, NoSuchPaddingException {
		key= new SecretKeySpec(SECRECT.getBytes(), AES);
		cipher= Cipher.getInstance(AES);
	}

	@Override
	public String convertToDatabaseColumn(String attribute) {
		try {
			if(attribute!=null) {
				cipher.init(Cipher.ENCRYPT_MODE, key);
				return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
			}else {
				return null;
			}
		}catch(InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new IllegalStateException();
		}
	}
	
	@Override
	public String convertToEntityAttribute(String dbData) {
		try {
			if (dbData!=null) {
				cipher.init(Cipher.DECRYPT_MODE, key);
				return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
			}else {
				return null;
			}
		}catch(InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new IllegalStateException();
		}
	}

}
