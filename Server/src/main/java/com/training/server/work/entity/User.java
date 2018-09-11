package com.training.server.work.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.InputMismatchException;


/**
 * User represents real-world entity
 * This model class is to contain the User DATA
 * and standard get and set methods.
 */

@XmlRootElement
public class User {

   // depend on userName as a primary key
   private String userName;
   private String password;
   private UserType userType;

   private User() {
   }

   // atomic
   public User(String userName, String password, UserType userType) {

      this.userName = userName;
      this.password = password;
      this.userType = userType;
   }

   /**
    * standard get/set methods.
    */

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {

      if(userName == null)
         throw new InputMismatchException("userName cannot be null") ;

      this.userName = userName;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {

      if(password == null)
         throw new InputMismatchException("password cannot be null") ;

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
         "userName='" + userName + '\'' +
         ", userType=" + userType +
         '}';
   }

   // No Equals and hashcode cause each instance of this class is equal to itself only
   // using the userName field which is a unique value for each instance

}
