package controller;

import model.*;
import view.*;

import java.awt.event.*;

import io.LogHandler;

// ChangeListener will process the registered action events
class RemoveFoodLogListener implements ActionListener {

    private DMModel model;
    private DMView view;
    private DMController controller;

    public RemoveFoodLogListener(DMModel model, DMView view, DMController controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String date = this.view.getDate();
        Log log = this.model.getLogOnDate(date);

        if (log.getFood().size() > 0 && !this.view.getJlLogFood().isSelectionEmpty()) {

            log.removeFood(this.view.getJlLogFood().getSelectedIndex());
            this.view.getFoodModel().remove(this.view.getJlLogFood().getSelectedIndex());

            LogHandler.saveLogs();

            // updateExerciseLogs
            this.controller.updateLogData(log);
        }

    }

}