package com.training.server.work.authentication;

import com.training.server.work.DB.daoImplemnters.LicenseDAOImp;
import com.training.server.work.Status;
import com.training.server.work.entity.License;
import com.training.server.work.entity.PrivilegesLicense;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;

/**
 * ADMIN
 */
public class Authenticator {

   private static LicenseDAOImp licenseDAOImp = new LicenseDAOImp();

   public static Status readPrivileged (String userName) {

      License license = licenseDAOImp.findByUserName(userName);

      if (license == null)
         return Status.NOT_EXIST;

      LocalDate licenseDate = license.getEnd_date();

      if (LocalDate.now().isAfter(licenseDate))
         return Status.LICENSE_EXPIRED;

      return Status.LICENSE_ACTIVE;

   }

   public static Status writePrivileged (String userName) {

      License license = licenseDAOImp.findByUserName(userName);

      if (license == null)
         return Status.NOT_EXIST;

      if (license.getPrivilegesLicense() != PrivilegesLicense.READ_WRITE )
         return Status.NOT_ALLOWD;

      LocalDate licenseDate = license.getEnd_date();

      if (LocalDate.now().isAfter(licenseDate))
         return Status.LICENSE_EXPIRED;

      return Status.LICENSE_ACTIVE;

   }



   public static Status checkAuthenticationCode (String adminCode) {

      String systemCode = null;
      try {

         BufferedReader br = new BufferedReader(new FileReader("AuthenticationCode"));
         systemCode = br.readLine();

      } catch (Exception e ) {
         e.printStackTrace();
      }

      if (adminCode != null && adminCode.equals(systemCode)) {

         return Status.VALID_CODE;
      }

      return Status.INVALID_CODE;
   }

   public static Status updateTimeLicense (String userName, License requestedLicense) {

      return Status.ERROR;
   }

   public static Status updatePrivilegesLicense (String userName, License requestedLicense){

      return Status.ERROR;
   }

   public static Status updateSlice (String userName, String requestedSlice) {

      return Status.ERROR;
   }

}
