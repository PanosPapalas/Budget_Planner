/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package File_Management;

import Data_Models.Transaction_Model;
import java.io.File;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 *
 * @author jack1
 */
public class File_System {
    private static final File IMAGE_FOLDER = new File("./images");
    
    public static void initialize(){
        if(!IMAGE_FOLDER.exists()){
            IMAGE_FOLDER.mkdir();
        }
        Transaction_Images.atStart();
        Transactions_List.DataBaseAccess();
        Transactions_List.initialize();
    }
    
    public static void setT(Transaction_Model t){
        Transactions_List.addTransaction(t);
    }
    
    public HashMap<String, Image> getTransactionList(){
        return Transaction_Images.getMap();
    }
    
    public static Map<YearMonth, List<Transaction_Model>> getAllTransactions(){
        return Transactions_List.getAllTransactions();
    }
    
    public static SimpleBooleanProperty getOnChange(){
        return Transactions_List.getOnChange();
    }
    
    public static ObservableList<Transaction_Model> getMasterList(){
        return Transactions_List.getMasterList();
    }
    public static List<Transaction_Model> getTransactionsByYearMonth(YearMonth key){
        return Transactions_List.getListFrom(key);
    }
}
