package com.training.server.work.protocols.publicationProtocols;

import com.training.server.work.DB.daoImplemnters.PublicationDAOImp;
import com.training.server.work.Status;
import com.training.server.work.authentication.Verification;
import com.training.server.work.entity.Publication;
import com.training.server.work.protocols.Protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * delete publication protocol
 */

public class PublicationDelete implements Protocol {

   private PublicationDAOImp publicationDAOImp;
   private String statement;
   private String publicationId;
   private String password;
   private String journalName;
   private Publication publication;

   private String deletePublicationRegex = "^DELETE\\s[a-zA-Z_0-9]+\\sPASSWORD\\s[a-zA-Z_0-9]+";
   private final Pattern deletePublicationPattern = Pattern.compile(deletePublicationRegex, Pattern.CASE_INSENSITIVE);
   private Matcher deletePublicationMatcher;

   /**
    * constructs a new instance of this protocol
    * and initiate it with the statement
    * @param statement statement specified from user
    */

   public PublicationDelete(String statement) {
      this.statement = statement;
      publicationDAOImp = new PublicationDAOImp();
   }

   /**
    * deletes a publication
    * 1st checks syntax of the statement sent by the user
    * if it's incorrect syntax then return
    * 2nd checks if the publication requested for deleting
    * is exist or not
    * 3rd verifies the user if he is able to delete the publication
    * @return the status of the operation
    */

   private Status deletePublication () {

      if (!checkSyntax())
         return Status.SYNTAX_ERROR;

      if (!isFound())
         return Status.NOT_EXIST;

      if (!isVerified())
         return Status.INCORRECT_PASSWORD;

      return publicationDAOImp.deletePublication(publicationId);
   }

   /**
    * returns the result of deletePublication method
    * @return returns the result of deletePublication method
    */

   @Override
   public String getResult() {

      Status status = deletePublication();

      return status.getMsg();
   }

   /**
    * checks for the statement syntax
    * @return {@code true} if and only if the syntax was correct
    * {@code false} otherwise
    */

   @Override
   public boolean checkSyntax() {

      deletePublicationMatcher = deletePublicationPattern.matcher(statement);

      if (isCorrectSyntax()) {

         String [] data = deletePublicationMatcher.group().split(" ");
         publicationId = data[1];
         password = data[3];
         return true;
      }
      return false;
   }

   private boolean isCorrectSyntax () {

      return deletePublicationMatcher.find();
   }

   /**
    * checks if the publication is exist or not
    * @return {@code true} if and only if the publication is found
    * {@code false} otherwise
    */

   private boolean isFound () {

      publication = publicationDAOImp.findById(publicationId);

      return publication != null;
   }

   /**
    * checks if the user is verified or not
    * @return @code true} if and only if the user is verified,
    * {@code false} otherwise
    */

   private boolean isVerified () {

      journalName = publication.getJournalName();

      return Verification.isValidPassword(journalName, password);
   }
}
