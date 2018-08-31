package com.training.server.work.memoryDB.EntitiesImp;

import com.training.server.work.IdGenerator;
import com.training.server.work.dao.PublicationDAO;
import com.training.server.work.dao.Status;
import com.training.server.work.entity.Publication;
import com.training.server.work.memoryDB.DataDealer;
import com.training.server.work.memoryDB.Table;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class PublicationDAOImp implements PublicationDAO {
   private DataDealer dataDealer;

    private static AtomicInteger id =
       new AtomicInteger(IdGenerator.generateId(Table.PUBLICATION.getTableName()));

   @Override
   public Publication findById(String publicationId) {
      return null;
   }

   @Override
   public List<Publication> findByJournalName() {
      return null;
   }

   @Override
   public int createPublication(String journalName, String content) {

      int publicationId = id.incrementAndGet();
      Publication publication = new Publication(publicationId,journalName,content);


      dataDealer.saveData(Table.PUBLICATION.getTableName(),String.valueOf(publicationId),publication);
      return publicationId;
   }

   @Override
   public Status updatePublication(String publicationId, String newContent) {

      Object publication = dataDealer.retrieveData(Table.PUBLICATION.getTableName(),publicationId);

      if (publication == null)
         return Status.NOT_EXIST;

      Publication newPublication = (Publication)publication;
      newPublication.setContent(newContent);
      dataDealer.saveData(Table.PUBLICATION.getTableName(), publicationId, newPublication);

      return Status.MISSION_ACCOMPLISHED;
   }

   @Override
   public Status deletePublication(String publicationId) {
      return null;
   }








}
