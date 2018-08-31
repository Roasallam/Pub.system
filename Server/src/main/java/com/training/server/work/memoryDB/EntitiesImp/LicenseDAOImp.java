package com.training.server.work.memoryDB.EntitiesImp;

import com.training.server.work.dao.*;
import com.training.server.work.entity.*;
import com.training.server.work.memoryDB.DataDealer;
import com.training.server.work.memoryDB.Table;

import java.time.LocalDate;



public class LicenseDAOImp implements LicenseDAO {
   private DataDealer dataDealer;

   LicenseDAOImp () {

   }

   @Override
   public License findByUserName(String userName) {

      Object license = dataDealer.retrieveData(Table.LICENSE.getTableName(),userName);

      if (license == null)
         return null;

      return (License)license;
   }

   @Override
   public Status updateTimeLicense(String userName, TimeLicense newTimeLicense) {

      Object license = dataDealer.retrieveData(Table.LICENSE.getTableName(), userName);

      if (license == null)
         return Status.NOT_EXIST;

      switch (newTimeLicense.getLicense_id()) {
         case 3: {
            newTimeLicense.setStart_date(LocalDate.now());
            newTimeLicense.setEnd_date(LocalDate.now().plusMonths(3));
            newTimeLicense = TimeLicense.QUARTER_YEAR;
         } break;
         case 6: {
            newTimeLicense.setStart_date(LocalDate.now());
            newTimeLicense.setEnd_date(LocalDate.now().plusMonths(6));
            newTimeLicense = TimeLicense.HALF_YEAR;
         } break;
         case 2: {
            newTimeLicense.setStart_date(LocalDate.now());
            newTimeLicense.setEnd_date(LocalDate.now().plusYears(2));
            newTimeLicense = TimeLicense.TWO_YEARS;
         } break;
         case 7: {
            newTimeLicense.setStart_date(LocalDate.now());
            newTimeLicense.setEnd_date(LocalDate.now().plusYears(Integer.MAX_VALUE));
            newTimeLicense = TimeLicense.PERMANENT;
         } break;
         case 0: {
            newTimeLicense.setEnd_date(LocalDate.now());
            newTimeLicense = TimeLicense.EXPIRED;
         }
      }

      License newLicense = (License) license;
      newLicense.setTimeLicense(newTimeLicense);
      dataDealer.saveData(Table.LICENSE.getTableName(), userName, newLicense);

      return Status.MISSION_ACCOMPLISHED;
   }

   @Override
   public Status updatePrivilegesLicense(String userName, PrivilegesLicense newPrivilegesLicense) {
      return null;
   }

   @Override
   public Status updateSlice(String userName, String newSlice) {
      return null;
   }

   @Override
   public Status deleteLicense(String userName) {
      return null;
   }
}
