package com.training.server.work.protocols;


public abstract class Factory {

   public abstract Protocol acknowledgeProtocol(Protocols protocolType, String statement);
}
