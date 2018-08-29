package com.training.server.work.memoryDB.repositories;

public interface Repository {

   void add (String name, int id, Object obj);

   boolean remove (String name, int id);

   Object get(String name, int id);
}
