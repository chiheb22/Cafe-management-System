package com.ck.cafe_back.utils;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class CafeUtils {
    private CafeUtils() {
    }

    public static ResponseEntity<String> getResponse(String message, HttpStatusCode httpStatusCode) {
        return new ResponseEntity<String>("{\"message\":\"" + message + "\"}", httpStatusCode);
    }
}
