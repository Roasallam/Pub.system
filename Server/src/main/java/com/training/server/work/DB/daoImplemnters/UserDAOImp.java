package com.training.server.work.DB.daoImplemnters;

import com.training.server.work.SetUpDB;
import com.training.server.work.Status;
import com.training.server.work.dao.UserDAO;
import com.training.server.work.entity.PrivilegesLicense;
import com.training.server.work.entity.TimeLicense;
import com.training.server.work.entity.User;
import com.training.server.work.entity.UserType;
import com.training.server.work.DB.DataDealer;
import com.training.server.work.entity.License;
import com.training.server.work.DB.Table;

import java.time.LocalDate;


public class UserDAOImp implements UserDAO {

   private final DataDealer dataDealer;

   public UserDAOImp() {
      this.dataDealer = SetUpDB.getInstance();
   }

   @Override
   public User findByName(String userName) {

      if (userName == null)
         throw new NullPointerException();

      // to prevent from retrieving an object of type Object, than an object of type User
      // we have to put the result in a container so we save its type

      Object [] containUser = {null};
      containUser[0] = dataDealer.retrieveData(Table.USER.getTableName(), userName);

      if (containUser[0] != Status.NOT_EXIST)
         return (User) containUser[0];

      return null;
   }

   @Override
   public synchronized Status SignUp(String userName, String password, UserType userType) {

      // check if the userName is reserved or not.

      if (dataDealer.retrieveData(Table.USER.getTableName(), userName) != Status.NOT_EXIST) {
         return Status.ALREADY_EXISTS;
      }

      // start a new User.

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

         User user = new User(userName, password, userType);

         dataDealer.saveData(Table.USER.getTableName(), userName, user);
         dataDealer.saveData(Table.LICENSE.getTableName(), userName, license);

      return Status.MISSION_ACCOMPLISHED;
   }

   @Override
   public Status updatePassword(String userName, String newPassword) {

      if (userName == null || newPassword == null)
         return Status.ERROR;

     Object [] containUser = {null};
     containUser[0] = dataDealer.retrieveData(Table.USER.getTableName(), userName);

     if (containUser[0] != Status.NOT_EXIST) {

        ((User) containUser[0]).setPassword(newPassword);
        dataDealer.saveData(Table.USER.getTableName(), userName, containUser[0]);
        return Status.MISSION_ACCOMPLISHED;
     }
      return Status.FAILED;
   }

   @Override
   public Status deleteUser(String userName) {

      if (userName == null)
         return Status.ERROR;

      Object [] containLicense = {null};
      containLicense[0] = dataDealer.retrieveData(Table.LICENSE.getTableName(), userName);

      if (containLicense[0] != Status.NOT_EXIST) {
         dataDealer.deleteData(Table.LICENSE.getTableName(), userName);
         dataDealer.deleteData(Table.USER.getTableName(), userName);
         return Status.MISSION_ACCOMPLISHED;
      }

      return Status.NOT_EXIST;
   }
}
