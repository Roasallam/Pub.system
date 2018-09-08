package com.training.client.services.publicationServices;

import com.training.client.services.Service;

public class Read implements Service {

   private String statement;

   public Read(String statement) {
      this.statement = statement;
   }

   public String askService() {
      return statement;
   }
}
