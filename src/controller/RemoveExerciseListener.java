package controller;

import model.*;
import view.*;

import java.awt.event.*;

import io.LogHandler;

// ChangeListener will process the registered action events
class RemoveExerciseListener implements ActionListener {

    private DMModel model;
    private DMView view;
    private DMController controller;

    public RemoveExerciseListener(DMModel model, DMView view, DMController controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String date = this.view.getDate();
        Log log = this.model.getLogOnDate(date);
        if (log.getExercise().size() > 0 && !this.view.getJlLogExercise().isSelectionEmpty()) {
            log.removeExercise(this.view.getJlLogExercise().getSelectedIndex());
            this.view.getExerciseModel().remove(this.view.getJlLogExercise().getSelectedIndex());
            LogHandler.saveLogs();

            // updateExerciseLogs
            this.controller.updateLogData(log);
        }

    }

}