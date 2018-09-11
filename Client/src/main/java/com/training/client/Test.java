package com.training.client;

import com.training.client.services.Factory;
import com.training.client.services.Service;
import com.training.client.services.ServiceFactory;
import com.training.client.services.Services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This client application was implemented
 * to test the server performance under
 * some amount of requests
 */

public class Test {


   public static void main(String[] args) {

      Factory factory = new ServiceFactory();
      ExecutorService executorService = Executors.newFixedThreadPool(300);
      Service requestedService;

      String statement = "";


      // 100 reader sign up

      for (int i = 0 ; i < 100 ; i++) {

         statement = "new user " + i + " password " + i;
         requestedService = factory.provideService(Services.NEW_USER, statement);
         executorService.execute(requestedService.askService());

      }

      // 100 journal sign up

      for (int i = 100 ; i < 200 ; i++) {

         statement = "new journal " + i + " password " + i;
         requestedService = factory.provideService(Services.NEW_JOURNAL, statement);
         executorService.execute(requestedService.askService());

      }

      // create publications in journal  100

      for (int i = 0 ; i < 100 ; i++) {

         statement = "CREATE IN JOURNAL 100" + " CONTENT content " + i;
         requestedService = factory.provideService(Services.CREATE_PUBLICATION, statement);
         executorService.execute(requestedService.askService());
      }

      // update contents

      for (int i = 0 ; i < 100 ; i++) {

         statement = "UPDATE " + i + " CONTENT myNewContent " + i;
         requestedService = factory.provideService(Services.UPDATE_CONTENT, statement);
         executorService.execute(requestedService.askService());

      }

      // read publications

      for (int i = 0 ; i < 100 ; i++) {

         statement = "0 " + "READ " + i;
         requestedService = factory.provideService(Services.READ_PUBLICATION, statement);
         executorService.execute(requestedService.askService());

      }

      executorService.shutdown();
   }

}
