package com.training.server.work.memoryDB.EntitiesImp;

import com.training.server.work.dao.Status;
import com.training.server.work.dao.UserDAO;
import com.training.server.work.entity.User;
import com.training.server.work.entity.UserType;

import java.util.concurrent.ConcurrentHashMap;

public class UserDAOImp implements UserDAO {



   // for current time only ,, this should be in the InMemory class
   ConcurrentHashMap<String,User> users;


   public UserDAOImp() {

   }

   @Override
   public User findByName(String userName) {

     /* if (users.containsKey(userName))
         return users.get(userName);
          return Status.ERROR*/
      return null;
   }

   @Override
   public Status RegisterUser(String userName, String password, UserType userType) {

      /* don't forget to create their license ..
      return users.putIfAbsent(user.getUserName(),user); */
      return null;
   }

   @Override
   public Status updateUserPassword(String userName, String newPassword) {

     /* if (users.containsKey(user.getUserName()))
         return users.replace(user.getUserName(),user); */

      return null;
   }

   @Override
   public Status deleteUser(String userName) {

      // don't forget to remove their license from license table
     /* if (users.containsKey(user.getUserName()))
         return users.remove(user.getUserName()); */
      return null;
   }
}
