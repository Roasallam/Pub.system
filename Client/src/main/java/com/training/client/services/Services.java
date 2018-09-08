package com.training.client.services;

public enum Services {

   NEW_JOURNAL ("NEW JOURNAL myName PASSWORD myPassword"),

   NEW_USER ("NEW USER myName PASSWORD myPassword"),

   NEW_ADMIN("NEW ADMIN myName PASSWORD myPassword CODE authenticationCode"),

   UPDATE_PASSWORD ("UPDATE myName NEWPASSWORD myNewPassword OLDPASSWORD myOldPassword"),

   DELETE_USER ("DELETE myName PASSWORD myPassword"),

   DELETE_PUBLICATION ("DELETE publicationId PASSWORD myPassword"),

   READ_PUBLICATION ("myName READ publicationId"),

   CREATE_PUBLICATION ("CREATE IN JOURNAL myName CONTENT content"),

   UPDATE_CONTENT ("UPDATE publicationId CONTENT myNewContent");


   private String protocol;

   Services(String protocol) {
      this.protocol = protocol;
   }
}
