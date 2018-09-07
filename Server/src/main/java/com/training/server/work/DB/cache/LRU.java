package com.training.server.work.DB.cache;

import com.training.server.work.Status;

import java.util.*;

/**
 * LRU (Least Recently Used) algorithm
 * This class was implemented as an in-memory storage
 * different entries are saved in a LinkedHashMap (Insertion order)
 * when map size exceed maxEntries, deleteLRU method
 * will delete least recently used entry
 * to make space for a most recently used entry.
 */
public class LRU implements Cacheable {

   private final int maxEntries;
   private static final int DEFAULT_INITIAL_CAPACITY = 50;
   private static final float DEFAULT_LOAD_FACTOR = 0.75f;
   private Map <String,Object> cachedEntries = new LinkedHashMap<>
      (DEFAULT_INITIAL_CAPACITY,DEFAULT_LOAD_FACTOR, false) ;


   public LRU (int maxEntries) {

      if (maxEntries <= 0 )
         maxEntries = 10;

      this.maxEntries = maxEntries;
   }

   @Override
   public synchronized void add(String name, Object obj) {

      if (name == null || obj == null)
         throw new InputMismatchException("nulls");

      if (cachedEntries.containsKey(name))
         update(name, obj);
      else
         addNew(name, obj);

   }

   private void update (String name, Object obj) {
      // no need to check for available space

     cachedEntries.remove(name);
     cachedEntries.put(name, obj);

   }

   private void addNew (String name, Object obj) {
      // check for available space

      if (isFull()) {
         Status status = deleteLRU();
         if (status == Status.FAILED)
            throw new RuntimeException("Failed to delete Least recently used");
      }

      cachedEntries.put(name, obj);
   }

   @Override
   public synchronized Object retrieve(String name) {

      if (name == null)
         return Status.NOT_EXIST;

      if (!(cachedEntries.containsKey(name)))
         return Status.NOT_EXIST;

      update(name, cachedEntries.get(name));
      return cachedEntries.get(name);
   }

   @Override
   public synchronized boolean removeObj(String name) {

      if (!cachedEntries.containsKey(name))
         return false;

      cachedEntries.remove(name);

      return true;
   }

   @Override
   public synchronized Status deleteLRU() {

      String name = getEldestKey();

      if (name == null)
         return Status.FAILED;

      cachedEntries.remove(name);

      return Status.MISSION_ACCOMPLISHED;
   }


   private boolean isFull () {
      return cachedEntries.size() >= maxEntries;
   }

   private String getEldestKey () {
      // LEAST RECENTLY USED ENTRY

      Map.Entry<String ,Object> eldest = cachedEntries.entrySet().iterator().next();

      return eldest.getKey();

   }
}
