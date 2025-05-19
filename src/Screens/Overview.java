/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Screens;

import Components.Calendar.Calendar;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author jack1
 */
public class Overview extends AnchorPane{
    private final Calendar calendar = new Calendar();
    
    private void CSS(){
        
    }
    
    private void atStart(){
        AnchorPane.setBottomAnchor(calendar, 10.0);
        AnchorPane.setTopAnchor(calendar, 10.0);
        AnchorPane.setLeftAnchor(calendar, 10.0);
        getChildren().add(calendar);
    }
    
    
    public Overview(){
        atStart();
        CSS();
    }
}
