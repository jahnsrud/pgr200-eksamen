package no.kristiania.pgr200.server;

import no.kristiania.pgr200.core.Utils;

import java.io.IOException;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {

        if (args.length > 0) {
            if (args[0].equals("resetdb")) {
                resetDb();
            }
        }

        try {
            new HttpServer(Utils.DEFAULT_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Resets the whole DB.
     * Will prompt a confirmation dialogue (to prevent curious users from deleting everything)
     */

    public static void resetDb() {

        System.out.println("Reset? (y/n)");

        Scanner userInput = new Scanner(System.in);

        String input = userInput.nextLine();

        if (input.equalsIgnoreCase("y")) {

            System.out.println("✅ Reset");

        } else {
            System.out.println("❌ Cancelled");
        }

    }
}