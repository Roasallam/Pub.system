package com.training.server.work.protocols.publicationProtocols;

import com.training.server.work.DB.daoImplemnters.PublicationDAOImp;
import com.training.server.work.Status;
import com.training.server.work.authentication.Authenticator;
import com.training.server.work.protocols.Protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublicationCreate implements Protocol {

   private PublicationDAOImp publicationDAOImp;
   private String statement;
   private String journalName;
   private String content;

   public PublicationCreate(String statement) {
      this.statement = statement;
      publicationDAOImp = new PublicationDAOImp();
   }

   private String createPublication () {

      if (!checkSyntax())
         return "Syntax error";

      // check license

      if (Authenticator.writePrivileged(journalName) == Status.LICENSE_ACTIVE)
         return "Publication ID is: " + publicationDAOImp.createPublication(journalName, content);

      return Authenticator.writePrivileged(journalName).getMsg();
   }


   @Override
   public String getResult() {

      return createPublication();
   }

   @Override
   public boolean checkSyntax() {

      String createRegex = "^CREATE\\sIN\\sJOURNAL\\s[a-zA-Z_0-9]+\\sCONTENT\\s.*";

      Pattern createPattern = Pattern.compile(createRegex, Pattern.CASE_INSENSITIVE);

      Matcher createMatcher = createPattern.matcher(statement);

      if (createMatcher.find()) {

         String [] data = createMatcher.group().split(" ");
         journalName = data[3];
         content = statement;
         content = content.replaceAll("(?i)^CREATE\\sIN\\sJOURNAL\\s[a-zA-Z_0-9]+\\sCONTENT\\s", "");
         return true;
      }
      return false;
   }
}
