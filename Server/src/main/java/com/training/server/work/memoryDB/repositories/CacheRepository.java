package com.training.server.work.memoryDB.repositories;

import com.training.server.work.dao.Status;
import com.training.server.work.memoryDB.cache.*;

import java.util.*;

/**
 * A Repository that contains tables to be cached
 */

public class CacheRepository implements Repository {

   // String is for the table name
   // Cacheable is a cacheable object/record in that table
   // we can add or get or remove records using that cacheable object
   private Map<String, Cacheable<String,Object>> cachedTables = new HashMap<>();

   public void addTable (String tableName, Cacheable cacheable) {

      cachedTables.put(tableName,cacheable);
   }

   @Override
   public void add(String tableName, String id, Object obj) {

      // only cache records that their tables are in cache
      if (cachedTables.containsKey(tableName))
         cachedTables.get(tableName).add(id, obj);
   }

   @Override
   public boolean remove(String tableName, String id) {

      if (cachedTables.containsKey(tableName)) {

         Status status = (cachedTables.get(tableName).delete(id));
         if (status.getStatusId() == 1)
            return true;
      }
      return false;
   }

   @Override
   public Object get(String tableName, String id) {

      if (cachedTables.containsKey(tableName)) {
          return cachedTables.get(tableName).get(id);
      }
      return null;
   }
}
