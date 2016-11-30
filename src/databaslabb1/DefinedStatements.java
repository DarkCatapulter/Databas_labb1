/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaslabb1;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 *
 * @author swehu
 */
public class DefinedStatements {
    DBConnection dbcon = null;

    public DefinedStatements(DBConnection connection) {
    dbcon = connection;
    }
    
    public boolean login(String email, String password) {
       
        
                           
        try {
            PreparedStatement stmt = dbcon.con.prepareStatement("SELECT COUNT(*) AS c FROM user WHERE Email = ? AND Password = Password(?);");
            stmt.setString(1,email);
            stmt.setString(2,password);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("c")== 1;
        } catch (SQLException ex) {
            Logger.getLogger(DefinedStatements.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
            
        
        
     
    }
}
