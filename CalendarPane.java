package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class CalendarPane {
    private BorderPane boarderPane;
    private GridPane grid;
    private GregorianCalendar calendar;
    private int monthCount;
    private int index_DayOfWeek;
    private Text monthYear;
    private String weekdays[] = {"Sunday", "Monday", "Tuesday ", "Wednesday ", "Thursday", "Friday", "Saturday"};
    private int year;
    private Label dates_lbl[];

    private GregorianCalendar prevMonthCalendar;
    private GregorianCalendar nextMonthCalendar;
    public CalendarPane() {
        boarderPane = new BorderPane();
        grid = new GridPane();
        calendar = new GregorianCalendar();
        monthYear = new Text();
        monthCount = 0;
        index_DayOfWeek = 0;
        dates_lbl = new Label[43];
    }

    public BorderPane start(){
        //setting the calendar to current month and year
        year = calendar.get(Calendar.YEAR);
        calendar.set(year, Calendar.MONTH + getMonth(), 1);

        prevMonthCalendar = (GregorianCalendar) calendar.clone();
        nextMonthCalendar = (GregorianCalendar) calendar.clone();

        prevMonthCalendar.set(year, Calendar.MONTH + getMonth() -1, 1);
        nextMonthCalendar.set(year, Calendar.MONTH + getMonth() +1, 1);

        monthYear.setText(header(calendar));
        monthYear.setTextAlignment(TextAlignment.CENTER);

        boarderPane.setTop(monthYear);
        boarderPane.setAlignment(monthYear, Pos.TOP_CENTER);

        grid.setAlignment(Pos.TOP_CENTER);

        grid.setHgap(70);
        grid.setVgap(25);
        grid.setPadding(new Insets(25, 0, -20, -40));

        calendarBuilder();

        boarderPane.setCenter(grid);
        grid.setAlignment(Pos.TOP_CENTER);
        return boarderPane;

    }
    public static String[] getMonths() {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return months;
    }
    //returns Month and Year
    public String header(Calendar calendar){
        String[] months = getMonths();
        String monthYear = (months[calendar.get(Calendar.MONTH)] + ", " +
                calendar.get(Calendar.YEAR));
        return monthYear;
    }
    public void setMonth(int monthNum){
        this.monthCount = monthNum;
    }

    public int getMonth() {
        return monthCount;
    }

    private void calendarBuilder() {

        Label week_lbl[] = new Label[7];
        for (int i = 0; i < 7; i++){
            week_lbl[i] = new Label(weekdays[i]);
            grid.add(week_lbl[i], i+1, 0, 2, 2);
        }

        for (int i = 1; i < calendar.get(Calendar.DAY_OF_WEEK); i++)
            index_DayOfWeek = i;

        addPrevMonth();

        addCurrentMonth();

        //addNextMonth() is called inside addCurrentMonth

    }

    public void addPrevMonth(){
        int prev = prevMonthCalendar.getActualMaximum(Calendar.DATE) - index_DayOfWeek;

        prevMonthCalendar.set(year, Calendar.MONTH + getMonth() -1, prev);

        for (int i = 0; i < index_DayOfWeek; i++){
            prevMonthCalendar.add(Calendar.DATE,1);
            dates_lbl[i] = new Label (String.valueOf(prevMonthCalendar.get(Calendar.DATE)));
            dates_lbl[i].setTextFill(Color.LIGHTGRAY);
            grid.add(dates_lbl[i], i+1, 1, 2, 2);

        }

    }

    public void addCurrentMonth(){
        dates_lbl[index_DayOfWeek] = new Label(String.valueOf(calendar.get(Calendar.DATE)));

        int index = 1;
        int weekIndex = index_DayOfWeek;

        while (calendar.get(Calendar.DATE) < calendar.getActualMaximum(Calendar.DATE)){
            if (calendar.get(Calendar.DAY_OF_WEEK) == 7) {
                index_DayOfWeek += 1;
                dates_lbl[index_DayOfWeek] = new Label (String.valueOf(calendar.get(Calendar.DATE)) );
                weekIndex = weekIndex + 1;
                grid.add(dates_lbl[index_DayOfWeek], weekIndex, index, 2, 2);
                index = index +1;
                weekIndex = 0;
            }

            else{
                index_DayOfWeek += 1;
                dates_lbl[index_DayOfWeek] = new Label (String.valueOf(calendar.get(Calendar.DATE)) );
                weekIndex = weekIndex + 1;
                grid.add(dates_lbl[index_DayOfWeek], weekIndex, index, 2, 2);
            }

            calendar.add(Calendar.DATE,1);

        }

        index_DayOfWeek += 1;
        dates_lbl[index_DayOfWeek] = new Label (String.valueOf(calendar.get(Calendar.DATE)) );
        weekIndex = weekIndex + 1;
        grid.add(dates_lbl[index_DayOfWeek], weekIndex, index, 2, 2);

        //Calling addNextMonth to add dates from next month
        addNextMonth(weekIndex, index);

    }
    public void addNextMonth(int weekIndex, int index) {
        int newIndex = index_DayOfWeek;

        for (int i =newIndex; i< dates_lbl.length-1 ; i++){

            if(nextMonthCalendar.get(Calendar.DAY_OF_WEEK) == 7 && weekIndex >= 7){
                index_DayOfWeek += 1;
                dates_lbl[index_DayOfWeek] = new Label (String.valueOf(nextMonthCalendar.get(Calendar.DATE)));
                dates_lbl[index_DayOfWeek].setTextFill(Color.LIGHTGRAY);
                weekIndex = weekIndex + 1;
                grid.add(dates_lbl[index_DayOfWeek], weekIndex, index, 2, 2);
                index = index +1;
                weekIndex = 0;
            }
            else if (weekIndex == 7){
                weekIndex = 1;
                index = index+1;
                dates_lbl[index_DayOfWeek] = new Label (String.valueOf(nextMonthCalendar.get(Calendar.DATE)));
                dates_lbl[index_DayOfWeek].setTextFill(Color.LIGHTGRAY);
                grid.add(dates_lbl[index_DayOfWeek], weekIndex, index, 2, 2);

            }
            else{
                index_DayOfWeek += 1;
                dates_lbl[index_DayOfWeek] = new Label (String.valueOf(nextMonthCalendar.get(Calendar.DATE)) );
                dates_lbl[index_DayOfWeek].setTextFill(Color.LIGHTGRAY);
                weekIndex = weekIndex + 1;
                grid.add(dates_lbl[index_DayOfWeek], weekIndex, index, 2, 2);
            }

            nextMonthCalendar.add(Calendar.DATE,1);
        }


    }


}
