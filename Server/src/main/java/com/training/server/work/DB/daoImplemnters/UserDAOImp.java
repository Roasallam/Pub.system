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
         return null;

      // this could contain a Status or a User object

      Object [] containUser = {""};
      containUser[0] = dataDealer.retrieveData(Table.USER.getTableName(), userName);

      if (containUser[0] != Status.NOT_EXIST)
         return (User) containUser[0];

      return null;
   }

   @Override
   public synchronized Status SignUp(String userName, String password, UserType userType) {

      if (userName == null || password == null || userType == null)
         return Status.ERROR;

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
            .setEnd_date(LocalDate.now().plusYears(100))
            .build();
      } else {
         license = new License.Builder(userName).build();
      }

         User user = new User(userName, password, userType);

         dataDealer.saveData(Table.USER.getTableName(), userName, user);
         dataDealer.saveData(Table.LICENSE.getTableName(), userName, license);

      return Status.WELCOME;
   }

   @Override
   public Status updatePassword(String userName, String newPassword) {

      if (userName == null || newPassword == null)
         return Status.ERROR;

      // this could contain a Status or a User object

     Object [] containUser = {""};
     containUser[0] = dataDealer.retrieveData(Table.USER.getTableName(), userName);

     if (containUser[0] != Status.NOT_EXIST) {

        ((User) containUser[0]).setPassword(newPassword);
        dataDealer.saveData(Table.USER.getTableName(), userName, containUser[0]);
        return Status.UPDATED;
     }
      return Status.FAILED;
   }

   @Override
   public Status deleteUser(String userName) {

      if (userName == null)
         return Status.ERROR;

      // this could contain a Status or a User object

      Object [] containLicense = {""};
      containLicense[0] = dataDealer.retrieveData(Table.LICENSE.getTableName(), userName);

      if (containLicense[0] != Status.NOT_EXIST) {
         dataDealer.deleteData(Table.LICENSE.getTableName(), userName);
         dataDealer.deleteData(Table.USER.getTableName(), userName);
         return Status.DELETED;
      }
      return Status.NOT_EXIST;
   }
}
