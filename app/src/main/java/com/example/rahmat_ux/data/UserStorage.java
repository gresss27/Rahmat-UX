package com.example.rahmat_ux.data;

import com.example.rahmat_ux.model.User;
import java.util.ArrayList;
import java.util.List;
public class UserStorage {
    private static UserStorage instance;
    private List<User> users;
    private User loggedInUser;

    private UserStorage(){
        this.users = new ArrayList<User>();
    }




    public static UserStorage getInstance(){
        if(instance==null){
            instance = new UserStorage();
        }
        return instance;
    }

    public void addUser(User user){
        users.add(user);
    }

    public User findUserbyEmail(String email){
        for(User user:users){
            if(user.getEmail().equalsIgnoreCase(email)){
                return user;
            }
        }
        return null;
    }

    public boolean authenticate(String email,String password){
        User user = findUserbyEmail(email);
        if(user!=null && user.checkPassword(password)){
            loggedInUser=user;
            return true;
        }
        return false;
    }

    public User getLoggedInUser(){
        if(loggedInUser==null){
            loggedInUser=new User("test001","kuli","test001@gmail.com","08123192","password123",100000,10000,"");
            addUser(loggedInUser);
        }
        return loggedInUser;
    }
    
    public void logout(){
        loggedInUser=null;
    }

    public long addBalance(long amount){
        this.loggedInUser.setBalance(this.loggedInUser.getBalance()+amount);
        return loggedInUser.getBalance();
    }

    public long substractBalance(long amount){
        this.loggedInUser.setBalance(this.loggedInUser.getBalance()-amount);
        return loggedInUser.getBalance();
    }
}
