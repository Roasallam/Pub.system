package com.training.server.work.memoryDB.repositories;

public interface Repository {

   // id could be publicationId (int)
   // or userName (String)

   void add (String tableName, String id, Object obj);

   boolean remove (String tableName, String id);

   Object get(String tableName, String id);
}
