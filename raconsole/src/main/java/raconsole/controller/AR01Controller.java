package raconsole.controller;


import audit.Auditlog;
import services.UserService;

public class AR01Controller {

    private static Auditlog al = new Auditlog();
    UserService US;

    public AR01Controller() {
        US = new UserService();
    }

    public Integer authenticateUser(String nome, String secret) {
        al.registerEvent(nome, "AR01Controller", "authenticateUser", "Tried to authenticate.", "");

        if (!US.existsOnDbByTitle(nome)) {
            al.registerEvent(nome, "AR01Controller", "authenticateUser", "Tried to authenticate (wrong user)", "");
            return -1; //"User does not exist!";
        }

        if (US.authenticateUser(nome, secret)) {
            al.registerEvent(nome, "AR01Controller", "authenticateUser", "Authenticated!", "");
            return 0; // "Authenticated!";
        } else {
            al.registerEvent(nome, "AR01Controller", "authenticateUser", "Wrong password!", "");
            return 1; //"Not authenticated! - wrong password";
        }

    }

    public Integer mockAuthenticateUser(String nome, String secret) {
        al.registerEvent(nome, "AR01Controller", "MockAuthenticateUser", "Tried to authenticate MOCK.", "Mock");

        if (!US.existsOnDbByTitle(nome)) {
            al.registerEvent(nome, "AR01Controller", "MockauthenticateUser", "Tried to authenticate (wrong user)", "Mock");
            return -1; //"User does not exist!";
        }

        if (US.mockAuthenticateUser(nome, secret)) {
            al.registerEvent(nome, "AR01Controller", "MockauthenticateUser", "Authenticated! MOCK", "Mock");
            return 0; // "Authenticated!";
        } else {
            al.registerEvent(nome, "AR01Controller", "MockauthenticateUser", "Wrong password! MOCK", "Mock");
            return 1; //"Not authenticated! - wrong password";
        }
    }
}
