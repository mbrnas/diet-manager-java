package controller;

import java.awt.event.*;

import model.*;
import view.*;

class DateListener implements ActionListener {

    private DMModel model;
    private DMView view;
    private DMController controller;

    public DateListener(DMModel model, DMView view, DMController controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {

        String date = this.view.getDate();

        Log log = this.model.getLogOnDate(date);

        this.controller.updateLogData(log);

    }

}