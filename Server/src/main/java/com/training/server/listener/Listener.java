package com.training.server.listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Listener {

   private static final int port = 1975;
   private static boolean listening = true;
   private static ExecutorService executorService;


   // setting up thread pool while loading the class
   // server can serve 100 connections at a time
   static {

      executorService = Executors.newFixedThreadPool(100);
   }

   public static void start () {

      try (ServerSocket serverSocket = new ServerSocket(port)) {

         while (listening) {

            // server is waiting for clients requests

            Socket clientSocket = serverSocket.accept();

            // execute each connection/request in a thread, to manage concurrent connections

            executorService.execute(new WorkerResponse(clientSocket));
         }
      } catch (IOException e ) {

         e.printStackTrace();
      }

   }
}
