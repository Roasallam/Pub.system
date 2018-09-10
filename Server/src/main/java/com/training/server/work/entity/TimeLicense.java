package com.training.server.work.entity;

/**
 * There are 6 time licenses
 * available at the moment
 * PERMANENT is for Admins
 */

public enum TimeLicense {
   EXPIRED(0),FREE_TRIAL(1), QUARTER_YEAR(3), HALF_YEAR(6),
   TWO_YEARS(2), PERMANENT(7);

   private int license_id;

   TimeLicense (int license_id) {

      this.license_id = license_id;
   }

   public int getLicense_id() {
      return license_id;
   }

}
