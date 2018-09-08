package com.training.client.services.userServices;

import com.training.client.services.Service;

public class UpdatePassword implements Service {

   private String statement;

   public UpdatePassword(String statement) {
      this.statement = statement;
   }

   public String askService() {
      return statement;
   }
}
