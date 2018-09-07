package com.training.server.work.protocols.publicationProtocols;

import com.training.server.work.DB.daoImplemnters.PublicationDAOImp;
import com.training.server.work.Status;
import com.training.server.work.entity.Publication;
import com.training.server.work.protocols.Protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublicationRead implements Protocol {

   private PublicationDAOImp publicationDAOImp;
   private String statement;
   private String publicationId;

   public PublicationRead(String statement) {
      this.statement = statement;
      publicationDAOImp = new PublicationDAOImp();
   }

   private Publication readPublication () {

      if (!checkSyntax())
         return null;

      return publicationDAOImp.findById(publicationId);

   }


   @Override
   public Object getResult() {

      if (readPublication() == null)
         return Status.NOT_EXIST;

      return readPublication();
   }

   @Override
   public boolean checkSyntax() {

      String readRegex = "^READ\\s[a-zA-Z_0-9]+";

      Pattern readPattern = Pattern.compile(readRegex, Pattern.CASE_INSENSITIVE);

      Matcher readMatcher = readPattern.matcher(statement);

      if (readMatcher.find()) {
         String [] data = readMatcher.group().split(" ");
         publicationId = data[1];
         return true;
      }
      return false;
   }
}
