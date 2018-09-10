package com.training.client.services.publicationServices;

import com.training.client.Request;
import com.training.client.services.Service;

public class Read implements Service {

   private String statement;

   public Read(String statement) {
      this.statement = statement;
   }

   public Request askService() {

      String temp = "READ_PUBLICATION\n" + statement;

      return new Request(temp);
   }
}
