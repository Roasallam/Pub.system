package com.training.server.work.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.InputMismatchException;


/**
 * Publication Represents entity
 * this class is to contain the publication DATA
 */

@XmlRootElement
public class Publication {

   private String journalName;
   private LocalDate publicationDate;
   // define/set the publicationId when saving the publication.
   private int publicationId;

   private String content;
   // private Data type/Structure content
   // each publication has a contents ( generic data type )
   // then add it to a generic data type data structure

   private Publication () {

   }


   // solve this ,, it is not atomic
   public Publication(int publicationId, String journalName, String content) {

      if (journalName == null) throw new InputMismatchException("JOURNAL NAME  cannot be null");
      if (content == null) throw new InputMismatchException("PUBLICATION cannot be empty");

      this.publicationId = publicationId;
      this.journalName = journalName;
      this.content = content;

      // critic idea , creation of the publication differs from the publishing of it
      this.publicationDate = LocalDate.now();
   }


   public int getPublicationId() {
      return publicationId;
   }

   public void setPublicationId(int publicationId) {
      this.publicationId = publicationId;
   }

   public LocalDate getPublicationDate() {
      return publicationDate;
   }

   public void setPublicationDate(LocalDate publicationDate) {
      if (publicationDate == null)
         throw new InputMismatchException("Date cannot be null!");
      this.publicationDate = publicationDate;
   }

   public String getJournalName() {
      return journalName;
   }

   public void setJournalName(String journalName) {
      if(journalName == null)
         throw new InputMismatchException("JOURNAL NAME cannot be null!") ;
      this.journalName = journalName;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {

      // PUBLICATION must have a content to publish.

      if (content == null)
         throw new InputMismatchException("PUBLICATION cannot be empty!");
      this.content = content;

   }

   @Override
   public String toString() {
      return "Publication{" +
         "journalName='" + journalName + '\'' +
         ", publicationDate=" + publicationDate +
         ", content='" + content + '\'' +
         '}';
   }

   // No Equals and hashcode cause each instance of this class is equal to itself only
   // using the publicationId field which is a unique value for each instance , with IdGenerator
}
