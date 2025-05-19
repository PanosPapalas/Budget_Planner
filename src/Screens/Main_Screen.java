/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Screens;

import Components.Navigation_Box;
import Data_Models.Transaction_Model;
import File_Management.File_System;
import java.time.LocalDate;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author jack1
 */
public class Main_Screen extends VBox {
    private final Navigation_Box NAV_BOX = new Navigation_Box();
    private final Overview overview = new Overview();
    private final Transactions_Screen transactions = new Transactions_Screen();
    private final Goals_Screen goals = new Goals_Screen();
    private final Settings_Screen settings = new Settings_Screen();
    private final StackPane contentHolder = new StackPane(overview,transactions,goals,settings);
    
    
    private void setContent(int value){
        
        switch (value){
            case 0:
                contentHolder.getChildren().remove(overview);
                contentHolder.getChildren().add(overview);
                break;
            case 1:
                contentHolder.getChildren().remove(transactions);
                contentHolder.getChildren().add(transactions);
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                contentHolder.getChildren().remove(overview);
                contentHolder.getChildren().add(overview);
                break;
        }
    }
    
    private void atStart(){
        
        getChildren().add(NAV_BOX);
        
        getChildren().add(contentHolder);
        setContent(0);
    }
    
    private void CSS(){
        getStyleClass().add("root-node");
    }
    
    public Main_Screen(){
        atStart();
        CSS();
        
        NAV_BOX.getOverviewButton().setOnAction(event -> {setContent(0);});
        NAV_BOX.getTransactionsButton().setOnAction(event -> {setContent(1);});
        NAV_BOX.getGoalsButton().setOnAction(event -> {setContent(2);});
        NAV_BOX.getSettingsButton().setOnAction(event -> {setContent(3);});
    }
}
