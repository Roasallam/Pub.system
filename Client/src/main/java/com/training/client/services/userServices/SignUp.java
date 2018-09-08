package com.training.client.services.userServices;

import com.training.client.services.Service;

public class SignUp implements Service {

   private String statement;

   public SignUp(String statement) {
      this.statement = statement;
   }

   public String askService() {
      return statement;
   }
}
