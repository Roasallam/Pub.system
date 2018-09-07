package com.training.server.work.authentication;

import com.training.server.work.DB.daoImplemnters.UserDAOImp;
import com.training.server.work.Status;
import com.training.server.work.entity.User;

public class SignIn {

   private static UserDAOImp userDAOImp = new UserDAOImp();

    private static Status passwordStatus (String userName, String password) {

      User user = userDAOImp.findByName(userName);

      if (user == null)
         return Status.NOT_EXIST;

      String savedPassword = user.getPassword();

      if (savedPassword.equals(password))
         return Status.WELCOME;

      return Status.INCORRECT_PASSWORD;
   }

   public static boolean isValidPassword (String userName, String password) {

       Status status = passwordStatus(userName, password);

       if (status == Status.WELCOME)
          return true;

       return false;
   }
}
