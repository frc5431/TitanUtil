package frc.team5431.titan.mimic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

import frc.team5431.titan.core.misc.Logger;

public class Repeater {
    private static FileInputStream log = null;
    private static BufferedReader reader = null;
    private static final ArrayList<Stepper> pathData = new ArrayList<Stepper>();
    
    public static void prepare(final String fileName) {
        final String fName = String.format(Stepper.mimicFile, fileName);
        try {
            Logger.l("Loading the mimic file");
            if(!Files.exists(new File(fName).toPath())) {
                Logger.e("The requested mimic data was not found");
            }
            
            log = new FileInputStream(fName);
            InputStreamReader iReader = new InputStreamReader(log, StandardCharsets.US_ASCII);
            reader = new BufferedReader(iReader);
            pathData.clear(); //Clear all of the pathData
            
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    pathData.add(new Stepper(line));
                } catch (Exception e) {
                    Logger.ee("MimicData", e);
                }
            }
            
            try {
                reader.close();
            } catch (Exception e) {
                Logger.ee("Failed to close the mimic file", e);
            }
            Logger.l("Loaded the mimic file");
        } catch (IOException e) {
            Logger.ee("Mimic", e);
        }
    }

    public static ArrayList<Stepper> getData() {
        return pathData;
    }
}