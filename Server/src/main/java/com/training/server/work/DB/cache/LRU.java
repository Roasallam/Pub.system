package com.training.server.work.DB.cache;

import com.training.server.work.Status;

import java.util.*;

/**
 * This class implements
 * LRU (Least Recently Used) algorithm.
 * Different cacheable entries are saved in a LinkedHashMap (Insertion order).
 * When map size exceed maxEntries, deleteLRU method
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

   /**
    * adds an entry into the LinkedHashMap
    * if the entry is already exist then updated it
    * with the new value, otherwise add new entry.
    * @param name key name
    * @param obj value object
    */

   @Override
   public synchronized void add(String name, Object obj) {

      if (name == null || obj == null)
         throw new InputMismatchException("nulls");

      if (cachedEntries.containsKey(name))
         update(name, obj);
      else
         addNew(name, obj);
   }

   /**
    * updates an exiting entry with the new value.
    * no need to check for available space.
    * @param name key name
    * @param obj new value object
    */

   private void update (String name, Object obj) {

     cachedEntries.remove(name);
     cachedEntries.put(name, obj);
   }

   /**
    * adds a new entry.
    * check for available space first,
    * if HashMap is full, then remove the LRU entry,
    * if failed removing the LRU entry, throw runtime exception,
    * now add the new entry.
    * @param name key name
    * @param obj value object
    */

   private void addNew (String name, Object obj) {

      if (isFull()) {
         Status status = deleteLRU();
         if (status == Status.FAILED)
            throw new RuntimeException("Failed to delete Least recently used");
      }
      cachedEntries.put(name, obj);
   }

   /**
    * retrieves an entry object, if the HashMap
    * does not contain it, then the entry is not exist,
    * otherwise update its position, Most Recently Used.
    * @param name key name
    * @return the value object to which the specified key name
    * is mapped
    */

   @Override
   public synchronized Object retrieve(String name) {

      if (name == null)
         return Status.NOT_EXIST;

      if (!(cachedEntries.containsKey(name)))
         return Status.NOT_EXIST;

      update(name, cachedEntries.get(name));
      return cachedEntries.get(name);
   }

   /**
    * removes an entry from the HashMap, if it
    * does not contain the entry, returns false,
    * otherwise removes it from the HashMap
    * @param name key name
    * @return {@code true} if the entry successfully deleted, {@code false} otherwise
    */

   @Override
   public synchronized boolean removeObj(String name) {

      if (!cachedEntries.containsKey(name))
         return false;

      cachedEntries.remove(name);

      return true;
   }

   /**
    * deletes the least recently used entry when
    * the HashMap is full
    * @return FAILED if the LRU entry was not deleted,
    * otherwise returns MISSION_ACCOMPLISHED
    */

   @Override
   public synchronized Status deleteLRU() {

      String name = getEldestKey();

      if (name == null)
         return Status.FAILED;

      cachedEntries.remove(name);

      return Status.MISSION_ACCOMPLISHED;
   }

   /**
    * checks if the HashMap size has exceeded
    * maxEntries
    * @return {@code true} if the HashMap is full, {@code false} otherwise
    */

   private boolean isFull () {
      return cachedEntries.size() >= maxEntries;
   }

   /**
    * gets the key corresponding to the LRU entry
    * @return the key name of the LRU entry
    */

   private String getEldestKey () {

      Map.Entry<String ,Object> eldest = cachedEntries.entrySet().iterator().next();

      return eldest.getKey();
   }
}
