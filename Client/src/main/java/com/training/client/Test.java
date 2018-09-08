package com.training.client;

import com.training.client.services.Factory;
import com.training.client.services.ServiceFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {


   public static void main(String[] args) {


      Factory factory = new ServiceFactory();

      String requestedService ;//factory.provideService();



      ExecutorService executorService = Executors.newFixedThreadPool(100);

      executorService.execute(new Request("NEW_ADMIN\nNEW ADMIN Bayan PASSWORD 1997 CODE AdminRoa"));

      executorService.shutdown();
   }

}
