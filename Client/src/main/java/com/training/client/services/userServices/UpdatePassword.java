package com.training.client.services.userServices;

import com.training.client.Request;
import com.training.client.services.Service;

public class UpdatePassword implements Service {

   private String statement;

   public UpdatePassword(String statement) {
      this.statement = statement;
   }

   public Request askService() {

      String temp = "UPDATE_PASSWORD\n" + statement;

      return new Request(temp);
   }
}
