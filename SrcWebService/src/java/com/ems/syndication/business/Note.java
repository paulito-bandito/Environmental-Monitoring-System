/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.business;

/**
 *
 * @author Paul W Walter
 */
public class Note extends AData {
    public String   message;
    public User     author;
    
    public Note()
    {
        super();
    }
    
    public Note (long a_id, long a_noteTimestamp, String a_message, User a_author)
    {
        super(a_id, a_noteTimestamp);
        this.message = a_message;
        this.author = a_author;
    }
}
