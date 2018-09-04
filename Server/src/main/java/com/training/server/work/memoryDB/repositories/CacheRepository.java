package com.training.server.work.memoryDB.repositories;

import com.training.server.work.Status;
import com.training.server.work.memoryDB.cache.*;

import java.util.*;

/**
 * A Repository that contains tables to be cached
 */

public class CacheRepository implements Repository {


   private Map<String, Cacheable> cachedTables = new HashMap<>();

   public void addTable (String tableName, Cacheable cacheable) {

      cachedTables.put(tableName,cacheable);
   }

   @Override
   public void add(String tableName, String id, Object obj) {

      if (cachedTables.containsKey(tableName))
         cachedTables.get(tableName).add(id, obj);

   }

   @Override
   public boolean remove(String tableName, String id) {

      if (cachedTables.containsKey(tableName)) {

         if ((cachedTables.get(tableName).removeObj(id)))
            return true;
      }
      return false;
   }

   @Override
   public Object get(String tableName, String id) {

      if (cachedTables.containsKey(tableName)) {
          return cachedTables.get(tableName).retrieve(id);
      }
      return Status.NOT_EXIST;
   }
}
