package com.training.server.work.authentication;

import com.training.server.work.Status;

import java.io.BufferedReader;
import java.io.FileReader;

public class Authenticator {

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
}
