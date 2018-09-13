package com.training.server.work.DB.repositories;

import com.training.server.work.Status;
import com.training.server.work.DB.cache.*;

import java.util.*;

/**
 * A Repository that saves tables records to be cached
 * It's separated into tables, each table
 * is mapped to its key table name
 */

public class Cache implements Repository {

   private Map<String, Cacheable> cachedTables = new HashMap<>();

   /**
    * adds a new table to the HashMap
    * @param tableName the specified table name
    * @param cacheable the cacheable entries to stored
    *                  in that table
    */

   public void addTable (String tableName, Cacheable cacheable) {

      cachedTables.put(tableName,cacheable);
   }

   /**
    * adds a new value object which belongs to
    * a specified table
    * @param tableName the table which the entry belongs to
    * @param id the id of the cacheable entry
    * @param obj the value object of that cacheable entry
    */

   @Override
   public void add(String tableName, String id, Object obj) {

      if (cachedTables.containsKey(tableName))
         cachedTables.get(tableName).add(id, obj);
   }

   /**
    * removes a specified cacheable entry from the table
    * it belongs to
    * @param tableName table that entry belongs to
    * @param id the id of that cacheable entry
    * @return {@code true} if the entry was deleted, {@code false}
    * otherwise
    */

   @Override
   public boolean remove(String tableName, String id) {

      if (cachedTables.containsKey(tableName)) {

         return (cachedTables.get(tableName).removeObj(id));
      }
      return false;
   }

   /**
    * gets a value object which it belongs to
    *  the specified table
    * @param tableName table which the entry is in
    * @param id the id of that cacheable entry
    * @return the value object if its exist, otherwise
    * returns {@code NOT_EXIST}
    */

   @Override
   public Object get(String tableName, String id) {

      if (cachedTables.containsKey(tableName)) {
          return cachedTables.get(tableName).retrieve(id);
      }
      return Status.NOT_EXIST;
   }
}
