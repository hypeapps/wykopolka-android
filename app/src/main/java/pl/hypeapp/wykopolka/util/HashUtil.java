package pl.hypeapp.wykopolka.util;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import pl.hypeapp.wykopolka.BuildConfig;

public class HashUtil {
    private static final String WYKOPOLKA_SECRET = BuildConfig.WYKOPOLKA_SECRET;

    public static String md5(String s) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")), 0, s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String generateApiSign(String... parameters) {
        String parametersImploded = StringUtils.join(parameters, ",");
        Log.e("STRING UTILS ", parametersImploded);
        String apiSign = md5(WYKOPOLKA_SECRET + parametersImploded);
        return apiSign;
    }
}
