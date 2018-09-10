package com.training.client.services.publicationServices;

import com.training.client.Request;
import com.training.client.services.Service;

public class DeleteContent implements Service {

   private String statement;

   public DeleteContent(String statement) {
      this.statement = statement;
   }

   public Request askService() {

      String temp = "DELETE_PUBLICATION\n" + statement;

      return new Request(temp);

   }
}
