/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaslabb1;

import java.util.ArrayList;

/**
 *
 * @author swehu
 */
public class Content {
    int contentID, timestamp;
    ContentType type;
    String title, addedBy;
    ArrayList<String> genre, creator;

    public Content(int contentID, ContentType type, String title, int releaseDate, String addedBy) {
        this.contentID = contentID;
        this.timestamp = releaseDate;
        this.type = type;
        this.title = title;
        this.addedBy = addedBy;
    }
    
}
