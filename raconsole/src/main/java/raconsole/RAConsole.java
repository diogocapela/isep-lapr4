package raconsole;

import raconsole.controller.AR01Controller;
import settings.Settings;

import java.util.Scanner;

public class RAConsole {


    public static void main(String[] args) {

        for (String s : args) {
            if ("-mysql".equals(s))
                Settings.DB_PU = "DATABASE_02";
            if ("-local".equals(s))
                Settings.DB_PU = "DATABASE_01";
        }

        System.out.println("RA CONSOLE. User: ");
        Scanner sc = new Scanner(System.in);
        String user = sc.nextLine();
        System.out.println("Password:<CLEAR TEXT ON PROMPT> ");
        String secret = sc.nextLine();

        AR01Controller ar01c = new AR01Controller();


        switch (ar01c.authenticateUser(user, secret)) {
            case -1: {
                System.out.println("This user does not exist"); //vuln: user enum
                break;
            }
            case 0: {
                System.out.println("Authenticated!");
                //CALLS HERE THE MENU!
                Menu.loop(user);
                break;
            }
            case 1: {
                System.out.println("Wrong password!");
                break;
            }
        }
    }

}
