package io;

import model.*;

import java.io.*;
import java.util.*;

public class ExerciseHandler {
    private static ArrayList<Exercise> exercise = new ArrayList<Exercise>();

    private static String FILE_PATH = null;

    public static ArrayList<Exercise> load(String fileName) {

        Scanner scan = null;
        FILE_PATH = "csv/" + fileName;

        try {

            File exerciseFile = new File(FILE_PATH);

            if (!exerciseFile.exists()) {
                exerciseFile.createNewFile();
            }

            scan = new Scanner(new BufferedReader(new FileReader(exerciseFile)));

            while (scan.hasNextLine()) {

                String line = scan.nextLine();
                String[] attributes = line.split(",");

                try {

                    String name = attributes[1];
                    double calories = Double.parseDouble(attributes[2]);

                    Exercise item = new Exercise(name, calories);

                    exercise.add(item);

                } catch (NumberFormatException nfe) {
                    System.out.println("Error when loading");
                    System.exit(0);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (scan != null) {
                scan.close();
            }
        }

        return exercise;
    }

    public static void saveExerciseFile() {

        File exerciseCSV = new File(FILE_PATH);

        try {

            // instantiate a print writer which will write to the file to save the input
            // data
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(exerciseCSV, false)));

            // loop through the map of all basic food entries
            for (Exercise exercise : exercise) {

                // write to the file and add the char 'f' that marks this as a basic food entry
                // in the file
                pw.println("e," + exercise.toString());

            }

            // close the print writer
            pw.close();

        } catch (

        IOException e) {

            e.printStackTrace();
        }
    } // end of save to food file method
      // saving to the log.csv file
}
