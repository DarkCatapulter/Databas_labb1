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
    
    public ArrayList<Content> searchContent(String title, String genre, String creator, int rating){
       ArrayList<Content> tmpArr = new ArrayList<>();
        try {
            PreparedStatement stmt = dbcon.con.prepareStatement("SELECT * FROM ("
                    + "SELECT content.ContentID, content.Type, content.Title, content.AddedBy, content.ReleaseDate, GROUP_CONCAT(DISTINCT creator.CreatorID) AS creatorID, GROUP_CONCAT(DISTINCT contentgenre.Genre) AS genre, GROUP_CONCAT(DISTINCT creator.Name) AS creatorName, AVG(review.Score) AS avgScore FROM content, contentgenre, madeby, creator, review WHERE content.contentID = contentgenre.contentID AND( madeby.contentID = content.contentID AND creator.creatorID = madeby.creatorID ) AND content.ContentID = review.ContentID GROUP BY content.ContentID) "
                    + "WHERE Title LIKE '%?%' OR genre LIKE '%?%' OR creatorName LIKE '%?%' OR avgScore LIKE '?%'");
            stmt.setString(1, title);
            stmt.setString(2, genre);
            stmt.setString(3, creator);
            stmt.setInt(4, rating);
            ResultSet rs = stmt.executeQuery();
            
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
