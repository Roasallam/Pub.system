package com.training.server.work.entity;

/**
 * There are 2 privileges licenses
 * READ, READ_WRITE, only.
 */

public enum PrivilegesLicense {
      READ(1), READ_WRITE (2);

      private int license_id;

      PrivilegesLicense(int license_id) {
         this.license_id = license_id;
      }

      public int getLicense_id() {
         return license_id;
      }
}
