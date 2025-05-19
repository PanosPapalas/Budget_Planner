/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data_Models;

import java.time.LocalDate;

/**
 *
 * @author jack1
 */
public class Transaction_Model {
    private String imageName = "";
    private String title = "title";
    private double amount = 0.0;
    private LocalDate date = LocalDate.now();
    private Transaction_Type type;
    
    public Transaction_Model(String imageName, String title, double amount, LocalDate date, Transaction_Type tType){
        this.imageName = imageName;
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.type = tType;
    }
    
    public Transaction_Model(String imageName, String title, double amount, LocalDate date, int tType){
        this.imageName = imageName;
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.type = Transaction_Type.getTypeByID(tType);
    }
    
    public String getImageName(){
        return imageName;
    }
    
    public void setImageName(String newImageName){
        imageName = newImageName;
    }
    
    public String getTitle(){
        return title;
    }
    
    public void set(String newTitle){
        title = newTitle;
    }
    
    public double getAmount(){
        return amount;
    }
    
    public void set(double newAmount){
        amount = newAmount;
    }
    
    public LocalDate getDate(){
        return date;
    }
    
    public void setDate(LocalDate newDate){
        date = newDate;
    }
    
    public void setTransactionType(Transaction_Type newType){
        type = newType;
    }
    
    public Transaction_Type getTransactionType(){
        return type;
    }
    
    @Override
    public String toString(){
        return "Image Name: " + imageName 
                + ",\nTransaction Title: " + title 
                + ",\nAmount: " + amount 
                + ",\nDate of Transaction: " + date 
                + ",\nTransaction Type: " + type +".";
    }
}
