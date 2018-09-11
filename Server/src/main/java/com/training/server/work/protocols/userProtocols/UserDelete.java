package com.training.server.work.protocols.userProtocols;

import com.training.server.work.DB.daoImplemnters.UserDAOImp;
import com.training.server.work.authentication.Verification;
import com.training.server.work.protocols.Protocol;
import com.training.server.work.Status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * delete user protocol
 */

public class UserDelete implements Protocol {

   private UserDAOImp userDAOImp ;
   private String statement;
   private String userName;
   private String password;

   /**
    * constructs a new instance of this protocol
    * and initiate it with the statement
    * @param statement statement specified from user
    */

   public UserDelete(String statement) {
      this.statement = statement;
      userDAOImp = new UserDAOImp();
   }

   /**
    * deletes user
    * 1st checks syntax of the statement sent by the user
    * if it's incorrect syntax then return
    * 2nd verifies the user
    * @return the status of the operation
    */

   private Status deleteUser () {

      if (!checkSyntax())
         return Status.SYNTAX_ERROR;

      if (!isVerified())
         return Status.INCORRECT_PASSWORD;

      return userDAOImp.deleteUser(userName);
   }

   /**
    * returns the result of deleteUser method
    * @return returns the result of deleteUser method
    */

   @Override
   public String getResult() {

      Status status = deleteUser();

      return status.getMsg();
   }

   /**
    * checks for the statement syntax
    * @return {@code true} if and only if the syntax was correct
    * {@code false} otherwise
    */

   @Override
   public boolean checkSyntax() {

      String deleteUserRegex = "^DELETE\\s[a-zA-Z_0-9]+\\sPASSWORD\\s[a-zA-Z_0-9]+";

      Pattern deleteUserPattern = Pattern.compile(deleteUserRegex, Pattern.CASE_INSENSITIVE);

      Matcher deleteUserMatcher = deleteUserPattern.matcher(statement);


      if (deleteUserMatcher.find()) {

         String [] data = deleteUserMatcher.group().split(" ");
         userName = data[1];
         password = data[3];
         return true;
      }
      return false;
   }

   /**
    * checks if the user is verified or not
    * @return @code true} if and only if the user is verified,
    * {@code false} otherwise
    */

   private boolean isVerified () {

      return Verification.isValidPassword(userName, password);
   }
}
