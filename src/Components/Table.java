/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import Data_Models.Transaction_Model;
import File_Management.File_System;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author jack1
 */
public class Table {
    private final TableView<Transaction_Model> table = new TableView();
    private final ObservableList<Transaction_Model> item_list;
    private final TableColumn<Transaction_Model, String> titleColoumn = new TableColumn<>("Title");
    private final TableColumn<Transaction_Model,Double> amountColoumn = new TableColumn<>("Amount");
    private final TableColumn<Transaction_Model,String> typeColoumn = new TableColumn<>("Type");
    private final TableColumn<Transaction_Model,LocalDate> dateColoumn = new TableColumn<>("Date");
    
    private void setRowFactories(){
        titleColoumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColoumn.setPrefWidth(170);
        amountColoumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountColoumn.setPrefWidth(170);
        typeColoumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColoumn.setPrefWidth(170);
        dateColoumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColoumn.setPrefWidth(170);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    private void columnsInsert(){
        table.getColumns().addAll(titleColoumn,typeColoumn,amountColoumn,dateColoumn);
        table.setItems(item_list);
    } 
    
    public ObservableList<Transaction_Model> getList(){
        return item_list;
    }
    
    private void atStart(){
        
    }
    
    public TableView<Transaction_Model> getT(){
        return table;
    }
    
    public Table(){
        item_list = File_System.getMasterList();
        setRowFactories();
        columnsInsert();
    }
}
