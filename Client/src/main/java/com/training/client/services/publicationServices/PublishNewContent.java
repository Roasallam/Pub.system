package com.training.client.services.publicationServices;

import com.training.client.Request;
import com.training.client.services.Service;

public class PublishNewContent implements Service {

   private String statement;

   public PublishNewContent(String statement) {
      this.statement = statement;
   }

   public Request askService() {

      String temp = "CREATE_PUBLICATION\n" + statement;

      return new Request(temp);
   }
}
