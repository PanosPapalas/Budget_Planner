/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Calendar;

import Utils.Color_Utils;
import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 *
 * @author jack1
 */
public class Day extends AnchorPane{
    private final Label title = new Label("a");
    private LocalDate date;
    private double percentSpending = 0;
    private double percentIncome = 0;
    
    private void CSS(){
        check();
        title.setStyle("-fx-font-size: 16px;");
//        setOnMouseEntered(event -> {setStyle("-fx-background-color: black;"); });
//        setOnMouseExited(event -> {setStyle("-fx-background-color: red;");});
        
    }
    
    private void check(){
        double max = percentIncome+percentSpending;
        if(max == 0) max = 1.0;
        Color bgColor = Color_Utils.getColor(percentIncome/max, percentSpending/max);
        Color txtColor = Color_Utils.textColor(bgColor);
        
        setBackground(new Background(new BackgroundFill(bgColor, new CornerRadii(15), Insets.EMPTY)));
        title.setTextFill(txtColor);
    }
    
    public void setSpending(double value){
        percentSpending = value;
        check();
    }
    
    public void setIncome(double value){
        percentIncome = value;
        check();
    }
    
    private void atStart(){
        
        getChildren().add(title);
        
        AnchorPane.setBottomAnchor(title, 5.0);
        AnchorPane.setRightAnchor(title, 10.0);
        AnchorPane.setLeftAnchor(title, 40.0);
        AnchorPane.setTopAnchor(title, 35.0);
    }
    
    public void setText(String ss){
        title.setText(ss);
    }
    public LocalDate getDate(){
        return date;
    }
    public Day(String day){
        CSS();
        atStart();
        
        title.setText(day);
    }
    
    public Day(int day){
        CSS();
        atStart();
        date = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), day);
        title.setText(String.valueOf(day));
    }
    
    public Day(LocalDate day){
        CSS();
        atStart();
        date = day;
        title.setText(String.valueOf(day.getDayOfMonth()));
    }
}
