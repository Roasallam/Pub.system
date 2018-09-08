package com.training.client.services.publicationServices;

import com.training.client.services.Service;

public class UpdateContent implements Service {

   private String statement;

   public UpdateContent(String statement) {
      this.statement = statement;
   }

   public String askService() {
      return null;
   }
}
