/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author jack1
 */
public class Navigation_Box extends HBox{
    private final Button OVERVIEW = new Button("Overview");
    private final Button TRANSACTIONS = new Button("Transactions");
    private final Button GOALS = new Button("Goals");
    private final Button SETTINGS = new Button("Settings");
    
    private void atStart(){
        
        getChildren().addAll(OVERVIEW,TRANSACTIONS,GOALS,SETTINGS);
    }
    
    private void CSS(){
        AnchorPane.setLeftAnchor(this, 10.0);
        AnchorPane.setTopAnchor(this, 10.0);
    }
    
    public void setAnchors(double top, double right, double bottom, double left){
        AnchorPane.setTopAnchor(this, top >=0 ? top : null);
        AnchorPane.setRightAnchor(this, right >=0 ? right : null);
        AnchorPane.setBottomAnchor(this, bottom >=0 ? bottom : null);
        AnchorPane.setLeftAnchor(this, left >=0 ? left : null);
    }
    
    public Navigation_Box(){
        atStart();
        CSS();
    }
    
    public Button getOverviewButton(){
        return OVERVIEW;
    }
    
    public Button getTransactionsButton(){
        return TRANSACTIONS;
    }
    
    public Button getGoalsButton(){
        return GOALS;
    }
    
    public Button getSettingsButton(){
        return SETTINGS;
    }
}
