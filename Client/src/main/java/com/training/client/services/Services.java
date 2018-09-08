package com.training.client.services;

public enum Services {

   NEW_JOURNAL ("NEW JOURNAL myName PASSWORD myPassword",1),

   NEW_USER ("NEW USER myName PASSWORD myPassword",2),

   NEW_ADMIN("NEW ADMIN myName PASSWORD myPassword CODE authenticationCode",3),

   UPDATE_PASSWORD ("UPDATE myName NEWPASSWORD myNewPassword OLDPASSWORD myOldPassword",4),

   DELETE_USER ("DELETE myName PASSWORD myPassword",5),

   DELETE_PUBLICATION ("DELETE publicationId PASSWORD myPassword",6),

   READ_PUBLICATION ("myName READ publicationId",7),

   CREATE_PUBLICATION ("CREATE IN JOURNAL myName CONTENT content",8),

   UPDATE_CONTENT ("UPDATE publicationId CONTENT myNewContent",9);


   private String protocol;
   private int id;

   Services(String protocol, int id) {
      this.protocol = protocol;
      this.id = id;
   }

   public int getId() {
      return id;
   }
}
