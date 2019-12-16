package Interface;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class DateFunctions {
    public String getDateInRUS(DateTime d){
        return  d.getDayOfMonth() + "." + d.getMonthOfYear() + "." +  d.getYear();
    }

    public String getDateInRUS(LocalDate d){
        return d.getDayOfMonth() + "." + d.getMonthOfYear() + "." +  d.getYear();
    }
}
