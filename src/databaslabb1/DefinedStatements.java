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
import java.util.ArrayList;
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
    public boolean register(String email, String password) {                           
        try {
            PreparedStatement stmt = dbcon.con.prepareStatement("INSERT INTO user(Email, Password) VALUES(?,Password(?));");
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
    
    public ArrayList<Content> getAllContent(){
       ArrayList<Content> tmpArr = new ArrayList<>();
        try {
            Statement stmt = dbcon.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT content.ContentID, content.Type, content.Title, content.AddedBy,  content.ReleaseDate, GROUP_CONCAT(DISTINCT creator.CreatorID) AS creatorID, GROUP_CONCAT(DISTINCT contentgenre.Genre) AS genre "
                    + "FROM content, contentgenre, madeby, creator "
                    + "WHERE content.contentID = contentgenre.contentID AND (madeby.contentID = content.contentID AND creator.creatorID = madeby.creatorID) "
                    + "GROUP BY content.ContentID;");
            
            while(rs.next()){
                Content tmp = new Content(rs.getShort("ContentID"), null, rs.getString("Title"), rs.getInt("ReleaseDate"), rs.getString("AddedBy"));
                tmpArr.add(tmp);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DefinedStatements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmpArr;
    }
    
}
