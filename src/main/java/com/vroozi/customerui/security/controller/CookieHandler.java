package com.vroozi.customerui.security.controller;


import com.vroozi.customerui.config.AppConfig;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;

/**
 * User: SURYA MANDADAPU
 * Date: 2/7/13
 * Time: 4:13 PM
 */
@Component
public class CookieHandler {
    private final Logger logger = LoggerFactory.getLogger(CookieHandler.class);

    @Autowired
    AppConfig appConfig;

    public boolean isValidVSSCookie(String vss) throws Exception {
//        Cookie cookie = getCookie(request,appConfig.cookieVSD);
//        if (cookie != null) {
//            String vss = decrypt(cookie.getValue());
//
//            logger.debug("VSS cookie value:"+ vss);
//            return true;
//        }
        return true;
    }

    public String getCookie(HttpServletRequest request, String name) throws Exception{
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return decrypt(cookie.getValue());
                }
            }
        }

        return null;
    }


    private String decrypt(String data) throws Exception{
        // password
        String password = appConfig.encryptionAESKey;
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(password.getBytes("UTF8"));

        // setup cipher
        SecretKeySpec keySpec = new SecretKeySpec(digest, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        cipher.init(Cipher.DECRYPT_MODE, keySpec);
         //Hex.decodeHex(encryptedData.);
        byte[] encryptedData = fromHexString(data);
        byte[] decryptedData = cipher.doFinal(encryptedData);

        String originalString = new String( decryptedData );
        return originalString;
        //getBytes("UTF-8"));
       // return  Hex.decodeHex(decryptedData.toCharArray());

//        var decipher = crypto.createDecipher('aes-128-ecb', settings.cookieEncryptionKey);
//        var dec = decipher.update(crypted,'hex','utf8');
//        dec += decipher.final('utf8');
//        return dec;


    }

    private static byte[] fromHexString(final String encoded) {
        if ((encoded.length() % 2) != 0)
            throw new IllegalArgumentException("Input string must contain an even number of characters");

        final byte result[] = new byte[encoded.length()/2];
        final char enc[] = encoded.toCharArray();
        for (int i = 0; i < enc.length; i += 2) {
            StringBuilder curr = new StringBuilder(2);
            curr.append(enc[i]).append(enc[i + 1]);
            result[i/2] = (byte) Integer.parseInt(curr.toString(), 16);
        }
        return result;
    }


}
