package com.training.server.work.DB.daoImplemnters;

import com.training.server.work.SetUpDB;
import com.training.server.work.Status;
import com.training.server.work.dao.*;
import com.training.server.work.entity.*;
import com.training.server.work.DB.DataDealer;
import com.training.server.work.DB.Table;

import org.joda.time.LocalDate;

/**
 * concrete class of LicenseDAO
 * which has the implementations of CRUD operations
 * that manipulates the License Data.
 */

public class LicenseDAOImp implements LicenseDAO {

   private final DataDealer dataDealer;

   /**
    * constructs a new instance
    * and gets the instance of the data dealer
    * which provides data to manipulate
    * using this class methods
    */

   public LicenseDAOImp () {
      this.dataDealer = SetUpDB.getInstance();
   }

   /**
    * finds the value license to which the specified key userName
    * is mapped,
    * @param userName userName for the license
    * @return {@code license} if it exist, {@code null} otherwise
    */

   @Override
   public License findByUserName(String userName) {

      if (userName == null)
         return null;

      // this could contain a License or a Status object

      Object [] containLicense = {""};
      containLicense[0] = dataDealer.retrieveData(Table.LICENSE.getTableName(), userName);

      if (containLicense[0] != Status.NOT_EXIST)
         return (License) containLicense[0];

      return null;
   }

   /**
    * updates a license time license,
    * according to the {@param newTimeLicense} id
    * and sets the new start date and end date of the license
    * @param userName userName for the license
    * @param newTimeLicense new time license
    * @return the status of the operation
    */

   @Override
   public Status updateTimeLicense(String userName, TimeLicense newTimeLicense) {

      if (newTimeLicense == null || userName == null)
         return Status.ERROR;

      Object [] containTimeLicense = {""};
      containTimeLicense[0] = dataDealer.retrieveData(Table.LICENSE.getTableName(), userName);

      if (containTimeLicense[0] != Status.NOT_EXIST) {

         switch (newTimeLicense.getLicense_id()) {
            case 3: {
               ((License) containTimeLicense[0]).setStart_date(LocalDate.now());
               ((License) containTimeLicense[0]).setEnd_date(LocalDate.now().plusMonths(3));
               ((License) containTimeLicense[0]).setTimeLicense(TimeLicense.QUARTER_YEAR);
            }
            break;
            case 6: {
               ((License) containTimeLicense[0]).setStart_date(LocalDate.now());
               ((License) containTimeLicense[0]).setEnd_date(LocalDate.now().plusMonths(6));
               ((License) containTimeLicense[0]).setTimeLicense(TimeLicense.HALF_YEAR);
            }
            break;
            case 2: {
               ((License) containTimeLicense[0]).setStart_date(LocalDate.now());
               ((License) containTimeLicense[0]).setEnd_date(LocalDate.now().plusYears(2));
               ((License) containTimeLicense[0]).setTimeLicense(TimeLicense.TWO_YEARS);
            }
            break;
            case 7: {
               ((License) containTimeLicense[0]).setStart_date(LocalDate.now());
               ((License) containTimeLicense[0]).setEnd_date(LocalDate.now().plusYears(Integer.MAX_VALUE));
               ((License) containTimeLicense[0]).setTimeLicense(TimeLicense.PERMANENT);
            }
            break;
            case 0: {
               ((License) containTimeLicense[0]).setEnd_date(LocalDate.now());
               ((License) containTimeLicense[0]).setTimeLicense(TimeLicense.EXPIRED);
            }
         }

         dataDealer.saveData(Table.LICENSE.getTableName(), userName, containTimeLicense[0]);
         return Status.UPDATED;
      }
      return Status.FAILED;
   }

   /**
    * updates license privileges
    *
    * @param userName userName for the license
    * @param newPrivilegesLicense new privileges license
    * @return the status of the operation
    */

   @Override
   public Status updatePrivilegesLicense(String userName, PrivilegesLicense newPrivilegesLicense) {

      if (newPrivilegesLicense == null || userName == null)
         return Status.ERROR;

      Object [] containPrivilegesLicense = {""};
      containPrivilegesLicense[0] = dataDealer.retrieveData(Table.LICENSE.getTableName(), userName);

      if (containPrivilegesLicense[0] != Status.NOT_EXIST) {

         switch (newPrivilegesLicense.getLicense_id()) {
            case 1: {
               ((License) containPrivilegesLicense[0]).setPrivilegesLicense(PrivilegesLicense.READ);
            }
            break;
            case 2: {
               ((License) containPrivilegesLicense[0]).setPrivilegesLicense(PrivilegesLicense.READ_WRITE);
            }
         }

         dataDealer.saveData(Table.LICENSE.getTableName(), userName, containPrivilegesLicense[0]);
         return Status.UPDATED;
      }
      return Status.FAILED;
   }

   /**
    * updates license slice
    * @param userName userName for the license
    * @param newSlice new slice
    * @return the status of the operation
    */

   @Override
   public Status updateSlice(String userName, String newSlice) {

      if (newSlice == null || userName == null)
         return Status.ERROR;

      Object [] containLicense = {""};
      containLicense[0] = dataDealer.retrieveData(Table.LICENSE.getTableName(), userName);

      if (containLicense[0] != Status.NOT_EXIST) {
         ((License) containLicense[0]).setSlice(newSlice);
         dataDealer.saveData(Table.LICENSE.getTableName(), userName, containLicense[0]);
         return Status.UPDATED;
      }
      return Status.FAILED;
   }

}
