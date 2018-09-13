package com.training.server.work.protocols.userProtocols;

import com.training.server.work.DB.daoImplemnters.UserDAOImp;
import com.training.server.work.Status;
import com.training.server.work.authentication.Verification;
import com.training.server.work.protocols.Protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * update user password protocol
 */

public class UserUpdatePassword implements Protocol {

   private UserDAOImp userDAOImp ;
   private String statement;
   private String userName;
   private String newPassword;
   private String oldPassword;

   private String passwordRegex =
      "^UPDATE\\s[a-zA-Z_0-9]+\\sNEWPASSWORD\\s[a-zA-Z_0-9]+\\sOLDPASSWORD\\s[a-zA-Z_0-9]+";

   private final Pattern passwordPattern = Pattern.compile(passwordRegex, Pattern.CASE_INSENSITIVE);
   private Matcher passwordMatcher;

   /**
    * constructs a new instance of this protocol
    * and initiate it with the statement
    * @param statement statement specified from user
    */

   public UserUpdatePassword(String statement) {

      this.statement = statement;
      userDAOImp = new UserDAOImp();
   }

   /**
    * updates a user password
    * 1st checks syntax of the statement sent by the user
    * if it's incorrect syntax then return
    * 2nd verifies the user if he is able to update his password
    * @return the status of the operation
    */

   private Status updatePassword () {

      if (!checkSyntax())
         return Status.SYNTAX_ERROR;

      if (!isExist())
         return Status.NOT_EXIST;

      if (!isVerified())
         return Status.INCORRECT_PASSWORD;

      return userDAOImp.updatePassword(userName, newPassword);
   }

   /**
    * returns the result of updatePassword method
    * @return returns the result of updatePassword method
    */

   @Override
   public String getResult() {

      Status status = updatePassword();

      return status.getMsg();
   }

   /**
    * checks for the statement syntax
    * @return {@code true} if and only if the syntax was correct
    * {@code false} otherwise
    */

   public boolean checkSyntax () {

      passwordMatcher = passwordPattern.matcher(statement);

      if (isCorrectSyntax()) {

         String [] data = passwordMatcher.group().split(" ");
         userName = data[1];
         newPassword = data[3];
         oldPassword = data[5];
         return true;
      }
      return false;
   }

   private boolean isCorrectSyntax () {

      return passwordMatcher.find();
   }

   /**
    * checks if the user is verified or not
    * @return @code true} if and only if the user is verified,
    * {@code false} otherwise
    */

   private boolean isVerified () {

      return Verification.isValidPassword(userName, oldPassword);
   }

   /**
    * checks if a user exist or not
    * @return {@code true} if the user is a user of the system
    * {@code false} if the user is not exist
    */

   private boolean isExist () {

      return Verification.isExist(userName);
   }
}
