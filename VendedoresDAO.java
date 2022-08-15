/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intecap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VendedoresDAO {
    
    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    Conexion conectar = new Conexion();

    public int filtro(String nombre, String contra ){
    
    String sql = "select * from vendedor where nombre = "+"'"+nombre+"'"+" and password = "+"'"+contra+"'"+";";
        try {
            con = conectar.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {                
                return rs.getInt(7);               
            }
            
        } catch (Exception e) {
           
        }
    
        return -1;
    }
    
    
    
    

    
}