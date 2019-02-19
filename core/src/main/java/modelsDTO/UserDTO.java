package modelsDTO;

public class UserDTO {
    private String email;
    private String name;

    /**
     * No need for the other information as it is confidential
     */

    public UserDTO(String email, String nome) {
        this.email = email;
        this.name = nome;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
