package org.example.spimex;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.commons.cli.*;

@SpringBootApplication
public class SimpleCliAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SimpleCliAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Options options = new Options();
        options.addOption(Option.builder("a")
                .longOpt("action")
                .hasArg()
                .desc("Action to perform (greet or status)")
                .build());
        options.addOption(Option.builder("c")
                .longOpt("code")
                .hasArg()
                .desc("Code value for the action")
                .build());
        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("Show this help message")
                .build());

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.setSyntaxPrefix("Usage: ");
                formatter.setWidth(100);
                System.out.println("Command Line Tool\n");
                formatter.printHelp("java -jar app.jar", "\nAvailable options:", options, "\nExamples:\n" +
                        "  --action=greet --code=123\n" +
                        "  --action=status --code=456\n" +
                        "  --help", true);
                return;
            }

            if (cmd.hasOption("action")) {
                String action = cmd.getOptionValue("action");
                switch (action) {
                    case "greet":
                        System.out.println("Hello!");
                        break;
                    case "status":
                        String code = cmd.getOptionValue("code", "unknown");
                        System.out.println("Status code: " + code);
                        break;
                    default:
                        System.out.println("Unknown action: " + action);
                }
            }
        } catch (ParseException e) {
            System.err.println("Error parsing command line: " + e.getMessage());
        }
    }
}