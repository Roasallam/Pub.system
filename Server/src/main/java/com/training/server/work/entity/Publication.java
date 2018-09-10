package com.training.server.work.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.InputMismatchException;
import org.joda.time.LocalDate;

/**
 * Publication represents real-world entity
 * This model class is to contain the Publication DATA
 * and standard get and set methods.
 */

@XmlRootElement
public class Publication {

   private String journalName;
   private LocalDate publicationDate;
   private int publicationId;
   private String content;

   // atomic
   public Publication(int publicationId, String journalName, String content) {

      this.publicationId = publicationId;
      this.journalName = journalName;
      this.content = content;
      this.publicationDate = LocalDate.now();
   }

   /**
    * standard get/set methods.
    */

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
         throw new InputMismatchException("JOURNAL NAME cannot be null!");

      this.journalName = journalName;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {

      if (content == null)
         throw new InputMismatchException("A Publication MUST have a content to publish!");

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
   // using the publicationId field which is a unique value for each instance
}
