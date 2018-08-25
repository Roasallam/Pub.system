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
   private long publicationId;

   private String content;
   // private Data type/Structure content
   // each publication has a contents ( generic data type )
   // then add it to a generic data type data structure


   private Publication(long publicationId, String journalName, String content) {

      // handle all of these if == null
      this.publicationId = publicationId;
      this.journalName = journalName;
      this.content = content;

      // critic idea , creation of the publication differs from the publishing of it
      this.publicationDate = LocalDate.now();
   }

   public long getPublicationId() {
      return publicationId;
   }

   public void setPublicationId(long publicationId) {
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
      if (content == null)
         throw new InputMismatchException("PUBLICATION cannot be null!");
      this.content = content;

      // PUBLICATION must have a content to publish.
   }

   @Override
   public String toString() {
      return "Publication{" +
         "journalName='" + journalName + '\'' +
         ", publicationDate=" + publicationDate +
         ", content='" + content + '\'' +
         '}';
   }
}
