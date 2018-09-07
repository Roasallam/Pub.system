package com.training.server.work.DB.daoImplemnters;

import com.training.server.work.SetUpDB;
import com.training.server.work.dao.PublicationDAO;
import com.training.server.work.Status;
import com.training.server.work.entity.Publication;
import com.training.server.work.DB.DataDealer;
import com.training.server.work.DB.Table;

import java.util.concurrent.atomic.AtomicInteger;


public class PublicationDAOImp implements PublicationDAO {

   private final DataDealer dataDealer;

   public PublicationDAOImp() {
      this.dataDealer = SetUpDB.getInstance();
   }

   private static AtomicInteger idCounter = new AtomicInteger();

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


   @Override
   public String createPublication(String journalName, String content) {

      if (journalName == null || content == null)
         return "ERROR,TRY AGAIN, CONTENT CAN'T BE NULL";

      int publicationId = createID();
      Publication publication = new Publication(publicationId, journalName, content);

      dataDealer.saveData(Table.CONTENT.getTableName(), String.valueOf(publicationId), publication);
      return String.valueOf(publicationId);
   }

   @Override
   public Status updatePublication(String publicationId, String newContent) {

      if (publicationId == null || newContent == null)
         return Status.ERROR;

      // data retrieved could be a Status , or a Publication object

      Object [] containPublication = {""};
      containPublication[0] = dataDealer.retrieveData(Table.CONTENT.getTableName(),publicationId);

      if (containPublication[0] != Status.NOT_EXIST) {

         ((Publication) containPublication[0]).setContent(newContent);
         dataDealer.saveData(Table.CONTENT.getTableName(), publicationId, containPublication[0]);

         return Status.UPDATED;
      }
     return Status.FAILED;
   }

   @Override
   public Status deletePublication(String publicationId) {

      if (publicationId == null)
         return Status.ERROR;

      return dataDealer.deleteData(Table.CONTENT.getTableName(), publicationId);

   }

   private static int createID() {

      return idCounter.getAndIncrement();
   }








}
