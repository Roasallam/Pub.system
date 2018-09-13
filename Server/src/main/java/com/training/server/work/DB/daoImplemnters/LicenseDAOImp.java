package com.training.server.work.DB.daoImplemnters;

import com.training.server.work.Database;
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
      this.dataDealer = Database.getInstance();
   }

   /**
    * finds the value license to which the specified key userName
    * is mapped,
    * @param userName userName for the license
    * @return {@code license} if it exist,
    * {@code null} otherwise
    */

   @Override
   public License findByUserName(String userName) {

      if (userName == null)
         return null;

      Object obj = dataDealer.retrieveData(Table.LICENSE.getTableName(), userName);

      if (obj instanceof License)
         return ((License) obj);

      return null;
   }

   /**
    * updates a license time license,
    * according to the {@param newTimeLicense} id
    * and sets the new start date and end date of the license
    * using addPeriodToLicense method
    * @param userName userName for the license
    * @param newTimeLicense new time license
    * @return the status of the operation
    */

   @Override
   public Status updateTimeLicense(String userName, TimeLicense newTimeLicense) {

      if (newTimeLicense == null || userName == null)
         return Status.ERROR;

      Object obj = dataDealer.retrieveData(Table.LICENSE.getTableName(), userName);

      if (obj instanceof License) {
         License license = (License) obj;

         switch (newTimeLicense) {
            case QUARTER_YEAR:
               addPeriodToLicense(license, LocalDate.now(), LocalDate.now().plusMonths(3), TimeLicense.QUARTER_YEAR);
               break;
            case HALF_YEAR:
               addPeriodToLicense(license, LocalDate.now(), LocalDate.now().plusMonths(6), TimeLicense.HALF_YEAR);
               break;
            case TWO_YEARS:
               addPeriodToLicense(license, LocalDate.now(), LocalDate.now().plusYears(2), TimeLicense.TWO_YEARS);
               break;
            case PERMANENT:
               addPeriodToLicense(license, LocalDate.now(), LocalDate.now().plusYears(200), TimeLicense.PERMANENT);
               break;
            case EXPIRED: {
               license.setEnd_date(LocalDate.now());
               license.setTimeLicense(TimeLicense.EXPIRED);
            }
         }
         dataDealer.saveData(Table.LICENSE.getTableName(), userName, license);
         return Status.UPDATED;
      }
      return (Status) obj;
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

      Object obj = dataDealer.retrieveData(Table.LICENSE.getTableName(), userName);

      if (obj instanceof License) {
         License license = (License) obj;

         switch (newPrivilegesLicense) {
            case READ:
               license.setPrivilegesLicense(PrivilegesLicense.READ);
               break;
            case READ_WRITE:
               license.setPrivilegesLicense(PrivilegesLicense.READ_WRITE);
         }

         dataDealer.saveData(Table.LICENSE.getTableName(), userName, license);
         return Status.UPDATED;
      }
      return (Status) obj;
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

      Object obj = dataDealer.retrieveData(Table.LICENSE.getTableName(), userName);

      if (obj instanceof License) {

         License license = (License) obj;
         license.setSlice(newSlice);

         dataDealer.saveData(Table.LICENSE.getTableName(), userName, license);
         return Status.UPDATED;
      }
      return (Status) obj;
   }

   /**
    * adds a specified period of time to
    * a license
    * @param license license to update
    * @param start start date for the license
    * @param end end date for the license
    * @param time type of the time license
    */

   private void addPeriodToLicense(License license, LocalDate start, LocalDate end, TimeLicense time) {

      license.setStart_date(start);
      license.setEnd_date(end);
      license.setTimeLicense(time);
   }
}
