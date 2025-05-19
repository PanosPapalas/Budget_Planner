/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Calendar;

import java.time.LocalDate;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author jack1
 */
public class Calendar extends VBox {
    
    private final Label monthNameLabel = new Label(LocalDate.now().getMonth().name()+" " + LocalDate.now().getYear());
    private final Button forward = new Button(">");
    private final Button backward = new Button("<");
    private final DatePicker picker = new DatePicker(LocalDate.now());
    private final HBox buttonHolder = new HBox(10, backward,picker, forward);
    private final HBox labelBox = new HBox(monthNameLabel,buttonHolder);
    private final Calendar_Days_Holder days_Holder = new Calendar_Days_Holder(LocalDate.now().getMonth().length(LocalDate.now().isLeapYear()));
    private final HBox days_Pane = new HBox(days_Holder);
    
    private void moveForward(){
        days_Holder.setDays(days_Holder.getDate().plusMonths(1));
        monthNameLabel.setText(days_Holder.getDate().getMonth().toString() + " " + days_Holder.getDate().getYear());
    }
    
    private void moveBackward(){
        days_Holder.setDays(days_Holder.getDate().minusMonths(1));
        monthNameLabel.setText(days_Holder.getDate().getMonth().toString() + " " + days_Holder.getDate().getYear());
    }
    
    private void logic(){
        forward.setOnAction(event -> {moveForward();});
        backward.setOnAction(event -> {moveBackward();});
        picker.valueProperty().addListener((obs,o,n) -> {
            days_Holder.setDays(n);
            monthNameLabel.setText(days_Holder.getDate().getMonth().toString() + " " + days_Holder.getDate().getYear());
        });
    }
    
    private void atStart(){
        getChildren().add(labelBox);
        getChildren().add(days_Pane);
    }
    
    private void CSS(){
        labelBox.getStyleClass().add("calendar-label-box");
        days_Pane.getStyleClass().add("days_Pane");
        
        
        HBox.setHgrow(buttonHolder, Priority.ALWAYS);
        buttonHolder.setAlignment(Pos.CENTER_RIGHT);
    }
    
    public Calendar(){
        atStart();
        CSS();
        logic();
    }
}

/*
public class MyPickerSkin extends DatePickerSkin {

    StackPane arrowButtonAlias;
    public MyPickerSkin(DatePicker control) {
        super(control);
        arrowButtonAlias = (StackPane) control.lookup(".arrow-button");
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);
        // seems the removal must happen after super's layout (see OPs comment)
        getChildren().remove(getEditor());
        arrowButtonAlias.resizeRelocate(x, y, w, h);
    }

}
*/