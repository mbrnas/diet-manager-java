package controller;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import model.*;
import view.*;

/**
 * The Controller class handles user interaction with listeners. It ensure that
 * the Model and the View are called when needed. The class contains inner
 * classes which implement the ActionListener interface so as to catch all
 * changes and actions taking place in the user's interaction with the UI.
 *
 */
public class DMController {

    private DMModel model;
    private DMView view;

    public DMController(DMModel model, DMView view) {
        this.model = model;
        this.view = view;

        // Creating new listeners and adding them to the view
        this.view.addDateListener(new DateListener(this.model, this.view, this));
        this.view.addLogListener(new LogListener(this.model, this.view));
        this.view.removeExerciseLogListener(new RemoveExerciseListener(this.model, this.view, this));
        this.view.addIngredientListener(new IngredientListener(this.view));
        this.view.addQuantityIngredientListener(new QuantityIngredientListener(this.view));
        this.view.addSaveNewFoodListener(new SaveNewFoodListener(this.model, this.view));
        this.view.addRecipeNameListener(new RecipeNameListener(this.view));
        this.view.addRecipeListener(new SaveNewRecipeListener(this.model, this.view));
        this.view.addQuantityListener(new QuantityListener(this.view));
        this.view.removeFoodLogListener(new RemoveFoodLogListener(this.model, this.view, this));

        this.view.addNewFoodEntryListener(new NewFoodEntryListener(this.view));
        this.view.addNewFoodListener(new AddFoodListener(this.view));
        this.view.addNewExerciseListener(new AddExerciseListener(this.view));

        this.view.addExerciseListener(new ExerciseListener(this.model, this.view, this));
        this.view.saveExerciseListener(new SaveExerciseListener(this.model, this.view));

        LocalDate today = LocalDate.now();

        this.view.setDatePickerDate(today.getYear(), today.getMonthValue() - 1, today.getDayOfMonth());
        this.view.updateComboBoxes(this.model.getFoodNameList());
        this.view.updateExerciseBox(this.model.getExercises());

        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("u,MM,dd");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("u-MM-dd");
        String date = today.format(monthFormatter);
        String formattedDate = today.format(dateFormatter);
        this.view.getDatePicker().getJFormattedTextField().setText(formattedDate);
        Log log = this.model.getLogOnDate(date);

        this.updateLogData(log);
    }

    public void updateLogData(Log log) {

        double weight = log.getWeight();
        double calorieLimit = log.getCalorieLimit();
        double calorieIntake = 0;
        double fatIntake = 0;
        double proteinIntake = 0;
        double carbohydrateIntake = 0;

        double caloriesBurned = 0;

        ArrayList<String> dateFoods = new ArrayList<>();
        ArrayList<String> dateExercises = new ArrayList<>();

        
        ArrayList<Food> foods = log.getDailyFoodConsumed();
        ArrayList<Double> foodQuantity = log.getDailyQuantityConsumed();

        for (int i = 0; i < foods.size(); i++) {

            Food food = foods.get(i);
            Double quantity = foodQuantity.get(i);

            dateFoods.add(food.getName() + ", " + quantity);
            calorieIntake += food.getCalories() * quantity;
            fatIntake += food.getFat() * quantity;
            carbohydrateIntake += food.getCarbs() * quantity;
            proteinIntake += food.getProtein() * quantity;
        }

        this.view.setLogFood(dateFoods);
        this.view.setLogTextFields(weight, calorieLimit, calorieIntake, fatIntake, carbohydrateIntake,
                proteinIntake);

        ArrayList<Exercise> exercises = log.getExercise();
        ArrayList<Double> exerciseDuration = log.getDuration();

        for (int i = 0; i < exercises.size(); i++) {

            Exercise exercise = exercises.get(i);
            Double duration = exerciseDuration.get(i);

            dateExercises.add(exercise.getName() + ", " + duration);
            caloriesBurned += calcCaloriesBurnt(exercise, weight, duration);

        }

        double netCalories = calcNetCalories(calorieIntake, caloriesBurned);

        this.view.setLogExercises(dateExercises);
        this.view.setExpandedCaloriesTextField(caloriesBurned);
        this.view.setNetCaloriesTextField(netCalories);
        this.view.setGoalNetCaloriesTextField(calorieLimit);
    }

    public double calcCaloriesBurnt(Exercise exercise, double weight, double duration) {

        double exerciseCalories = exercise.getCalories();

        return Math.round(exerciseCalories * weight * (duration / 60.0));

    }

    public double calcNetCalories(double calorieIntake, double caloriesBurned) {

        return calorieIntake - caloriesBurned;
    }

}