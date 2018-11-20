package no.kristiania.pgr200.cli;

import no.kristiania.pgr200.core.Conference;
import no.kristiania.pgr200.core.Talk;
import no.kristiania.pgr200.http.HttpClient;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConferenceClient {

    private HttpClient client;
    private static Conference conference;

    public static void main(String[] args) {

        new ConferenceClient().run(args);

    }

    public void run(String[] args) {

        client = new HttpClient();
        conference = new Conference("PGR200", "A conference all about Java, Maven and lots of more fun");

        parseCommand(args);

    }

    public void parseCommand(String[] args) {

        if (args.length == 0) {
            System.out.println("Welcome to the " + conference.getTitle() + " conference 😸 Use the argument 'help' to get started");
            System.exit(0);
        }

        // TODO: Check if database is available:

        /**
         * Available commands
         */

        String input = args[0].toLowerCase();

        switch (input) {

            case "add":
                add(args);
                break;
            case "list":
                listAll(args);
                break;
            case "view":
                viewTalkById();
                break;
            case "help":
                printHelp();
                break;
            case "about":
                printAbout();
                break;
            case "quit":
                System.out.println("\u001B Finished running ☠️");
                System.exit(0);
                break;
            case "hackerman":
                printHackermanEasterEgg();
                break;
            case "halp":
                printHalp();
                break;
            case "😼":
            case "meow":
               printBark();
               break;
            case "fuglekassa":
               printFuglekassa();
               break;
            default:
                System.out.println("Command not recognized. Some help:");
                printHelp();
                break;

        }

    }

    /**
     * Adds a talk in the database
     */
    public void add(String[] args) {

        Talk talk = new Talk();

        // Checks if the user provided a title. If not, prompt for the title

        if ((args.length > 2) && (args[1].equals("-title"))) {
                talk.setTitle(args[2]);
        } else {

            System.out.println("✋ Adding a talk requires a title. What's the title?");

            Scanner userInput = new Scanner(System.in);
            talk.setTitle(userInput.nextLine());

        }

        System.out.println("👇 The talk's description?");
        Scanner userInput = new Scanner(System.in);

        talk.setDescription(userInput.nextLine().trim());

        System.out.println("👇 Finally - what is the talk's topic?");
        talk.setTopic(userInput.nextLine().trim());

        System.out.println("👍 Thanks!");
        System.out.println("Adding...");

        try {
            client.addTalkToServer(talk);
            System.out.println("\n✅ Added");

        } catch (IOException e) {
            System.out.println("❌ Couldn't create talk:");
            e.printStackTrace();

        }

    }

    /**
     * Lists up all the talks from the database
     */
    public void listAll(String[] args) {
        System.out.println("⏳ Listing all...");

        if (args.length > 2) {
            if (args[1].equals("-topic")) {
                listAllByTopic(args[2]);
                return;
            }

        }

        try {

            List<Talk> talks = client.listTalksFromServer();

            System.out.println("Found " + talks.size() + " talk(s)\n");

            for (Talk talk : talks) {
                System.out.println(talk);
                System.out.println();
            }


        } catch (Exception e) {
            System.out.println("❌ Couldn't list all talks:");
            e.printStackTrace();
        }

    }

    public void listAllByTopic(String topic) {

        System.out.println("⏳ Listing all with topic '" + topic + "'");

        try {

            List<Talk> talks = client.listTalksByTopicFromServer(topic);

            System.out.println("Found " + talks.size() + " talk(s)\n");

            for (Talk talk : talks) {
                System.out.println(talk);
                System.out.println();
            }


        } catch (Exception e) {
            System.out.println("❌ Couldn't list all talks:");
            e.printStackTrace();
        }



    }

    private void viewTalkById() {

        try {

            System.out.println("👇 Enter the id of the talk you want to view: (Number)");
            Scanner userInput = new Scanner(System.in);
            String id = userInput.nextLine().trim();

            System.out.println("\nResult:");
            System.out.println(client.getTalkById(Integer.parseInt(id)));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Prints the commands you can use in the terminal
     */
    public static void printHelp() {

        System.out.println();
        System.out.println("👋 Welcome to the PGR200 Conference");
        System.out.println("ℹ️ Version 1.0");
        System.out.println("👨 Created by Markus Jahnsrud and Jørgen Aasan‍");
        System.out.println("💻️ Here's an overview of the available commands\n");

        helpCodes.forEach((k, v) -> {
            System.out.println(k + ": " + v);
        });

        System.out.println();

    }

    public static void printAbout() {
        System.out.println("Welcome to " + conference.getTitle());
        System.out.println("The conference is about " + conference.getDescription());
        System.out.println("java.thanksForVisiting();");
    }

    /**
     * Easter eggs, get your easter eggs
     */

    public static void printHalp() {
        System.out.println("Compiler dog is here to halp you. Did you mean help?");
        System.out.println("  __    __\n" +
                "o-''))_____\\\\\n" +
                "\"--__/ * * * )\n" +
                "c_c__/-c____/");
    }


    private void printHackermanEasterEgg() {
        try {
            System.out.println("\n////////\n");
            System.out.println("Hacking your computer");

            for (int i = 0; i < 4; i++) {
                TimeUnit.SECONDS.sleep((long) 1);
                System.out.print(".");
            }

            System.out.println("Almost there...");

            for (int i = 0; i < 40; i++) {
                TimeUnit.MILLISECONDS.sleep(30);
                System.out.print("🤔");
            }

            for (int i = 0; i < 10; i++) {
                System.out.println();
            }

            System.out.println("Oh no! Look what we found on your device");
            System.out.println("  __    __\n" +
                    "o-''))_____\\\\\n" +
                    "\"--__/ * * * )\n" +
                    "c_c__/-c____/");
            System.out.println();

            System.out.println("Hackerman.didFinishHackingWithError: You're safe. For now.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void printBark() {
        System.out.println("BARK, BARK");
        System.out.println("^..^      /\n" +
                "/_/\\_____/\n" +
                "   /\\   /\\\n" +
                "  /  \\ /  \\");

    }

    public static void printFuglekassa() {
        try {
            System.out.println("Dra meg baklengs inn i fuglekassa");
            System.out.println("Sju og tredve mil nordover, litt øst og oppover, ligger Flåklypa, ei lita fjellbygd under himmelhvelvingen. Sjølberga med både dampysteri og campingplass");

            Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=wCwSyVwQTk8").toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method for view help
     */
    private static Map<String, String>helpCodes = new HashMap<>();
    static {

        helpCodes.put("add", "Adds a talk. Use -title 'arg' to get started");
        helpCodes.put("list", "Lists all talks. Use -topic 'arg' to filter by topic");
        helpCodes.put("view", "View info about a talk by using its ID");
        helpCodes.put("help", "Get an overview of available commands");
        helpCodes.put("about", "Displays information about your selected conference");
    }

}
