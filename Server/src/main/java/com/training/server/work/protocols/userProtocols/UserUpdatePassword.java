package com.training.server.work.protocols.userProtocols;

import com.training.server.work.DB.daoImplemnters.UserDAOImp;
import com.training.server.work.Status;
import com.training.server.work.protocols.Protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUpdatePassword implements Protocol {

   private UserDAOImp userDAOImp ;
   private String statement;
   private String userName;
   private String newPassword;

   public UserUpdatePassword(String statement) {

      this.statement = statement;
      userDAOImp = new UserDAOImp();
   }

   private Status updatePassword () {

      if (!checkSyntax())
         return Status.SYNTAX_ERROR;

      return userDAOImp.updatePassword(userName, newPassword);
   }

   @Override
   public String getResult() {

      Status status = updatePassword();

      return status.getMsg();
   }

   public boolean checkSyntax () {

      String passwordRegex = "^UPDATE\\s[a-zA-Z_0-9]+\\sPASSWORD\\s[a-zA-Z_0-9]+";

      Pattern passwordPattern = Pattern.compile(passwordRegex, Pattern.CASE_INSENSITIVE);

      Matcher passwordMatcher = passwordPattern.matcher(statement);


      if (passwordMatcher.find()) {

         String [] data = passwordMatcher.group().split(" ");
         userName = data[1];
         newPassword = data[3];
         return true;
      }

      return false;
   }
}
