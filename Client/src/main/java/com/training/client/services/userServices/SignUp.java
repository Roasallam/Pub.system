package com.training.client.services.userServices;

import com.training.client.Request;
import com.training.client.services.Service;

public class SignUp implements Service {

   private String statement;
   private int id;

   public SignUp(String statement, int id) {
      this.statement = statement;
      this.id = id;
   }

   public Request askService() {

      String temp = "";

      switch (id) {
         case 1: temp = "NEW_JOURNAL\n" + statement;
            break;
         case 2: temp = "NEW_USER\n" + statement;
            break;
         case 3: temp = "NEW_ADMIN\n" + statement;
            break;
      }

      return new Request(temp);
   }
}
