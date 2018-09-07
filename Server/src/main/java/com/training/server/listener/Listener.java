package com.training.server.listener;

import com.training.server.work.Status;
import com.training.server.work.protocols.Factory;
import com.training.server.work.protocols.Protocol;
import com.training.server.work.protocols.ProtocolFactory;
import com.training.server.work.protocols.Protocols;

public class Listener {

   private final int port;
   private boolean listening = true;

   public Listener(int port) {
      this.port = port;
   }

   public void start () {


   }

   public Object recieveRequest (Protocols protocolType, String statement) {

      Factory protocolFactory = new ProtocolFactory();

      Protocol requestedProtocol = protocolFactory.proceedProtocol(protocolType, statement);

      if (requestedProtocol == null)
         return Status.UNKNOWN_PROTOCOL;

      return requestedProtocol.getResult();

   }
}
