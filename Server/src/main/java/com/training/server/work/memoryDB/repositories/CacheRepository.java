package com.training.server.work.memoryDB.repositories;

public class CacheRepository implements Repository {

   @Override
   public void add(String name, int id, Object obj) {

   }

   @Override
   public boolean remove(String name, int id) {
      return false;
   }

   @Override
   public Object get(String name, int id) {
      return null;
   }
}
