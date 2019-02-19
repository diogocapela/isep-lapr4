package models;

import modelsDTO.UserDTO;
import services.CryptoServices;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table
public class User implements Serializable {

    // Variáveis de Instância
    //================================================================

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

    // Construtores
    //================================================================

    public User() {
    }

    public User(String email, String name, String passwordInClear) {
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
        return "User{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", name='" + name + '\'' +
            ", version=" + version +
            '}';
    }

    /**
     * Validate password
     */
    public Boolean validatePassword(String password) {

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

    public UserDTO toDTO() {
        return new UserDTO(this.email, this.name);
    }

}
