/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import javafx.scene.paint.Color;

/**
 *
 * @author jack1
 */
public class Color_Utils {
     private static final Color zomp = Color.rgb(67, 154,134);
    private static final Color persian_red = Color.rgb(187, 68, 48);
    
    public static Color getGreen(){
        return zomp;
    }
    
    public static Color getRed(){
        return persian_red;
    }
    
    public static Color borderColor(Color color){
         // Compute luminance
        double luminance = 0.299 * color.getRed()
                         + 0.587 * color.getGreen()
                         + 0.114 * color.getBlue();

        // Choose lighter or darker shade for border
        return (luminance < 0.5) ? color.brighter() : color.darker();
    }
    
    public static Color textColor(Color color){
        double luminance = 0.299 * color.getRed()
                         + 0.587 * color.getGreen()
                         + 0.114 * color.getBlue();

        return (luminance > 0.5) ? Color.BLACK : Color.WHITE;
    }
    
    public static Color getColor(double percentA, double percentB){
        
        
        if(percentA == 0 && percentB == 0){
            return Color.web("#E6E6E6");
        }
        
        double red = (zomp.getRed() * percentA) + (persian_red.getRed() * percentB);
        
        double green = zomp.getGreen() * percentA + persian_red.getGreen() * percentB;
        double blue = zomp.getBlue() * percentA + persian_red.getBlue() * percentB;
        return Color.color(red, green, blue);
    }
}
