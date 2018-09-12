package com.training.server.work.protocols.publicationProtocols;

import com.training.server.work.DB.daoImplemnters.PublicationDAOImp;
import com.training.server.work.Status;
import com.training.server.work.authentication.Authenticator;
import com.training.server.work.entity.Publication;
import com.training.server.work.protocols.Protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * read publication protocol
 */

public class PublicationRead implements Protocol {

   private PublicationDAOImp publicationDAOImp;
   private String statement;
   private String publicationId;
   private String userName;
   private Publication publication;

   private String readRegex = "^[a-zA-Z_0-9]+\\sREAD\\s[a-zA-Z_0-9]+";
   private final Pattern readPattern = Pattern.compile(readRegex, Pattern.CASE_INSENSITIVE);
   private Matcher readMatcher;

   /**
    * constructs a new instance of this protocol
    * and initiate it with the statement
    * @param statement statement specified from user
    */

   public PublicationRead(String statement) {
      this.statement = statement;
      publicationDAOImp = new PublicationDAOImp();
   }

   /**
    * returns a publication content to read by the user
    * 1st checks syntax of the statement sent by the user
    * if it's incorrect syntax then return
    * 2nd checks if the publication requested for reading
    * is exist or not
    * 3rd checks the license of the user
    * @return the content of the publication if its found and the user
    * is privileged, syntax error if the statement syntax is incorrect
    * publication not found it the publication is not exist
    */

   private String readPublication () {

      if (!checkSyntax())
         return "Syntax error";

      if (!isFound())
         return "PUBLICATION NOT FOUND";

      if (isPrivileged())
         return publication.getContent();

      return Authenticator.readPrivileged(userName).getMsg();
   }

   /**
    * returns the result of readPublication method
    * @return returns the result of readPublication method
    */

   @Override
   public String getResult() {

      return readPublication();
   }

   /**
    * checks for the statement syntax
    * @return {@code true} if and only if the syntax was correct
    * {@code false} otherwise
    */

   @Override
   public boolean checkSyntax() {

      readMatcher = readPattern.matcher(statement);

      if (isCorrectSyntax()) {
         
         String [] data = readMatcher.group().split(" ");
         publicationId = data[2];
         userName = data[0];
         return true;
      }
      return false;
   }

   private boolean isCorrectSyntax () {

      return readMatcher.find();
   }

   /**
    * checks if the publication is exist or not
    * @return {@code true} if and only if the publication is found
    * {@code false} otherwise
    */

   private boolean isFound () {

       publication = publicationDAOImp.findById(publicationId);

       if (publication != null)
          return true;

       return false;
   }

   /**
    * checks if the user is privileged to read or not
    * @return {@code true} if and only if the user is privileged to read
    * {@code false} otherwise
    */

   private boolean isPrivileged () {

      if (Authenticator.readPrivileged(userName) == Status.LICENSE_ACTIVE)
         return true;

      return false;
   }
}
