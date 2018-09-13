package com.training.server.work.DB.cache;

import com.training.server.work.Status;

import java.util.*;

/**
 * This class implements
 * LRU (Least Recently Used) algorithm.
 * Different cacheable entries are saved in a LinkedHashMap,
 * whose order of iteration is the order
 * in which its entries were last accessed,
 * from least-recently accessed to most-recently,
 * This kind of map is well-suited to building LRU caches.
 * When map size exceed maxEntries,
 * the removeEldestEntry, which invoked by {@code put}, will return {@code true},
 * so it will delete least recently used entry
 * to make space for a most recently used entry.
 */

public class LRU extends LinkedHashMap <String,Object> implements Cacheable {

   private final int maxEntries;
   private static final int DEFAULT_INITIAL_CAPACITY = 30;
   private static final float DEFAULT_LOAD_FACTOR = 0.75f;

   /**
    * Constructs an empty {@code LinkedHashMap} instance
    * whose order of iteration is the order
    * in which its entries were last accessed, from least-recently accessed to
    * most-recently, with the
    * specified initial capacity, load factor and ordering mode, and
    * the max number of entries the map can hold.
    * @param maxEntries the max number of entries the map can hold
    */

   public LRU (int maxEntries) {

      super(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, true);

      if (maxEntries <= 0 )
         maxEntries = 10;

      this.maxEntries = maxEntries;
   }

   /**
    * adds an entry into the LinkedHashMap
    * if the entry is already exist then updated it
    * with the new value, and update its
    * access order, this happens internally by {@code put}
    * otherwise add new entry.
    *
    * @param name key name
    * @param obj value object
    * @throws InputMismatchException if one or both
    * of the parameters are null
    */

   @Override
   public synchronized void add(String name, Object obj) {

      if (name == null || obj == null)
         throw new InputMismatchException("nulls");

      this.put(name, obj);
   }

   /**
    * Retrieves the value to which the specified key is mapped,
    * or {@code NOT_EXIST} if this map
    * contains no mapping for the key.
    * {@code get} also puts the entry back to the end of the map
    * as most recently used
    * @param name key name
    * @return the value object to which the specified key name
    * is mapped, {@code NOT_EXIST} if map contain no mapping
    * for the specified key
    */

   @Override
   public synchronized Object retrieve(String name) {

      if (name == null)
         return Status.FAILED;

      if (!(this.containsKey(name)))
         return Status.NOT_EXIST;

      return this.get(name);
   }

   /**
    * removes an entry from the map, if map contain no mapping
    * for the specified key, returns {@code false},
    * otherwise removes it from the map
    * @param name key name
    * @return {@code true} if the entry successfully deleted,
    * {@code false} otherwise
    */

   @Override
   public synchronized boolean removeObj(String name) {

      if (!this.containsKey(name))
         return false;

      this.remove(name);

      return true;
   }

   /**
    * Returns {@code true} if this map size exceed the
    * maximum number of entries it can hold.
    * This method is invoked by {@code put} after
    * inserting a new entry into the map.
    * @param eldest the least recently accessed
    * entry.
    * @return Returns {@code true} if this map size exceed the
    * maximum number of entries it can hold,
    * {@code false} otherwise
    */

   @Override
   protected boolean removeEldestEntry(Map.Entry<String, Object> eldest) {
      return this.size() > maxEntries;
   }
}
