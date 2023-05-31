package controller;

import java.awt.event.*;

import view.*;

public class AddExerciseListener implements ActionListener {

    private DMView view;

    public AddExerciseListener(DMView view) {
        this.view = view;
    }

    public void actionPerformed(ActionEvent e) {

        this.view.askNewExercise();
    }
}
