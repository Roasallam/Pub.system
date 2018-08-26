package com.training.server.work.memoryDB.EntitiesImp;

import com.training.server.work.dao.UserDAO;
import com.training.server.work.entity.User;

import java.util.concurrent.ConcurrentHashMap;
import java.util.*;

public class UserDAOImp implements UserDAO {

   // you should fine a way to search in data stored in the File System //
   // and the data stored in the cache


   // for cuurent time only ,, this should be in the InMemory class
   ConcurrentHashMap<String,User> users;


   public UserDAOImp() {

   }

   @Override
   public User RegisterUser(User user) {
      // don't forget to create their license ..
      return users.put(user.getUserName(),user);
   }

   @Override
   public User findByName(String userName) {
      if (users.containsKey(userName))
         return users.get(userName);
      else
         throw new NullPointerException("user dose not exist!");
   }

   @Override
   public User updateUserPassword(User user) {
      if (users.containsKey(user.getUserName()))
         return users.replace(user.getUserName(),user);
      else
         throw new NullPointerException("user dose not exist!");
   }

   @Override
   public User deleteUser(User user) {
      // don't forget to remove their license from license table
      if (users.containsKey(user.getUserName()))
         return users.remove(user.getUserName());
      else
         throw new NullPointerException("user dose not exist!");
   }
}
