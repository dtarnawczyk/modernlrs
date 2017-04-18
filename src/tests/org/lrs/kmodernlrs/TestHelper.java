package org.lrs.kmodernlrs;

import org.apache.commons.codec.binary.Base64;

public class TestHelper {
    public static String createBasicAuthHash(String userName, String password) {
        return new String(Base64.encodeBase64((userName+":"+password).getBytes()));
    }
}
