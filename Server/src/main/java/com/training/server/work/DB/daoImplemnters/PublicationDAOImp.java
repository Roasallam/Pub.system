package com.training.server.work.DB.daoImplemnters;

import com.training.server.work.SetUpDB;
import com.training.server.work.dao.PublicationDAO;
import com.training.server.work.Status;
import com.training.server.work.entity.Publication;
import com.training.server.work.DB.DataDealer;
import com.training.server.work.DB.Table;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * concrete class of PublicationDAO
 * which has the implementations of CRUD operations
 * that manipulates the Publication Data.
 */

public class PublicationDAOImp implements PublicationDAO {

   private final DataDealer dataDealer;

   /**
    * constructs a new instance
    * and gets the instance of the data dealer
    * which provides data to manipulate
    * using this class methods
    */

   public PublicationDAOImp() {
      this.dataDealer = SetUpDB.getInstance();
   }

   private static AtomicInteger idCounter = new AtomicInteger();

   /**
    * finds the value publication to which the specified key publication id
    * is mapped,
    * @param publicationId id for the publication
    * @return {@code publication} if it exist, {@code null} otherwise
    */

   @Override
   public Publication findById(String publicationId) {

      if (publicationId == null)
         return null;

      // data retrieved could be a Status , or a Publication object

      Object [] containPublication = {""};
      containPublication[0] = dataDealer.retrieveData(Table.CONTENT.getTableName(), publicationId);

      if (containPublication[0] != Status.NOT_EXIST)
         return  ((Publication)containPublication[0]);

      return null;
   }

   /**
    * creates a new publication that belongs
    * to {@param journalName} and has a content
    * to publish,
    * each new publication has a new id
    * provided by method createID
    * @param journalName journalName that the publication
    *                    belongs to
    * @param content content of the publication
    * @return the id of the publication
    */

   @Override
   public String createPublication(String journalName, String content) {

      if (journalName == null || content == null)
         return "ERROR,TRY AGAIN, CONTENT CAN'T BE NULL";

      int publicationId = createID();
      Publication publication = new Publication(publicationId, journalName, content);

      dataDealer.saveData(Table.CONTENT.getTableName(), String.valueOf(publicationId), publication);
      return String.valueOf(publicationId);
   }

   /**
    * updates a publication content to the specified new content
    * @param publicationId publication id
    * @param newContent the content of the publication
    * @return the status of the operation
    */

   @Override
   public Status updatePublication(String publicationId, String newContent) {

      if (publicationId == null || newContent == null)
         return Status.ERROR;

      Object [] containPublication = {""};
      containPublication[0] = dataDealer.retrieveData(Table.CONTENT.getTableName(),publicationId);

      if (containPublication[0] != Status.NOT_EXIST) {

         ((Publication) containPublication[0]).setContent(newContent);
         dataDealer.saveData(Table.CONTENT.getTableName(), publicationId, containPublication[0]);

         return Status.UPDATED;
      }
     return Status.FAILED;
   }

   /**
    * deletes the publication and its contents
    * @param publicationId publication id
    * @return the status of the operation
    */

   @Override
   public Status deletePublication(String publicationId) {

      if (publicationId == null)
         return Status.ERROR;

      return dataDealer.deleteData(Table.CONTENT.getTableName(), publicationId);
   }

   /**
    * creates a new id for each new publication
    * @return the new id of the publication
    */

   private static int createID() {

      return idCounter.getAndIncrement();
   }

}
