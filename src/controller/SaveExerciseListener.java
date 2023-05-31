package controller;

import io.*;
import model.*;
import view.*;

import java.awt.event.*;
import java.util.ArrayList;

class SaveExerciseListener implements ActionListener {

    private DMModel model;
    private DMView view;

    public SaveExerciseListener(DMModel model, DMView view) {
        this.model = model;
        this.view = view;
    }

    public void actionPerformed(ActionEvent e) {

        String exerciseName = this.view.getNewExerciseName();
        String caloriesText = this.view.getNewExerciseCalories();
        double calories = 0.0;
        ArrayList<Exercise> exercises = this.model.getExercises();
        for (Exercise exercise : exercises) {
            if (exerciseName.equals(exercise.getName())) {
                this.view.showError("Name already exists");
                return;
            }
        }
        try {
            calories = Double.parseDouble(caloriesText);
        } catch (NumberFormatException nfe) {
            this.view.showError("Please enter valid new food nutritional values");
            return;
        }
        Exercise exercise = new Exercise(exerciseName, calories);
        this.model.getExercises().add(exercise);
        this.view.updateExerciseBox(exercises);
        this.view.showSuccess("New Exercise added: " + exerciseName);
        ExerciseHandler.saveExerciseFile();
    }
}