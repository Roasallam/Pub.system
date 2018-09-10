package com.training.server.work.entity;

import javax.xml.bind.annotation.XmlRootElement;
import org.joda.time.LocalDate;
import java.util.InputMismatchException;


/**
 * License represents real-world entity
 * This model class is to contain the License DATA
 * and standard get and set methods.
 */

@XmlRootElement
public class License {

   private String userName;
   private TimeLicense timeLicense;
   private PrivilegesLicense privilegesLicense;
   private String slice;
   private LocalDate start_date;
   private LocalDate end_date;

   private License () {}

   License (Builder builder) {

      userName = builder.userName;
      timeLicense = builder.timeLicense;
      privilegesLicense = builder.privilegesLicense;
      slice = builder.slice;
      start_date = builder.start_date;
      end_date = builder.end_date;
   }

   /**
    * Builder inner class, instead of
    * telescoping constructor pattern,
    * due to the number of optional
    * parameters.
    */
   public static class Builder {

      // Required parameters
      private String userName;

      // Optional parameters - initialized to default values
      private TimeLicense timeLicense = TimeLicense.FREE_TRIAL;
      private PrivilegesLicense privilegesLicense = PrivilegesLicense.READ;
      private String slice = "Publication date";
      private LocalDate start_date = LocalDate.now();
      private LocalDate end_date = LocalDate.now().plusDays(20);

      public Builder (String userName) {
         this.userName = userName;
      }

      public Builder setTimeLicense (TimeLicense tl) {
         timeLicense = tl; return this;
      }

      public Builder setPrivilegesLicense (PrivilegesLicense pl) {
         privilegesLicense = pl; return this;
      }

      public Builder setSlice (String s) {
         slice = s; return this;
      }

      public Builder setStart_date (LocalDate sd) {
         start_date = sd; return this;
      }

      public Builder setEnd_date (LocalDate ed) {
         end_date = ed; return this;
      }

      /**
       * using this build method
       * the client will choose any optional parameter to set
       * if some optional parameter is not defined by the client,
       * it will have its default value.
       * @return a license object
       */
      public License build() {

         return new License(this);
      }
   }

   /**
    * standard get/set methods.
    */

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public TimeLicense getTimeLicense() {
      return timeLicense;
   }

   public void setTimeLicense(TimeLicense timeLicense) {

      if (timeLicense == null)
         throw new InputMismatchException("there must be a time license");

      this.timeLicense = timeLicense;
   }

   public PrivilegesLicense getPrivilegesLicense() {
      return privilegesLicense;
   }

   public void setPrivilegesLicense(PrivilegesLicense privilegesLicense) {

      if (privilegesLicense == null)
         throw new InputMismatchException("there must be a privileges license");

      this.privilegesLicense = privilegesLicense;
   }

   public String getSlice() {
      return slice;
   }

   public void setSlice(String slice) {

      if (slice == null)
         throw new InputMismatchException("slice cannot be null");

      this.slice = slice;
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

      if (end_date == null)
         throw new InputMismatchException("end date cannot be null");

      this.end_date = end_date;
   }

   @Override
   public String toString() {
      return "License{" +
         "userName='" + userName + '\'' +
         ", timeLicense=" + timeLicense +
         ", privilegesLicense=" + privilegesLicense +
         ", slice='" + slice + '\'' +
         ", start_date=" + start_date +
         ", end_date=" + end_date +
         '}';
   }

   // No Equals and hashcode are overridden cause each instance of this class is equal to only itself
   // using the userName field which is a unique value for each instance

}
