package com.training.server.work.memoryDB.EntitiesImp;

import com.training.server.work.dao.PublicationDAO;
import com.training.server.work.entity.Publication;

import java.util.concurrent.atomic.AtomicInteger;
import com.sun.corba.se.impl.util.Utility;



public class PublicationDAOImp implements PublicationDAO {


   // private static AtomicInteger id = new AtomicInteger
   //(Utility.getNextId(Table.PUBLICATION));


   @Override
   public void findAll() {

   }

   @Override
   public Publication findById() {
      return null;
   }

   @Override
   public void findByJournalName() {

   }

   @Override
   public Publication createPublication(Publication publication) {

      //int publicationID = id.incrementAndGet();
      return null;
   }

   @Override
   public Publication updatePublication(Publication publication) {
      return null;
   }

   @Override
   public Publication deletePublication(Publication publication) {
      return null;
   }



}
