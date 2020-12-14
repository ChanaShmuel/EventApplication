package myApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import myApp.events.Event;
import myApp.events.EventsStatistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Generator implements Runnable {

    public static final Logger LOGGER = Logger.getLogger(Generator.class.getName());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void run() {
        try {
            produceEvents();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Can't run the exe file");
        }
    }

    private void produceEvents() throws IOException {
        Runtime rt = Runtime.getRuntime();
        String generatorPath = new File("src/main/resources/generator.exe").getAbsolutePath();
        String[] exeFile = {generatorPath, "-get t"};
        Process proc = rt.exec(exeFile);

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        // Read the output from the command
        LOGGER.log(Level.INFO, "start reading the input from exe file:\n");
        String line;
        try {
            while ((line = stdInput.readLine()) != null) {
                try {
                    // LOGGER.log(Level.INFO, "Convest the input from jason to Event object  :\n");
                    Event event = objectMapper.readValue(line, Event.class);
                    EventsStatistics.addEvent(event);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, "Invalid json object" + line, ex.getMessage());
                } finally {
                    //System.out.println("check...");
                    EventsStatistics.removeEventsThatMoreThan_x_Sec();
                }
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }
}

