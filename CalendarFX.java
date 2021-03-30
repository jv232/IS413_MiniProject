package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class CalendarFX extends Application {
    private int counter = 0;
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Calendar");

        BorderPane bp = new BorderPane();

        CalendarPane cp = new CalendarPane();
        bp.setCenter(cp.start());

        Button prev = new Button("Prev");
        Button next = new Button("Next");
        HBox hbox = new HBox(5);
        hbox.getChildren().addAll(prev, next);
        bp.setBottom(hbox);
        hbox.setAlignment(Pos.CENTER);


        prev.setOnAction(e->
        {
            bp.setCenter(null);
            CalendarPane cp1 = new CalendarPane();
            counter = counter - 1;
            cp1.setMonth(counter);
            bp.setCenter(cp1.start());
            //counter = 0;
        });

        next.setOnAction(e->
        {
            bp.setCenter(null);
            CalendarPane cp1 = new CalendarPane();
            counter = counter + 1;
            cp1.setMonth(counter);
            bp.setCenter(cp1.start());
        } );


        primaryStage.setScene(new Scene(bp, 575,250));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
