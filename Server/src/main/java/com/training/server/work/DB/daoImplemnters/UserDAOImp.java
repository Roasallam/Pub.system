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

import org.joda.time.LocalDate;

/**
 * concrete class of UserDAO
 * which has the implementations of CRUD operations
 * that manipulates the User Data.
 */

public class UserDAOImp implements UserDAO {

   private final DataDealer dataDealer;

   /**
    * constructs a new instance
    * and gets the instance of the data dealer
    * which provides data to manipulate
    * using this class methods
    */

   public UserDAOImp() {
      this.dataDealer = SetUpDB.getInstance();
   }

   /**
    * finds the value user to which the specified key userName
    * is mapped,
    * @param userName userName for the user
    * @return {@code user} if it exist, {@code null} otherwise
    */

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

   /**
    * creates a new user/journal/admin
    * and build their license according to
    * user type,
    * before that it checks if the userName reserved or not
    * if so then return ALREADY EXISTS
    * if not then start a new user
    * @param userName specified userName from the new user
    * @param password specified password from the user
    * @param userType user type
    * @return the status of the operation
    */

   @Override
   public synchronized Status SignUp(String userName, String password, UserType userType) {

      if (userName == null || password == null || userType == null)
         return Status.ERROR;

      if (dataDealer.retrieveData(Table.USER.getTableName(), userName) != Status.NOT_EXIST) {
         return Status.ALREADY_EXISTS;
      }

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
            .setSlice("Unlimited")
            .build();
      } else {
         license = new License.Builder(userName).build();
      }

         User user = new User(userName, password, userType);

         dataDealer.saveData(Table.USER.getTableName(), userName, user);
         dataDealer.saveData(Table.LICENSE.getTableName(), userName, license);

      return Status.WELCOME;
   }

   /**
    * updates the password for the user
    * with the specified new password
    * @param userName user name
    * @param newPassword new password to update to
    * @return the status of the operation
    */

   @Override
   public Status updatePassword(String userName, String newPassword) {

      if (userName == null || newPassword == null)
         return Status.ERROR;

     Object [] containUser = {""};
     containUser[0] = dataDealer.retrieveData(Table.USER.getTableName(), userName);

     if (containUser[0] != Status.NOT_EXIST) {

        ((User) containUser[0]).setPassword(newPassword);
        dataDealer.saveData(Table.USER.getTableName(), userName, containUser[0]);
        return Status.UPDATED;
     }
      return Status.FAILED;
   }

   /**
    * deletes a user with the specified userName
    * @param userName user name
    * @return the status of the operation
    */

   @Override
   public Status deleteUser(String userName) {

      if (userName == null)
         return Status.ERROR;

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
