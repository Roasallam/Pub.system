package com.training.server.work.protocols.userProtocols;

import com.training.server.work.DB.daoImplemnters.UserDAOImp;
import com.training.server.work.Status;
import com.training.server.work.authentication.Authenticator;
import com.training.server.work.entity.UserType;
import com.training.server.work.protocols.Protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSignUp implements Protocol {

   private UserDAOImp userDAOImp ;
   private String statement;
   private UserType userType;
   private String userName;
   private String password;
   private String code;

   public UserSignUp(String statement) {

      this.statement = statement;
      userDAOImp = new UserDAOImp();
   }

   private Status signUp () {

      if (!checkSyntax())
         return Status.SYNTAX_ERROR;

      // if Admin > check the authentication code
      if (userType == UserType.ADMIN) {
         if (Authenticator.checkAuthenticationCode(code) == Status.INVALID_CODE)
            return Status.INVALID_CODE;
      }

      return userDAOImp.SignUp(userName, password, userType);
   }

   @Override
   public Object getResult() {

      Status status = signUp();

      return status.getMsg();

   }

   public boolean checkSyntax () {

      String journalRegex = "^NEW\\sJOURNAL\\s[a-zA-Z_0-9]+\\sPASSWORD\\s[a-zA-Z_0-9]+";
      String adminRegex = "^NEW\\sADMIN\\s[a-zA-Z_0-9]+\\sPASSWORD\\s[a-zA-Z_0-9]+\\sCODE\\s[a-zA-Z_0-9]+";
      String userRegex = "^NEW\\sUSER\\s[a-zA-Z_0-9]+\\sPASSWORD\\s[a-zA-Z_0-9]+";

      Pattern journalPattern = Pattern.compile(journalRegex, Pattern.CASE_INSENSITIVE);
      Pattern adminPattern = Pattern.compile(adminRegex, Pattern.CASE_INSENSITIVE);
      Pattern userPattern = Pattern.compile(userRegex, Pattern.CASE_INSENSITIVE);

      Matcher journalMatcher = journalPattern.matcher(statement);
      Matcher adminMatcher = adminPattern.matcher(statement);
      Matcher userMatcher = userPattern.matcher(statement);


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
}
