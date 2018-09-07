package com.training.server.work.protocols.publicationProtocols;

import com.training.server.work.DB.daoImplemnters.PublicationDAOImp;
import com.training.server.work.Status;
import com.training.server.work.authentication.Authenticator;
import com.training.server.work.entity.Publication;
import com.training.server.work.protocols.Protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublicationRead implements Protocol {

   private PublicationDAOImp publicationDAOImp;
   private String statement;
   private String publicationId;
   private String userName;

   public PublicationRead(String statement) {
      this.statement = statement;
      publicationDAOImp = new PublicationDAOImp();
   }

   private String readPublication () {

      if (!checkSyntax())
         return "Syntax error";

      // check if publication exist

      Publication publication = publicationDAOImp.findById(publicationId);

      if (publication == null)
         return "PUBLICATION NOT FOUND";

      // check license of the reader

      if (Authenticator.readPrivileged(userName) == Status.LICENSE_ACTIVE)
         return publication.getContent();

      return Authenticator.readPrivileged(userName).getMsg();
   }


   @Override
   public String getResult() {

      return readPublication();
   }

   @Override
   public boolean checkSyntax() {

      String readRegex = "^[a-zA-Z_0-9]+\\sREAD\\s[a-zA-Z_0-9]+";

      Pattern readPattern = Pattern.compile(readRegex, Pattern.CASE_INSENSITIVE);

      Matcher readMatcher = readPattern.matcher(statement);

      if (readMatcher.find()) {
         String [] data = readMatcher.group().split(" ");
         publicationId = data[2];
         userName = data[0];
         return true;
      }
      return false;
   }
}
