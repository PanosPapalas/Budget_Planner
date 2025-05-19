/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Calendar;

import Data_Models.Transaction_Type;
import File_Management.File_System;
import java.time.LocalDate;
import java.time.YearMonth;
import javafx.scene.layout.GridPane;

/**
 *
 * @author jack1
 */
public class Calendar_Days_Holder extends GridPane {
    private int row = 0;
    private int column = 0;
    private int dayCount = 0;
    private LocalDate date = LocalDate.now();
    
    private void CSS(){
        this.setHgap(2);
        this.setVgap(2);
    }
    
    private void atStart(int days){
        setDays(days);
        File_System.getOnChange().addListener((obs,o,n) -> {
            if(n){
                draw();
            }
        });
        draw();
    }
    
    public Calendar_Days_Holder(int days){
        atStart(days);
        CSS();
    }
    
    public void draw(){
        File_System.getTransactionsByYearMonth(YearMonth.of(date.getYear(), date.getMonth())).forEach(item ->{
            this.getChildren().forEach(day -> {
                Day d = (Day) day;
                if(d.getDate().equals(item.getDate())){
                    if(item.getTransactionType() == Transaction_Type.Income){
                        d.setIncome(item.getAmount());
                    }else if(item.getTransactionType() == Transaction_Type.Spending){
                        d.setSpending(item.getAmount());
                    }
                }
            });
        });
    }
    
    public LocalDate getDate(){
        return date;
    }
    
    public void setDays(LocalDate month){
        getChildren().clear();
        column =0;
        row =0;
        dayCount =0;
        date = month;
        while(dayCount<month.getMonth().length(month.isLeapYear())){
            if(column == 7){
                column =0;
                row +=1;
            }
            addDay(new Day(LocalDate.of(month.getYear(), month.getMonth(), dayCount+1)), column++, row);
            dayCount++;
        }
        draw();
    }
    
    public void setDays(int days){
        getChildren().clear();
        column =0;
        row =0;
        dayCount =0;
        while(dayCount<days){
            if(column == 7){
                column =0;
                row +=1;
            }
            addDay(new Day(dayCount+1), column++, row);
            dayCount++;
        }
        draw();
    }
    public void addDay(Day day, int columnIndex, int rowIndex){
        this.add(day, columnIndex, rowIndex);
    }
}

/*
private int row = 0;
    private int column = 0;
    private int dayCount = 0;
    
    private void CSS(){
        this.setHgap(2);
        this.setVgap(2);
    }
    
    private void atStart(int days){
        setDays(days);
    }
    
    public Calendar_Days_Holder(int days){
        atStart(days);
        CSS();
    }
    
    public void draw(){
        
        //File_System.getTransactionList().forEach(item -> {
          //  getChildren().forEach(day -> {
          //      Day d= (Day) day;
                
//                if(d.getDate().equals(item.getDate())){
//                    if(item.isIncome()){
//                        d.setIncome(item.getAmount());
//                        
//                    }else{
//                        d.setSpending(item.getAmount());
//                    }
//                }
           // });
       // });
    }
    
    public void setDays(LocalDate month){
        getChildren().clear();
        column =0;
        row =0;
        dayCount =0;
        while(dayCount<month.getMonth().length(month.isLeapYear())){
            if(column == 7){
                column =0;
                row +=1;
            }
            addDay(new Day(LocalDate.of(month.getYear(), month.getMonth(), dayCount+1)), column++, row);
            dayCount++;
        }
    }
    
    public void setDays(int days){
        getChildren().clear();
        column =0;
        row =0;
        dayCount =0;
        while(dayCount<days){
            if(column == 7){
                column =0;
                row +=1;
            }
            addDay(new Day(dayCount+1), column++, row);
            dayCount++;
        }
    }
    public void addDay(Day day, int columnIndex, int rowIndex){
        this.add(day, columnIndex, rowIndex);
    }
*/
