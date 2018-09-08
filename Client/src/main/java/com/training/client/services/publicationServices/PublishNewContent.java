package com.training.client.services.publicationServices;

import com.training.client.services.Service;

public class PublishNewContent implements Service {

   private String statement;

   public PublishNewContent(String statement) {
      this.statement = statement;
   }

   public String askService() {
      return statement;
   }
}
