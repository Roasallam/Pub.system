package com.training.server.work.memoryDB.daoImplemnters;

import com.training.server.work.SetUpDB;
import com.training.server.work.dao.PublicationDAO;
import com.training.server.work.Status;
import com.training.server.work.entity.Publication;
import com.training.server.work.memoryDB.DataDealer;
import com.training.server.work.memoryDB.Table;

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
         throw new NullPointerException();

      // didn't use Publication publication = .. , cause that will return the publication object
      // not the already existing publication.
      // data retrieved could be a Status , or a Publication ..2 different instances.

      Object [] containPublication = {null};
      containPublication[0] = dataDealer.retrieveData(Table.PUBLICATION.getTableName(), publicationId);

      if (containPublication[0] != Status.NOT_EXIST)
         return (Publication) containPublication[0];

      return null;
   }


   @Override
   public int createPublication(String journalName, String content) {

      if (journalName == null || content == null)
         throw new NullPointerException();

      int publicationId = createID();
      Publication publication = new Publication(publicationId, journalName, content);


      dataDealer.saveData(Table.PUBLICATION.getTableName(), String.valueOf(publicationId), publication);
      return publicationId;
   }

   @Override
   public Status updatePublication(String publicationId, String newContent) {

      if (publicationId == null || newContent == null)
         return Status.ERROR;

      Object [] containPublication = {null};
      containPublication[0] = dataDealer.retrieveData(Table.PUBLICATION.getTableName(),publicationId);

      if (containPublication[0] != Status.NOT_EXIST) {

         ((Publication) containPublication[0]).setContent(newContent);
         dataDealer.saveData(Table.PUBLICATION.getTableName(), publicationId, containPublication[0]);

         return Status.MISSION_ACCOMPLISHED;
      }
     return Status.FAILED;
   }

   @Override
   public Status deletePublication(String publicationId) {

      if (publicationId == null)
         return Status.ERROR;

      return dataDealer.deleteData(Table.PUBLICATION.getTableName(), publicationId);

   }

   private static int createID() {

      return idCounter.getAndIncrement();
   }








}
