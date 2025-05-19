/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Data_Models;

/**
 *
 * @author jack1
 */
public enum Transaction_Type {
    None(-1),// Type to make sure if there is an error there is a type ascribed to transaction to easier to identify
    Spending(0),
    Income(1);
    
    private final int type_ID;
    
    Transaction_Type(int id){
        type_ID = id;
    }
    
    public int getType_ID(){
        return type_ID;
    }
    
    public static Transaction_Type getTypeByID(int id){
        switch (id) {
            case 0:
                return Spending;
            case 1:
                return Income;
            default:
                return None;
        }
    }
}
