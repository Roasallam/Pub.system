package com.training.server.work;

import com.training.server.work.entity.*;


/**
 * this class was implemented to help DAO's
 * Not returning the values in case they were NULL
 * Instead return a status of the operation
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
   SYNTAX_ERROR ("SYNTAX ERROR");

   private String msg;

   Status(String msg) {
      this.msg = msg;
   }

   public String getMsg() {
      return msg;
   }

}
