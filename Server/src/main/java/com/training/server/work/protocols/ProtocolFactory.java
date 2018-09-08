package com.training.server.work.protocols;

import com.training.server.work.protocols.publicationProtocols.PublicationContentUpdate;
import com.training.server.work.protocols.publicationProtocols.PublicationCreate;
import com.training.server.work.protocols.publicationProtocols.PublicationDelete;
import com.training.server.work.protocols.publicationProtocols.PublicationRead;
import com.training.server.work.protocols.userProtocols.UserDelete;
import com.training.server.work.protocols.userProtocols.UserSignUp;
import com.training.server.work.protocols.userProtocols.UserUpdatePassword;

public class ProtocolFactory extends Factory {


   @Override
   public Protocol acknowledgeProtocol(Protocols protocolType, String statement) {

      if (protocolType == null)
         return null;

      switch (protocolType) {

         case NEW_USER: return new UserSignUp(statement);

         case NEW_JOURNAL: return new UserSignUp(statement);

         case NEW_ADMIN: return new UserSignUp(statement);

         case UPDATE_PASSWORD: return new UserUpdatePassword(statement);

         case DELETE_USER: return new UserDelete(statement);

         case UPDATE_CONTENT: return new PublicationContentUpdate(statement);

         case READ_PUBLICATION: return new PublicationRead(statement);

         case CREATE_PUBLICATION: return new PublicationCreate(statement);

         case DELETE_PUBLICATION: return new PublicationDelete(statement);

         default: return null;
      }
   }
}
