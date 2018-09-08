package com.training.client.services.publicationServices;

import com.training.client.services.Service;

public class DeleteContent implements Service {

   private String statement;

   public DeleteContent(String statement) {
      this.statement = statement;
   }

   public String askService() {
      return statement;
   }
}
