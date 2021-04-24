package com.common;


import com.context.AppContext;
import com.dto.ShortCodeData;
import com.error.CustomException;
import com.model.ShortCodeGenRequest;
import com.model.ShortCodeGenResponse;
import com.repository.ShortCodeHandler;
import org.apache.commons.validator.routines.UrlValidator;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Service
public class Utils {
    @Autowired
    private ShortCodeHandler db;
    private static String ERRCODE = "eerCode";
    private static String ERRMSG = "errMsg";

    private static ShortCodeGenResponse generateShortCode(ShortCodeGenRequest shortCodeGenRequest) throws Exception {
        validateUrl(shortCodeGenRequest);
        if (shortCodeGenRequest != null) {
            String hash = generateMd5Hash(shortCodeGenRequest.getUrl(), shortCodeGenRequest.getUserId());
            insertIntoDb(shortCodeGenRequest.getUrl(), shortCodeGenRequest.getUserId(), generateBase64Encoding(hash).substring(0, 6));
            ShortCodeGenResponse resp = new ShortCodeGenResponse();
            resp.setShortUrl(generateBase64Encoding(hash).substring(0, 6));
            System.out.println(resp);
            return resp;
        } else {
            throw new CustomException(CustomException.INVALID_DATA);
        }
    }

    private static Boolean validateUrl(ShortCodeGenRequest shortCodeGenRequest) throws Exception {
        UrlValidator urlValidator = new UrlValidator();

        if (shortCodeGenRequest.getUserId() == null) {
            throw new CustomException(CustomException.INVALID_USER_ID);
        }
        if (!urlValidator.isValid(shortCodeGenRequest.getUrl())) {
            throw new CustomException(CustomException.INAVLID_URL);
        }
        return true;
    }

    public static Object genShortCode(ShortCodeGenRequest shortCodeGenRequest) {
        try {
            ShortCodeGenResponse resp = generateShortCode(shortCodeGenRequest);
            resp.setRespCode(CustomException.SUCCESSFUL);
            resp.setRespMessage(CustomException.getDescription(CustomException.SUCCESSFUL));
            return resp;
        } catch (CustomException e) {
            JSONObject err = new JSONObject();
            err.put(ERRCODE, e.getErrorCode());
            err.put(ERRMSG, e.getErrorMessage());
            return err;
        } catch (Exception e) {
            JSONObject err = new JSONObject();
            err.put(ERRCODE, CustomException.UNKNOWN_ERROR);
            err.put(ERRMSG, e.getMessage());
            return err;
        }

    }

    private static String generateMd5Hash(String url, String userId) {
        try {
            // Static getInstance method is called with hashing MD5
            String input = url + userId + UUID.randomUUID().toString();
            System.out.println(input);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateBase64Encoding(String input) {
        String encodedString = Base64.getEncoder().encodeToString(input.getBytes());
        return encodedString;
    }

    private static void insertIntoDb(String url, String userId, String shortCode) {
        long expiryTime = System.currentTimeMillis() / 1000 + AppContext.getExpiryTime();
        ShortCodeData dbData = new ShortCodeData(url, shortCode, expiryTime, userId);
        System.out.println(dbData.toString());
        AppContext.getHandler().save(dbData);

    }

    public static String fetchOriginalUrl(String shortCode) throws Exception {
        ShortCodeData s = AppContext.getHandler().findByShortCode(shortCode);
        long currentTimeStamp = System.currentTimeMillis() / 1000;
        if (s.getExpiry_time() >= currentTimeStamp)
            return s.getOriginal_url();
        else {
            AppContext.getHandler().delete(s);
            throw new CustomException(CustomException.SHORT_CODE_EXPIRED);
        }
    }

    public static Object redirectUrl(String shortCode) {
        try {
            return new RedirectView(fetchOriginalUrl(shortCode));
        } catch (CustomException e) {
            JSONObject err = new JSONObject();
            err.put(ERRCODE, e.getErrorCode());
            err.put(ERRMSG, e.getErrorMessage());
            return err;
        } catch (Exception e) {
            JSONObject err = new JSONObject();
            err.put(ERRCODE, CustomException.UNKNOWN_ERROR);
            err.put(ERRMSG, e.getMessage());
            return err;
        }
    }

    private void save(ShortCodeData dbData) {
        System.out.println(db);
        db.save(dbData);
    }
}

