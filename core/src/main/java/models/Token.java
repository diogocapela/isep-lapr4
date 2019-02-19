package models;

import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Calendar;

@Embeddable
@Table
public class Token {
    private final int expMinutes = 5;
    private String token;
    private Calendar createTime;

    public Token() {
        this.token = Token.generateRandomBase64Token(64);
        this.createTime = createTime;
        this.createTime.add(Calendar.MINUTE, expMinutes /*5 min of tiemout*/);
    }

    private static String generateRandomBase64Token(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token);
    }

    public boolean isTokenValid() {
        if (Calendar.getInstance().after(this.createTime)) {
            //expired!
            return false;
        } else {
            //update timeout
            this.createTime = Calendar.getInstance();
            this.createTime.add(Calendar.MINUTE, expMinutes /*5 min of tiemout*/);
            return true;
        }
    }

    public String getToken() {
        return this.token;
    }

}
