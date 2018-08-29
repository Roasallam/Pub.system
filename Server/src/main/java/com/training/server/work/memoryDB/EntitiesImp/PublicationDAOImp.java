package com.training.server.work.memoryDB.EntitiesImp;

import com.training.server.work.dao.PublicationDAO;
import com.training.server.work.dao.Status;
import com.training.server.work.entity.Publication;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import com.sun.corba.se.impl.util.Utility;



public class PublicationDAOImp implements PublicationDAO {

   // private static AtomicInteger id = new AtomicInteger
   //(Utility.getNextId(Table.PUBLICATION));
   //int publicationID = id.incrementAndGet();


   @Override
   public Publication findById(int publicationId) {
      return null;
   }

   @Override
   public List<Publication> findByJournalName() {
      return null;
   }

   @Override
   public int createPublication(String journalName, String content) {
      return 0;
   }

   @Override
   public Status updatePublication(int publicationId, String newContent) {
      return null;
   }

   @Override
   public Status deletePublication(int publicationId) {
      return null;
   }








}
