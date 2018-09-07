package com.training.server.work.protocols.publicationProtocols;

import com.training.server.work.DB.daoImplemnters.PublicationDAOImp;
import com.training.server.work.protocols.Protocol;
import com.training.server.work.Status;

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

   private Object createPublication () {

      if (!checkSyntax())
         return Status.SYNTAX_ERROR;

      return publicationDAOImp.createPublication(journalName, content);
   }


   @Override
   public Object getResult() {

      String msg = "Syntax error";

      if (createPublication() == Status.SYNTAX_ERROR)
          return msg;

      msg = "Publication ID is: " + createPublication();
      return msg;
   }

   @Override
   public boolean checkSyntax() {

      String createRegex = "^CREATE\\sIN\\sJOURNAL\\s[a-zA-Z_0-9]+\\sCONTENT\\s.*";

      Pattern createPattern = Pattern.compile(createRegex, Pattern.CASE_INSENSITIVE);

      Matcher createMatcher = createPattern.matcher(statement);

      if (createMatcher.find()) {

         String [] data = createMatcher.group().split(" ");
         journalName = data[3];
         content = statement.toLowerCase();
         content = content.replaceAll("^CREATE\\sIN\\sJOURNAL\\s[a-zA-Z_0-9]+\\sCONTENT\\s".toLowerCase(), "");
         return true;
      }
      return false;
   }
}
