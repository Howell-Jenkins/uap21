/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package de.unitrier.st.uap.w21.tram;


import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;



final public class Main {
    private static final Logger logger = LogManager.getLogger("Main");
    private Main() {
    }
    public static void main(String[] args) {

        final CommandLineParser commandLineParser = new DefaultParser();
        final Options options = new Options();
        options.addOption("d", "debug", false, "Turn on debug.");
        //options.addOption("o", "output", true, "Log output File.");
        try {
            CommandLine commandLine = commandLineParser.parse(options, args);

            if(commandLine.getArgList().size() > 0){
                String filePath = commandLine.getArgList().get(0);
                final TramTokenizer tramTokenizer = new TramTokenizer();
                tramTokenizer.parse(filePath);
                if(!tramTokenizer.hasError()){
                    if(commandLine.hasOption("d")){
                        Configurator.setRootLevel(Level.DEBUG);
                    }

                    AbstractMachine abstractMachine = new AbstractMachine();
                    abstractMachine.execute(tramTokenizer.getInstructions());
                    System.out.println("Result : " + abstractMachine.getResult());
                }else{
                    System.out.println("ERROR in File: "+tramTokenizer.getFilePath());
                    System.out.println("MESSAGE: " + tramTokenizer.getErrorMessage());

                }

            }else{
                final HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("[file]", options);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
