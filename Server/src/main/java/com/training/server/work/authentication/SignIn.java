package com.training.server.work.authentication;

import com.training.server.work.DB.daoImplemnters.UserDAOImp;
import com.training.server.work.Status;
import com.training.server.work.entity.User;

public class SignIn {

   private UserDAOImp userDAOImp = new UserDAOImp();

   private Status isValidPassword (String userName, String password) {

      Object [] containUser = new Object[1];
      containUser[0] = userDAOImp.findByName(userName);

      if (containUser[0] == null)
         return Status.NOT_EXIST;

      String savedPassword = ((User) containUser[0]).getPassword();

      if (savedPassword.equals(password))
         return Status.WELCOME;

      return Status.INCORRECT_PASSWORD;

   }
}
