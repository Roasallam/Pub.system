package com.training.server.work.protocols.publicationProtocols;

import com.training.server.work.DB.daoImplemnters.PublicationDAOImp;
import com.training.server.work.Status;
import com.training.server.work.protocols.Protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublicationDelete implements Protocol {

   private PublicationDAOImp publicationDAOImp;
   private String statement;
   private String publicationId;

   public PublicationDelete(String statement) {
      this.statement = statement;
      publicationDAOImp = new PublicationDAOImp();
   }

   private Status deletePublication () {

      if (!checkSyntax())
         return Status.SYNTAX_ERROR;

      return publicationDAOImp.deletePublication(publicationId);

   }

   @Override
   public String getResult() {

      Status status = deletePublication();

      return status.getMsg();
   }

   @Override
   public boolean checkSyntax() {


      String deletePublicationRegex = "^DELETE\\s[a-zA-Z_0-9]+";

      Pattern deletePublicationPattern = Pattern.compile(deletePublicationRegex, Pattern.CASE_INSENSITIVE);

      Matcher deletePublicationMatcher = deletePublicationPattern.matcher(statement);

      if (deletePublicationMatcher.find()) {
         String [] data = deletePublicationMatcher.group().split(" ");
         publicationId = data[1];
         return true;
      }
      return false;
   }
}
