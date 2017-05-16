package org.lrs.kmodernlrs;

import org.apache.commons.codec.binary.Base64;
import org.lrs.kmodernlrs.controllers.security.UserAccount;

public class TestHelper {
    public static String createBasicAuthHash(String userName, String password) {
        return new String(Base64.encodeBase64((userName+":"+password).getBytes()));
    }

    String mockUserJson = "{\"name\":\"tester\",\"email\":\"test@testing.com\", \"password\":\"hideThis\"," +
            "\"role\":\"user\", \"active\":\"true\"}";

    public static UserAccount createMockUserAccount(String userName, String password){
        UserAccount mockUserAccount = new UserAccount();
        mockUserAccount.setActive(true);
        mockUserAccount.setName(userName);
        mockUserAccount.setPassword(password);
        mockUserAccount.setRole("USER");
        return mockUserAccount;
    }
}
