package com.training.client.test;

import com.training.client.services.Factory;
import com.training.client.services.Service;
import com.training.client.services.ServiceFactory;
import com.training.client.services.Services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestDeleteUser {

   public static void main(String[] args) {

      Factory factory = new ServiceFactory();
      ExecutorService executorService = Executors.newFixedThreadPool(300);
      Service requestedService;

      String statement = "";

      for (int i = 0 ; i < 300 ; i++) {
         statement = "DELETE " + i + " PASSWORD " + i;
         requestedService = factory.provideService(Services.DELETE_USER, statement);
         executorService.execute(requestedService.askService());
      }
      executorService.shutdown();
   }
}
