package com.training.server.work.entity;

public enum UserType {
   ADMIN (0) , JOURNAL (1), USER (10000);

   private int userTypeId;

   UserType (int userTypeId) {
      this.userTypeId = userTypeId;
   }

   public int getUserTypeId() {
      return userTypeId;
   }

   public void setUserTypeId(int userTypeId) {
      this.userTypeId = userTypeId;
   }

}
