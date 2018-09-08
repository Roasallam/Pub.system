package com.training.server.serving;

import com.training.server.work.Status;
import com.training.server.work.protocols.Factory;
import com.training.server.work.protocols.Protocol;
import com.training.server.work.protocols.ProtocolFactory;
import com.training.server.work.protocols.Protocols;

import java.io.*;
import java.net.Socket;

public class WorkerResponse implements Runnable {

   private Socket clientSocket;

   WorkerResponse(Socket clientSocket) {
      this.clientSocket = clientSocket;
   }

   @Override
   public void run() {

      try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
           PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(),true)) {

         String requestedService = bufferedReader.readLine();
         String statement = bufferedReader.readLine();
         String response = "failed reading";

         if (requestedService != null) {
            if (statement != null) {
               response = guideConnection(requestedService, statement);
            }
         }

         printWriter.println(response);

      } catch (IOException e) {

         e.printStackTrace();
      }
   }

   private String guideConnection (String requestService, String statement) {

      Protocols protocolType;
      Protocol requestedProtocol;

      try {

         protocolType = Protocols.valueOf(requestService.toUpperCase());

         Factory protocolFactory = new ProtocolFactory();

         requestedProtocol = protocolFactory.acknowledgeProtocol(protocolType, statement);

      } catch (IllegalArgumentException e) {

         e.printStackTrace();
         return Status.UNKNOWN_PROTOCOL.getMsg();
      }

      return requestedProtocol.getResult();
   }
}
