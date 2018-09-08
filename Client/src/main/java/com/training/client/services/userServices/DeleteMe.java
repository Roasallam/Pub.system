package com.training.client.services.userServices;

import com.training.client.services.Service;

public class DeleteMe implements Service {

   private String statement;

   public DeleteMe(String statement) {
      this.statement = statement;
   }

   public String askService() {
      return statement;
   }
}
