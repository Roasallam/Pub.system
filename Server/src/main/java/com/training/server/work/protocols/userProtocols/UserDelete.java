package com.training.server.work.protocols.userProtocols;

import com.training.server.work.DB.daoImplemnters.UserDAOImp;
import com.training.server.work.protocols.Protocol;
import com.training.server.work.Status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDelete implements Protocol {

   private UserDAOImp userDAOImp ;
   private String statement;
   private String userName;

   public UserDelete(String statement) {
      this.statement = statement;
      userDAOImp = new UserDAOImp();
   }

   private Status deleteUser () {

      if (!checkSyntax())
         return Status.SYNTAX_ERROR;

      return userDAOImp.deleteUser(userName);
   }

   @Override
   public String getResult() {

      Status status = deleteUser();

      return status.getMsg();
   }

   @Override
   public boolean checkSyntax() {

      String deleteUserRegex = "^DELETE\\s[a-zA-Z_0-9]+";

      Pattern deleteUserPattern = Pattern.compile(deleteUserRegex, Pattern.CASE_INSENSITIVE);

      Matcher deleteUserMatcher = deleteUserPattern.matcher(statement);


      if (deleteUserMatcher.find()) {

         String [] data = deleteUserMatcher.group().split(" ");
         userName = data[1];
         return true;
      }
      return false;
   }
}
