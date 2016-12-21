package pl.hypeapp.wykopolka.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import pl.hypeapp.wykopolka.BuildConfig;

public class HashUtil {
    private static final String WYKOPOLKA_SECRET = BuildConfig.WYKOPOLKA_SECRET;

    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            int i = (b & 0xFF);
            if (i < 0x10) hex.append('0');
            hex.append(Integer.toHexString(i));
        }
        return hex.toString();
    }

    public static String generateApiSign(String... parameters) {
        String parametersImploded = StringUtils.join(parameters, ",");
        String apiSign = md5(WYKOPOLKA_SECRET + parametersImploded);
        return apiSign;
    }
}
