package view;

import java.text.*;
import java.util.*;

import javax.swing.JFormattedTextField.*;

// a DateLabelFormatter class that extends the AbstractFormatter
public class DateLabelFormatter extends AbstractFormatter {

    // formatting the pattern the date needs to be displayed in
    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
                if (value instanceof Date) {
                    return dateFormatter.format(value); 
                }else if (value instanceof Calendar) {
                    Calendar cal = (Calendar) value;
                    return dateFormatter.format(cal.getTime());
                }
        }

        return "";
    }

} // end of date label formatter class
