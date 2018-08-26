package com.training.server.work.entity;

import java.util.InputMismatchException;

/**
 * User Represents entity
 * this class is to contain the User DATA
 */
public class User {


   // there is a possibility to delete userId and depend on userName as a primary key
   private long userId;
   private String userName;
   private String email;
   private String password;
   private UserType userType;

   // private static #noOfUsers
   // maybe userId = UserType.getId + 50 + #noOfUsers

   protected User () {

   }

   private User(String userName, String email, String password, UserType userType) {

      // handle all of these if == null

      this.userName = userName;
      this.email = email;
      this.password = password;
      // if ( userType == Journal)
      // then PrivilegesLicense is READ_WRITE
      // if ( userType == USER )
      // then PrivilegesLicense is READ
      this.userType = userType;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      if(userName == null) throw new InputMismatchException("userName cannot be null") ;
      this.userName = userName;
   }

   public long getUserId() {
      return userId;
   }

   public void setUserId(long userId) {
      this.userId = userId;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      if(password == null) throw new InputMismatchException("password cannot be null") ;
      this.password = password;
   }

   public UserType getUserType() {
      return userType;
   }

   public void setUserType(UserType userType) {

      if (userType == null)
         throw new InputMismatchException("USER TYPE cannot be null");
      this.userType = userType;
   }

   @Override
   public String toString() {
      return "User{" +
         "userId=" + userId +
         ", userName='" + userName + '\'' +
         '}';
   }
}
