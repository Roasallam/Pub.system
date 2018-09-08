package com.training.server.work.protocols;

public enum Protocols {

   NEW_JOURNAL ("NEW_JOURNAL"),

   NEW_USER ("NEW_USER"),

   NEW_ADMIN("NEW_ADMIN"),

   UPDATE_PASSWORD ("UPDATE_PASSWORD"),

   DELETE_USER ("DELETE_USER"),

   DELETE_PUBLICATION ("DELETE_PUBLICATION"),

   READ_PUBLICATION ("READ_PUBLICATION"),

   CREATE_PUBLICATION ("CREATE_PUBLICATION"),

   UPDATE_CONTENT ("UPDATE_CONTENT");

   private String service;

   Protocols(String service) {
      this.service = service;
   }
}
