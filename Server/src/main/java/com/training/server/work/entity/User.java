package com.training.server.work.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.InputMismatchException;


/**
 * User Represents entity
 * this class is to contain the User DATA
 */

@XmlRootElement
public class User {



   // depend on userName as a primary key
   private String userName;
   private String password;
   private UserType userType;


   private User () {

   }


   // solve this ,, it is not atomic
   public User(String userName, String password, UserType userType) {

      if (userName == null) throw new InputMismatchException("USERNAME cannot be null");
      if (password == null) throw new InputMismatchException("PASSWORD cannot be null");
      if (userType == null) throw new InputMismatchException("TYPE cannot be null");


      this.userName = userName;
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
         ", userName='" + userName + '\'' +
         '}';
   }

   // No Equals and hashcode cause each instance of this class is equal to itself only
   // using the userName field which is a unique value for each instance

}
