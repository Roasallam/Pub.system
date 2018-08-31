package com.training.server.work.memoryDB.repositories;

public interface Repository {

   // id could be publicationId (int)
   // or userName (String)

   void add (String name, String id, Object obj);

   boolean remove (String name, String id);

   Object get(String name, String id);

}
