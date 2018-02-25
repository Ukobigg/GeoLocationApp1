package aisha.geolocationapp.Services;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by UkoDavid on 08/11/2017.
 */

public class DateandTime {
    //Get or Generate Date
    Date todayDate = new Date();

    //Get an instance of the formatter
    DateFormat dateFormat = DateFormat.getDateTimeInstance();

    //If you want to show only the date then you will use
    //DateFormat dateFormat = DateFormat.getDateInstance();

    //Format date
    String todayDateTimeString = dateFormat.format(todayDate);

    public String getTodayDateTimeString() {
        return todayDateTimeString;
    }
}
