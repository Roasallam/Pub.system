package com.training.server.listener;

import com.training.server.work.Status;
import com.training.server.work.protocols.Factory;
import com.training.server.work.protocols.Protocol;
import com.training.server.work.protocols.ProtocolFactory;
import com.training.server.work.protocols.Protocols;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WorkerResponse implements Runnable {

   private Socket clientSocket;

   WorkerResponse(Socket clientSocket) {
      this.clientSocket = clientSocket;
   }

   @Override
   public void run() {

      try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
           BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

         String requestedService;
         String statement;
         String response = "failed reading";

         if ((requestedService = in.readLine()) != null) {
            if ((statement = in.readLine()) != null) {
               response = guideConnection(requestedService, statement);
            }
         }
         out.println(response);

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
