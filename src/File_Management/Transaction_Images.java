/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package File_Management;

import java.io.File;
import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 *
 * @author jack1
 */
class Transaction_Images {
    private static final HashMap<String, Image> MAP = new HashMap<>(); 
    private static final File TRANSACTION_IMAGES = new File("./images/transaction_images");
    private static final Image defaultImage = new WritableImage(24,24);
    
    
    public static void atStart(){
        if(!TRANSACTION_IMAGES.exists()){
            TRANSACTION_IMAGES.mkdir();
        }else{
            for(File imageFile : TRANSACTION_IMAGES.listFiles()){
                MAP.put(imageFile.getName(), new Image(imageFile.toURI().toString()));
            }
        }
    }
    
    public static HashMap<String, Image> getMap(){
        return MAP;
    }
    
    public static Image getImage(String key){
        return MAP.getOrDefault(key, defaultImage);
    }
}
