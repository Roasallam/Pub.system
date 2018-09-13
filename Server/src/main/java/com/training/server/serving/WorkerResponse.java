package com.training.server.serving;

import com.training.server.work.Status;
import com.training.server.work.protocols.Factory;
import com.training.server.work.protocols.Protocol;
import com.training.server.work.protocols.ProtocolFactory;
import com.training.server.work.protocols.Protocols;

import java.io.*;
import java.net.Socket;

/**
 * each client request has
 * a worker that will respond to him
 * according to the service
 * requested by the client.
 */

public class WorkerResponse implements Runnable {

   private Socket clientSocket;

   WorkerResponse(Socket clientSocket) {
      this.clientSocket = clientSocket;
   }

   /**
    * start execution of the worker
    * and return the response back to
    * the client
    */

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

   /**
    * identifies the specified protocol
    * for the requested service and returns
    * the result of the execution of that protocol
    * @param requestService service which requested by the client
    * @param statement statement from the client
    * @return the result of the execution of the protocol if
    * its acknowledged, {@code UNKNOWN_PROTOCOL} msg if the
    * protocol is not acknowledged
    */

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
