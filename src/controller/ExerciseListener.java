package controller;

import io.*;
import model.*;
import view.*;

import java.awt.event.*;
import java.util.*;
import java.util.stream.*;

// ChangeListener will process the registered action events
class ExerciseListener implements ActionListener {

    private DMModel model;
    private DMView view;
    private DMController controller;

    public ExerciseListener(DMModel model, DMView view, DMController controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {

        String exerciseName = this.view.getExerciseName();
        String exerciseDuration = this.view.getExerciseDuration();
        double minutes = 0;

        try {
            minutes = Double.parseDouble(exerciseDuration);
        } catch (NumberFormatException nfe) {
            this.view.showError("Please enter a valid duration in minutes");
            return;
        }

        String date = this.view.getDate();

        Log log = this.model.getLogOnDate(date);

        ArrayList<Exercise> exercises = this.model.getExercises();

        for (Exercise exercise : exercises) {
            if (exercise.getName().equals(exerciseName)) {

                log.addExercise(exercise, minutes);

                break;
            }
        }

        // System.out.println(this.model.getLog());
        this.view.addExercise(exerciseName + ", " + minutes);

        List<String> exerciseDurationList = Arrays.stream(this.view.getExerciseModel().toArray())
                .map(Object::toString)
                .toList()
                .stream()
                .flatMap(str -> Arrays.stream(str.split(",")))
                .map(splitStr -> splitStr.trim())
                .collect(Collectors.toList());

        // Alternative way of writing
        // Object[] objs = this.view.getModel().toArray();
        // List<String> strings = new ArrayList<>();
        // for (Object obj : objs)
        // strings.add(obj.toString());
        // List<String> exerciseDurationsList = new ArrayList<>();
        // for (String string : strings) {
        // String[] exerciseNameDuration = string.split(",");
        // exerciseDurationsList.add(exerciseNameDuration[0].trim());
        // exerciseDurationsList.add(exerciseNameDuration[1].trim());
        // }

        double caloriesBurned = 0;

        for (Exercise exercise : exercises) {
            for (int i = 0; i < exerciseDurationList.size(); i += 2) {
                if (exercise.getName().equals(exerciseDurationList.get(i))) {

                    caloriesBurned += this.controller.calcCaloriesBurnt(exercise,
                            Double.parseDouble(this.view.getWeight()),
                            Double.parseDouble(exerciseDurationList.get(i + 1)));

                }
            }
        }

        this.view.setExerciseCaloriesBurnedCount(caloriesBurned);

        double netCalories = this.controller.calcNetCalories(this.view.getCaloriesCount(), caloriesBurned);

        this.view.setNetCaloriesTextField(netCalories);
        this.view.setGoalNetCaloriesTextField(log.getCalorieLimit());

        LogHandler.saveLogs();
    }

}