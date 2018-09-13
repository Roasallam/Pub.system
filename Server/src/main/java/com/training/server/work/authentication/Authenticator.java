package com.training.server.work.authentication;

import com.training.server.work.DB.daoImplemnters.LicenseDAOImp;
import com.training.server.work.Status;
import com.training.server.work.entity.License;
import com.training.server.work.entity.PrivilegesLicense;

import java.io.BufferedReader;
import java.io.FileReader;
import org.joda.time.LocalDate;

/**
 * ADMIN
 * An admin of the database server
 * have full rights and give authorities
 * to users
 * An admin is verified using the authentication code written in a file
 * through {@code checkAuthenticationCode}.
 *
 * Each time a user request for a read operation or
 * a journal requests to publish a content
 * the admin checks their licenses if they were active
 * or expired.
 */

public class Authenticator {

   private static LicenseDAOImp licenseDAOImp = new LicenseDAOImp();

   /**
    * checks if the user is privileged to read a content
    * 1st checks if the license of the user exist
    * then checks if the license passed its end date
    * if so then its expired and the user is not able to read
    * otherwise its privileged to read
    * @param userName userName specified for the user
    * @return the status of the license if found,
    * {@code NOT_EXIST} otherwise
    */

   public static Status readPrivileged (String userName) {

      License license = licenseDAOImp.findByUserName(userName);

      if (license == null)
         return Status.NOT_EXIST;

      if (isExpired(license))
         return Status.LICENSE_EXPIRED;

      return Status.LICENSE_ACTIVE;
   }

   /**
    * checks if the journal is privileged to publish a content
    * or a user to write anything,
    * 1st checks if the user can write or its not allowed
    * 2nd checks for end date of the license if its passed or not
    * @param userName userName specified for the user
    * @return the status of the license if found,
    * {@code NOT_EXIST} otherwise
    */

   public static Status writePrivileged (String userName) {

      License license = licenseDAOImp.findByUserName(userName);

      if (license == null)
         return Status.NOT_EXIST;

      if (license.getPrivilegesLicense() != PrivilegesLicense.READ_WRITE )
         return Status.NOT_ALLOWED;

      if (isExpired(license))
         return Status.LICENSE_EXPIRED;

      return Status.LICENSE_ACTIVE;
   }

   /**
    * verifies an admin holding its code
    * if his code is equal to system authentication code
    * then its valid, otherwise he is not verified
    * @param adminCode code with the admin
    * @return {@code VALID} code if and only if it equals system code,
    * {@code INVALID} otherwise
    */

   public static Status checkAuthenticationCode (String adminCode) {


      try (BufferedReader br = new BufferedReader(new FileReader("AuthenticationCode"))){

         String systemCode = br.readLine();

         if (adminCode != null && adminCode.equals(systemCode)) {

            return Status.VALID_CODE;
         }

      } catch (Exception e ) {

         throw new AssertionError(e);
      }
      return Status.INVALID_CODE;
   }

   /**
    * checks if a specified license
    * is expired or not
    * @param license specified license to check its availability
    * @return {@code true} if license is expired
    * {@code false} otherwise
    */

   private static boolean isExpired (License license) {

      LocalDate licenseDate = license.getEnd_date();

      return LocalDate.now().isAfter(licenseDate);
   }
}
