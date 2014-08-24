/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ems.syndication.business;

/**
 *
 * @author Paul W Walter
 */
public class User extends AIdentifer {
    
    public String username;
    public String currentToken;
    public String userType;
    
    public User()
    {
        super();
    }
    public User(long a_id, String a_username, String a_userType, String a_currentToken)
    {
        this.id = a_id;
        this.currentToken = a_currentToken;
        this.username = a_username;
        this.userType = a_userType;
    }
}
