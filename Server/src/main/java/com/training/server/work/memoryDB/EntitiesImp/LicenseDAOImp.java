package com.training.server.work.memoryDB.EntitiesImp;

import com.training.server.work.dao.*;
import com.training.server.work.entity.*;



public class LicenseDAOImp implements LicenseDAO {

   LicenseDAOImp () {

   }


   @Override
   public License findByUserName(String userName) {
      return null;
   }

   @Override
   public Status createLicense(String userName) {
      return null;
   }

   @Override
   public Status updateTimeLicense(String userName, TimeLicense newTimeLicense) {
      return null;
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
