package com.training.client.protocols;

public enum Protocols {

   NEW_JOURNAL ("NEW JOURNAL myName PASSWORD myPassword"),

   NEW_USER ("NEW USER myName PASSWORD myPassword"),

   NEW_ADMIN("NEW ADMIN myName PASSWORD myPassword CODE authenticationCode"),

   UPDATE_PASSWORD ("UPDATE myName NEWPASSWORD myNewPassword OLDPASSWORD myOldPassword"),

   DELETE_USER ("DELETE myName PASSWORD myPassword"),

   DELETE_PUBLICATION ("DELETE publicationId PASSWORD myPassword"),

   READ_PUBLICATION ("myName READ publicationId"),

   CREATE_PUBLICATION ("CREATE IN JOURNAL myName CONTENT content"),

   UPDATE_CONTENT ("UPDATE publicationId CONTENT myNewContent");


   private String statement;

   Protocols(String statement) {
      this.statement = statement;
   }

   public String getStatement() {
      return statement;
   }

   public void setStatement(String statement) {
      this.statement = statement;
   }
}
