package com.training.client.services.publicationServices;

import com.training.client.Request;
import com.training.client.services.Service;

public class UpdateContent implements Service {

   private String statement;

   public UpdateContent(String statement) {
      this.statement = statement;
   }

   public Request askService() {

      String temp = "UPDATE_CONTENT\n" + statement;

      return new Request(temp);
   }
}
