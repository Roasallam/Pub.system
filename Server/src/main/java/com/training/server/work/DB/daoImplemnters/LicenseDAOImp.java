package com.training.server.work.DB.daoImplemnters;

import com.training.server.work.SetUpDB;
import com.training.server.work.Status;
import com.training.server.work.dao.*;
import com.training.server.work.entity.*;
import com.training.server.work.DB.DataDealer;
import com.training.server.work.DB.Table;

import java.time.LocalDate;



public class LicenseDAOImp implements LicenseDAO {

   private final DataDealer dataDealer;

   public LicenseDAOImp () {
      this.dataDealer = SetUpDB.getInstance();
   }


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

   @Override
   public Status updateTimeLicense(String userName, TimeLicense newTimeLicense) {

      if (newTimeLicense == null || userName == null)
         return Status.ERROR;

      // this could contain a License or a Status object

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

   @Override
   public Status updatePrivilegesLicense(String userName, PrivilegesLicense newPrivilegesLicense) {

      if (newPrivilegesLicense == null || userName == null)
         return Status.ERROR;

      // this could contain a License or a Status object

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

   @Override
   public Status updateSlice(String userName, String newSlice) {

      if (newSlice == null || userName == null)
         return Status.ERROR;

      // this could contain a License or a Status object

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
