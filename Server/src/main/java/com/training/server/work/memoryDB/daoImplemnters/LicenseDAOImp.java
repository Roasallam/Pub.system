package com.training.server.work.memoryDB.daoImplemnters;

import com.training.server.work.SetUpDB;
import com.training.server.work.Status;
import com.training.server.work.dao.*;
import com.training.server.work.entity.*;
import com.training.server.work.memoryDB.DataDealer;
import com.training.server.work.memoryDB.Table;

import java.time.LocalDate;



public class LicenseDAOImp implements LicenseDAO {

   private final DataDealer dataDealer;

   LicenseDAOImp () {
      this.dataDealer = SetUpDB.getInstance();
   }


   @Override
   public License findByUserName(String userName) {

      if (userName == null)
         throw new NullPointerException();

      Object [] containLicense = {null};
      containLicense[0] = dataDealer.retrieveData(Table.LICENSE.getTableName(), userName);

      if (containLicense[0] != Status.NOT_EXIST)
         return (License) containLicense[0];

      return null;
   }

   @Override
   public Status updateTimeLicense(String userName, TimeLicense newTimeLicense) {

      if (newTimeLicense == null || userName == null)
         return Status.ERROR;

      Object [] containTimeLicense = {null};
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
         return Status.MISSION_ACCOMPLISHED;
      }
      return Status.FAILED;
   }

   @Override
   public Status updatePrivilegesLicense(String userName, PrivilegesLicense newPrivilegesLicense) {

      if (newPrivilegesLicense == null || userName == null)
         return Status.ERROR;

      Object [] containPrivilegesLicense = {null};
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
         return Status.MISSION_ACCOMPLISHED;
      }
      return Status.FAILED;
   }

   @Override
   public Status updateSlice(String userName, String newSlice) {

      if (newSlice == null || userName == null)
         return Status.ERROR;

      Object [] containSlice = {null};
      containSlice[0] = dataDealer.retrieveData(Table.LICENSE.getTableName(), userName);

      if (containSlice[0] != Status.NOT_EXIST) {
         ((License) containSlice[0]).setSlice(newSlice);
         dataDealer.saveData(Table.LICENSE.getTableName(), userName, containSlice[0]);
      }
      return null;
   }

}
