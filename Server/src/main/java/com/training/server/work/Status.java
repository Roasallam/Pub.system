package com.training.server.work;

/**
 * This class was implemented to help other classes
 * inform the user the status of the operation
 * were requested for.
 */
public enum Status {

   ERROR ("ERROR"),
   NOT_EXIST ("NOT EXIST, CHECK AGAIN"),
   ALREADY_EXISTS ("ALREADY EXIST, TRY AGAIN"),
   FAILED ("FAILED"),
   MISSION_ACCOMPLISHED ("MISSION ACCOMPLISHED"),
   INCORRECT_PASSWORD ("INCORRECT PASSWORD"),
   WELCOME("WELCOME"),
   VALID_CODE ("VALID CODE"),
   INVALID_CODE ("INVALID CODE"),
   SYNTAX_ERROR ("SYNTAX ERROR"),
   LICENSE_EXPIRED ("LICENSE_EXPIRED"),
   NOT_ALLOWED ("NOT_ALLOWED"),
   LICENSE_ACTIVE ("LICENSE_ACTIVE"),
   UPDATED ("UPDATED"),
   DELETED ("DELETED"),
   UNKNOWN_PROTOCOL ("UNKNOWN_PROTOCOL");

   private String msg;

   Status(String msg) {
      this.msg = msg;
   }

   public String getMsg() {
      return msg;
   }

}
