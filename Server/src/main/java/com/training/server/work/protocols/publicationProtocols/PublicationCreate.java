package com.training.server.work.protocols.publicationProtocols;

import com.training.server.work.DB.daoImplemnters.PublicationDAOImp;
import com.training.server.work.Status;
import com.training.server.work.authentication.Authenticator;
import com.training.server.work.protocols.Protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create new publication and publish new content protocol
 */

public class PublicationCreate implements Protocol {

   private PublicationDAOImp publicationDAOImp;
   private String statement;
   private String journalName;
   private String content;

   private String createRegex = "^CREATE\\sIN\\sJOURNAL\\s[a-zA-Z_0-9]+\\sCONTENT\\s.*";
   private final Pattern createPattern = Pattern.compile(createRegex, Pattern.CASE_INSENSITIVE);
   private Matcher createMatcher;

   /**
    * constructs a new instance of this protocol
    * and initiate it with the statement
    * @param statement statement specified from user
    */

   public PublicationCreate(String statement) {
      this.statement = statement;
      publicationDAOImp = new PublicationDAOImp();
   }

   /**
    * create a new publication with a content to publish
    * 1st checks syntax of the statement sent by the user
    * if it's incorrect syntax then return
    * 2nd checks if the user is privileged to write
    * @return publication id if the operation succeed
    */

   private String createPublication () {

      if (!checkSyntax())
         return "Syntax error";

      if (isPrivileged())
         return "Publication ID is: " + publicationDAOImp.createPublication(journalName, content);

      return Authenticator.writePrivileged(journalName).getMsg();
   }

   /**
    * returns the result of createPublication method
    * @return returns the result of createPublication method
    */

   @Override
   public String getResult() {

      return createPublication();
   }

   /**
    * checks for the statement syntax
    * @return {@code true} if and only if the syntax was correct
    * {@code false} otherwise
    */

   @Override
   public boolean checkSyntax() {

      createMatcher = createPattern.matcher(statement);

      if (isCorrectSyntax()) {

         String [] data = createMatcher.group().split(" ");
         journalName = data[3];
         content = statement;
         content = content.replaceAll("(?i)^CREATE\\sIN\\sJOURNAL\\s[a-zA-Z_0-9]+\\sCONTENT\\s", "");
         return true;
      }
      return false;
   }

   private boolean isCorrectSyntax () {

      return createMatcher.find();
   }

   /**
    * checks if the user is privileged to write or not
    * @return {@code true} if and only if the user is privileged to write
    * {@code false} otherwise
    */

   private boolean isPrivileged () {

      if (Authenticator.writePrivileged(journalName) == Status.LICENSE_ACTIVE)
         return true;

      return false;
   }
}
