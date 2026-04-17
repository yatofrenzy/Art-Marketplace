
package com.ingcollegeapt.week7twebapp.dao.interfaces;

import com.ingcollegeapt.week7twebapp.model.User;

public interface UserDAOInterface {
    //create new user
    int insertUser(String name,  String password, String email);
    
    //get password for the user
    User getUser(String user);
}
