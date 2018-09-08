package com.training.client.services;

import com.training.client.services.publicationServices.DeleteContent;
import com.training.client.services.publicationServices.PublishNewContent;
import com.training.client.services.publicationServices.Read;
import com.training.client.services.publicationServices.UpdateContent;
import com.training.client.services.userServices.DeleteMe;
import com.training.client.services.userServices.SignUp;
import com.training.client.services.userServices.UpdatePassword;

public class ServiceFactory extends Factory {

   public ServiceFactory() {
   }

   public Service provideService(Services request, String statement) {

      if (request == null)
         return null;

      switch (request.getId()) {

         case 1:
         case 2:
         case 3: return new SignUp(statement);

         case 4: return new UpdatePassword(statement);

         case 5: return new DeleteMe(statement);

         case 6: return new DeleteContent(statement);

         case 7: return new Read(statement);

         case 8: return new PublishNewContent(statement);

         case 9: return new UpdateContent(statement);

            default: return null;
      }
   }
}
