package com.training.server.work.protocols.userProtocols;

import com.training.server.work.DB.daoImplemnters.UserDAOImp;
import com.training.server.work.Status;
import com.training.server.work.authentication.Authenticator;
import com.training.server.work.entity.UserType;
import com.training.server.work.protocols.Protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sign up user protocol
 */

public class UserSignUp implements Protocol {

   private UserDAOImp userDAOImp ;
   private String statement;
   private UserType userType;
   private String userName;
   private String password;
   private String code;

   private String journalRegex = "^NEW\\sJOURNAL\\s[a-zA-Z_0-9]+\\sPASSWORD\\s[a-zA-Z_0-9]+";
   private String adminRegex = "^NEW\\sADMIN\\s[a-zA-Z_0-9]+\\sPASSWORD\\s[a-zA-Z_0-9]+\\sCODE\\s[a-zA-Z_0-9]+";
   private String userRegex = "^NEW\\sUSER\\s[a-zA-Z_0-9]+\\sPASSWORD\\s[a-zA-Z_0-9]+";

   private final Pattern journalPattern = Pattern.compile(journalRegex, Pattern.CASE_INSENSITIVE);
   private final Pattern adminPattern = Pattern.compile(adminRegex, Pattern.CASE_INSENSITIVE);
   private final Pattern userPattern = Pattern.compile(userRegex, Pattern.CASE_INSENSITIVE);

   private Matcher journalMatcher ;
   private Matcher adminMatcher ;
   private Matcher userMatcher;


   /**
    * constructs a new instance of this protocol
    * and initiate it with the statement
    * @param statement statement specified from user
    */

   public UserSignUp(String statement) {

      this.statement = statement;
      userDAOImp = new UserDAOImp();
   }

   /**
    * signs up a new user/admin/journal
    * 1st checks syntax of the statement sent by the user
    * if it's incorrect syntax then return
    * 2nd if its admin checks his code
    * @return the status of the operation
    */

   private Status signUp () {

      if (!checkSyntax())
         return Status.SYNTAX_ERROR;

      // if Admin > check the authentication code
      if (userType == UserType.ADMIN) {
         if (!isVerified())
            return Status.INVALID_CODE;
      }
      return userDAOImp.SignUp(userName, password, userType);
   }

   /**
    * returns the result of signUp method
    * @return returns the result of signUp method
    */

   @Override
   public String getResult() {

      Status status = signUp();

      return status.getMsg();
   }

   /**
    * checks for the statement syntax
    * @return {@code true} if and only if the syntax was correct
    * {@code false} otherwise
    */

   public boolean checkSyntax () {

      journalMatcher = journalPattern.matcher(statement);
      adminMatcher = adminPattern.matcher(statement);
      userMatcher = userPattern.matcher(statement);

      if (journalMatcher.find()) {

         String [] data = journalMatcher.group().split(" ");
         userType = UserType.JOURNAL;
         userName = data[2];
         password = data[4];
         return true;

      } else if (adminMatcher.find()) {

         String [] data = adminMatcher.group().split(" ");
         userType = UserType.ADMIN;
         userName = data[2];
         password = data[4];
         code = data[6];
         return true;

      } else if (userMatcher.find()) {

         String [] data = userMatcher.group().split(" ");
         userType = UserType.READER;
         userName = data[2];
         password = data[4];
         return true;
      }

      return false;
   }

   /**
    * checks if the admin is verified or not
    * @return @code true} if and only if the admin is verified,
    * {@code false} otherwise
    */

   private boolean isVerified () {

      return Authenticator.checkAuthenticationCode(code) == Status.VALID_CODE;
   }
}
