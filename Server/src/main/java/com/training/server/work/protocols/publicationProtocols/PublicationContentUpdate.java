package com.training.server.work.protocols.publicationProtocols;

import com.training.server.work.DB.daoImplemnters.PublicationDAOImp;
import com.training.server.work.Status;
import com.training.server.work.authentication.Authenticator;
import com.training.server.work.entity.Publication;
import com.training.server.work.protocols.Protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * update publication's content protocol
 */

public class PublicationContentUpdate implements Protocol {

   private PublicationDAOImp publicationDAOImp;
   private String statement;
   private String publicationId;
   private String newContent;
   private String journalName;
   private Publication publication;

   private String updateRegex = "^UPDATE\\s[a-zA-Z_0-9]+\\sCONTENT\\s.*";
   private final Pattern updatePattern = Pattern.compile(updateRegex, Pattern.CASE_INSENSITIVE);
   private Matcher updateMatcher;

   /**
    * constructs a new instance of this protocol
    * and initiate it with the statement
    * @param statement statement specified from user
    */

   public PublicationContentUpdate(String statement) {
      this.statement = statement;
      publicationDAOImp = new PublicationDAOImp();
   }

   /**
    * updates a publication content
    * 1st checks syntax of the statement sent by the user
    * if it's incorrect syntax then return
    * 2nd checks if the publication requested for updating
    * is exist or not
    * 3rd checks if the user is privileged to write
    * @return the status of the operation
    */

   private Status updateContent () {

      if (!checkSyntax())
         return Status.SYNTAX_ERROR;

      if (!isFound())
         return Status.NOT_EXIST;

      if (isPrivileged())
         return publicationDAOImp.updatePublication(publicationId, newContent);

      return Authenticator.writePrivileged(journalName);
   }

   /**
    * returns the result of PublicationContentUpdate method
    * @return returns the result of PublicationContentUpdate method
    */

   @Override
   public String getResult() {

      Status status = updateContent();

      return status.getMsg();
   }

   /**
    * checks for the statement syntax
    * @return {@code true} if and only if the syntax was correct
    * {@code false} otherwise
    */

   @Override
   public boolean checkSyntax() {

      updateMatcher = updatePattern.matcher(statement);

      if (isCorrectSyntax()) {

         String [] data = updateMatcher.group().split(" ");
         publicationId = data[1];
         newContent = statement;
         newContent = newContent.replaceAll("(?i)^UPDATE\\s[a-zA-Z_0-9]+\\sCONTENT\\s", "");
         return true;
      }
      return false;
   }

   private boolean isCorrectSyntax () {

      return updateMatcher.find();
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
    * checks if the user is privileged to write or not
    * @return {@code true} if and only if the user is privileged to write
    * {@code false} otherwise
    */

   private boolean isPrivileged () {

      journalName = publication.getJournalName();

      return (Authenticator.writePrivileged(journalName) == Status.LICENSE_ACTIVE);
   }
}
