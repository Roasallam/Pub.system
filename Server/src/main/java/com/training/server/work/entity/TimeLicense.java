package com.training.server.work.entity;

import java.time.LocalDate;

public enum TimeLicense {
   EXPIRED(0),FREE_TRIAL(1), QUARTER_YEAR(3), HALF_YEAR(6),
   TWO_YEARS(2), PERMANENT(7);

   private int license_id;
   private LocalDate start_date;
   private LocalDate end_date;

   TimeLicense (int license_id) {

      this.license_id = license_id;
   }

   public int getLicense_id() {
      return license_id;
   }

   public void setLicense_id(int license_id) {
      this.license_id = license_id;
   }

   public LocalDate getStart_date() {
      return start_date;
   }

   public void setStart_date(LocalDate start_date) {
      this.start_date = start_date;
   }

   public LocalDate getEnd_date() {
      return end_date;
   }

   public void setEnd_date(LocalDate end_date) {
      this.end_date = end_date;
   }

}
