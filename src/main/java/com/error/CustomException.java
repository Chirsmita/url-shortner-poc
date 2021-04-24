package com.error;

import java.util.HashMap;
import java.util.Map;
/*
Class defined to handle custom exceptions
 */
public class CustomException extends RuntimeException {
    private String errorCode;
    private String errorMessage;
    private static final long serialVersionUID = 1L;
    private static final Map<String, String> errorList;

    public static final String UNKNOWN_ERROR = "XXXX";
    public static final String SUCCESSFUL = "0000";
    public static final String INAVLID_URL = "0001";
    public static final String INVALID_DATA = "0002";
    public static final String INVALID_USER_ID = "0003";
    public static final String SHORT_CODE_EXPIRED = "0004";

    static {
        errorList = new HashMap<>();
        errorList.put(SUCCESSFUL, "Request was successful.");
        errorList.put(INAVLID_URL, "Invalid URL.");
        errorList.put(INVALID_DATA, "Invalid Data.");
        errorList.put(UNKNOWN_ERROR, "Unknown error");
        errorList.put(INVALID_USER_ID, "Invalid user Id");
        errorList.put(SHORT_CODE_EXPIRED, "Short code expired");
    }
    public CustomException(String errorCode) {
        super(getDescription(errorCode));
        this.errorCode = errorCode;
        this.errorMessage = getDescription(errorCode);
    }
    public static String getDescription(String message) {
        try {
            return errorList.get(message);
        } catch (Exception e) {
            return null;
        }
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
