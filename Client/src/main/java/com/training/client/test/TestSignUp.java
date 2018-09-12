package com.training.client.test;

import com.training.client.services.Factory;
import com.training.client.services.Service;
import com.training.client.services.ServiceFactory;
import com.training.client.services.Services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestSignUp {

   public static void main(String[] args) {

      Factory factory = new ServiceFactory();
      ExecutorService executorService = Executors.newFixedThreadPool(300);
      Service requestedService;

      String statement = "";

      for (int i = 0 ; i < 100 ; i++) {

         statement = "new user " + i + " password " + i;
         requestedService = factory.provideService(Services.NEW_USER, statement);
         executorService.execute(requestedService.askService());
      }

      for (int i = 100 ; i < 200 ; i++) {

         statement = "new journal " + i + " password " + i;
         requestedService = factory.provideService(Services.NEW_JOURNAL, statement);
         executorService.execute(requestedService.askService());
      }

      for (int i = 200 ; i < 300 ; i++) {

         statement = "new admin " + i + " password " + i + " code AdminRoa";
         requestedService = factory.provideService(Services.NEW_ADMIN, statement);
         executorService.execute(requestedService.askService());
      }
      executorService.shutdown();
   }
}
