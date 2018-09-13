package com.training.server.work.authentication;

import com.training.server.work.DB.daoImplemnters.UserDAOImp;
import com.training.server.work.Status;
import com.training.server.work.entity.User;

/**
 * verifies if a user is a user of the system
 * and checks for users passwords if they are correct
 * or not
 */

public class Verification {

   private static UserDAOImp userDAOImp = new UserDAOImp();
   private static User user;

   /**
    * checks for the password status,
    * if a user is not exist then return {@code NOT_EXIST},
    * if the password which entered by the user is not equal to
    * the password which saved in the database then
    * return {@code INCORRECT_PASSWORD} and the user is not able to sign in
    * if not then he is able to sign in
    * @param userName userName specified for the user
    * @param password the password that the user entered
    * @return {@code NOT_EXIST} if the user is not a user of the system,
    * {@code WELCOME} if and only if
    * the password matched the saved one
    */

    private static Status passwordStatus (String userName, String password) {

      if (!isExist(userName))
         return Status.NOT_EXIST;

      String savedPassword = user.getPassword();

      if (savedPassword.equals(password))
         return Status.WELCOME;

      return Status.INCORRECT_PASSWORD;
   }

   /**
    * checks if the password entered by the user is valid or not
    * @param userName userName specified for the user
    * @param password the password that the user entered
    * @return {@code true} if and only if the password is valid,
    * {@code false} otherwise
    */

   public static boolean isValidPassword (String userName, String password) {

       Status status = passwordStatus(userName, password);

       return status == Status.WELCOME;
   }

   /**
    * checks if a user exist or not
    * @param userName userName specified for the user
    * @return {@code true} if the user is a user of the system
    * {@code false} if the user is not exist
    */

   public static boolean isExist (String userName) {

      user = userDAOImp.findByName(userName);

      return user != null;
   }
}
