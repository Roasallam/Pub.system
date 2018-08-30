package com.training.server.work.dao;


/**
 * Like an Optional class
 * EFFECTIVE JAVA, ITEM 55
 * this class was implemented to help DAO's
 * Not returning the values in case they were NULL
 * Instead return a status of the operation
 */
public enum Status {

   ERROR (0), NOT_EXIST (0) , MISSION_ACCOMPLISHED (1);

   private int statusId;
   Status (int statusId ) {
      this.statusId = statusId;
   }

   public int getStatusId() {
      return statusId;
   }

   public void setStatusId(int statusId) {
      this.statusId = statusId;
   }
}
