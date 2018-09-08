package com.training.client;

import java.io.*;
import java.net.Socket;

public class Request implements Runnable {

   int LISTENING_PORT = 1975;
   String requestedService;

   Request(String requestedService) {
      this.requestedService = requestedService;
   }

   public void run() {

      try (Socket socket = new Socket("localhost",LISTENING_PORT);
           PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
           BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

         writer.println(requestedService);

         String msg = reader.readLine();

         if (msg != null)
            System.out.println(msg);


      } catch (Exception e) {

         e.printStackTrace();

      }

   }
}
