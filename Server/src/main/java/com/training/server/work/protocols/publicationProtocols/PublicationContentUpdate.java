package com.training.server.work.protocols.publicationProtocols;

import com.training.server.work.DB.daoImplemnters.PublicationDAOImp;
import com.training.server.work.Status;
import com.training.server.work.protocols.Protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublicationContentUpdate implements Protocol {

   private PublicationDAOImp publicationDAOImp;
   private String statement;
   private String publicationId;
   private String newContent;

   public PublicationContentUpdate(String statement) {
      this.statement = statement;
      publicationDAOImp = new PublicationDAOImp();
   }


   private Status updateContent () {

      if (!checkSyntax())
         return Status.SYNTAX_ERROR;

      return publicationDAOImp.updatePublication(publicationId, newContent);

   }


   @Override
   public Object getResult() {

      Status status = updateContent();

      return status.getMsg();
   }

   @Override
   public boolean checkSyntax() {

      String updateRegex = "^UPDATE\\s[a-zA-Z_0-9]+\\sCONTENT\\s.*";

      Pattern updatePattern = Pattern.compile(updateRegex, Pattern.CASE_INSENSITIVE);

      Matcher updateMatcher = updatePattern.matcher(statement);

      if (updateMatcher.find()) {

         String [] data = updateMatcher.group().split(" ");
         publicationId = data[1];
         newContent = statement.toLowerCase();
         newContent = newContent.replaceAll("^UPDATE\\s[a-zA-Z_0-9]+\\sCONTENT\\s".toLowerCase(), "");
         return true;
      }

      return false;
   }
}
