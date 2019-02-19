package models;

import services.CryptoServices;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table
public class UserExterno {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Long version;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column
    private byte[] hashedPassword;
    @Column
    private byte[] salt;
    @Column
    private Token token;

    // Construtores
    //================================================================

    public UserExterno() {
    }

    public UserExterno(String email, String name, String passwordInClear) {
        if (email == null || name == null) {
            throw new IllegalArgumentException();
        }
        this.email = email;
        this.name = name;
        this.createPassword(passwordInClear);
    }

    // Métodos
    //================================================================

    @Override
    public String toString() {
        return "UserExterno{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", name='" + name + '\'' +
            ", version=" + version +
            '}';
    }

    /**
     * Validate password
     */
    private Boolean validatePassword(String password) {

        byte[] cmp = CryptoServices.hashedString(password, this.salt);
        if (Arrays.equals(cmp, hashedPassword)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }

    }

    /**
     * Function to set up a valid password on the user.
     * This function already calls CryptoServices to setup the hashed+salted pass using PBKDF2WithHmacSHA1 alg.
     */
    Boolean createPassword(String password) {
        this.salt = CryptoServices.generateRandomSalt();
        if (salt != null)
            this.hashedPassword = CryptoServices.hashedString(password, this.salt);
        else
            return Boolean.FALSE;


        if (this.hashedPassword == null) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * Create and assign the tokens for the external users
     */
    public String loginToken(String passswd) {
        this.token = null; // destroy our current token
        if (validatePassword(passswd)) {
            this.token = new Token();
            return this.token.getToken();
        } else {
            this.token = null;
            return "NOT VALID PASSWORD";
        }
    }

}
