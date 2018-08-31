package com.training.server.work.memoryDB.EntitiesImp;

import com.training.server.work.dao.Status;
import com.training.server.work.dao.UserDAO;
import com.training.server.work.entity.PrivilegesLicense;
import com.training.server.work.entity.TimeLicense;
import com.training.server.work.entity.User;
import com.training.server.work.entity.UserType;
import com.training.server.work.memoryDB.DataDealer;
import com.training.server.work.entity.License;
import com.training.server.work.memoryDB.Table;

import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;

public class UserDAOImp implements UserDAO {


   private DataDealer dataDealer;

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

      License license;

      if (userType == UserType.JOURNAL) {
         license = new License.Builder(userName)
            .setPrivilegesLicense(PrivilegesLicense.READ_WRITE)
            .setSlice(userName)
            .build();
      }
      else if (userType == UserType.ADMIN) {
         license = new License.Builder(userName)
            .setPrivilegesLicense(PrivilegesLicense.READ_WRITE)
            .setTimeLicense(TimeLicense.PERMANENT)
            .setEnd_date(LocalDate.now().plusYears(Integer.MAX_VALUE))
            .build();
      } else {
         license = new License.Builder(userName).build();
      }


      // my cache is thread safe but disk is not
      synchronized (UserDAO.class) {

         Object object = dataDealer.retrieveData(Table.USER.getTableName(), userName);

         if (object != null)
            return Status.ALREADY_EXISTS;

         User user = new User(userName, password, userType);

         dataDealer.saveData(Table.USER.getTableName(),userName,user);
         dataDealer.saveData(Table.LICENSE.getTableName(), userName, license);
      }

      return Status.MISSION_ACCOMPLISHED;
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
