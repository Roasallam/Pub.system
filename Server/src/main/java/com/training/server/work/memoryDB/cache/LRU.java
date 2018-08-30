package com.training.server.work.memoryDB.cache;

import com.training.server.work.dao.Status;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;

/**
 * Implementing Least Recently Used algorithm
 * using a HashMap to save values
 * and a queue to keep the most recently used in front
 * and the least recently used in back
 * @param <K> key with which the specified value is to be associated
 * @param <V> cached value to be associated with the specified key
 */
public class LRU<K, V> implements Cacheable<K, V>{

   // Node represents a (User/ Publication/License ) object
   // K is like a userName or a PublicationId
   // V is like the User object or Publication object...
   private Deque<Node<K,V>> cache = new ConcurrentLinkedDeque<>();

   private ConcurrentMap<K, Node<K,V>> data = new ConcurrentHashMap<>();


   // capacity of the cache
   private final int maxSize;

   private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
   private Lock readLock = readWriteLock.readLock();
   private Lock writeLock = readWriteLock.writeLock();

   public LRU(int maxSize) {

      if (maxSize <= 0 )
         throw new InputMismatchException("Minimum capacity should be 1");
      this.maxSize = maxSize;
   }

   @Override
   public void add(K name, V obj) {

      if (name != null && obj != null) {

         writeLock.lock();

         try {

            Node<K, V> toAdd = new Node<>(name, obj);

            // had to check size first, ((cause putIfAbsent may add a new node..))

            // Checking if there is an available space
            if (data.size() < maxSize) {

               // if there is an value associated with the specified key,
               // >> then returns that value
               // if not >> then puts the value, and returns null.
               if (data.putIfAbsent(name, toAdd) == null) {

                  cache.addFirst(toAdd);
               } else {

                  // update the existing value
                  updateExistingValue(name, obj);
               }
               // Making some room space for the new node.
            } else {

               // if the value associated with the specified key already exist
               // just update the value , no need for new room space,
               // if not >> then puts the new value and returns null.
               if (data.putIfAbsent(name, toAdd) != null)

                  updateExistingValue(name, obj);

               // value is not exist, so make a new room space
               else {

                  // remove the LAST RECENTLY USED VALUE .
                  Node<K, V> toRemove = cache.removeLast();
                  data.remove(toRemove.index);

                  cache.addFirst(toAdd);
               }
            }
         } catch (Exception e ) {
            e.printStackTrace();
         } finally {

            writeLock.unlock();
         }
      }
   }

   private void updateExistingValue (K name, V newObj) {

      Node <K,V> node = data.get(name);
      cache.remove(node);

      Node <K,V> toAdd = new Node<>(name,newObj);
      data.put(name, toAdd);
      cache.addFirst(toAdd);
   }


   @Override
   public V get(K name) {

      if (name == null)
         return null;

      readLock.lock();

      try {

         Node<K, V> node = data.get(name);

         // data is not in cache ,, get it from somewhere else..
         // cache MISS..
         if (node == null)
            return null;

         // if  map contains a mapping from a key name to a value V
         // data is in cache ,, so just bring it to the front of the queue
         // cache HIT..
         else {

            cache.remove(node);
            cache.addFirst(node);

            return node.value;
         }

      } catch (Exception e ) {
         e.printStackTrace();
      } finally {

         readLock.unlock();
      }
      // cache MISS..
      return null;
   }

   @Override
   public Status delete(K name) {

      try {

         Thread.sleep(2000);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

      writeLock.lock();
      try {

         Node<K, V> node = data.get(name);
         // checking if the data exist in cache
         if (node != null) {

            cache.remove(node);
            data.remove(name);
            return Status.MISSION_ACCOMPLISHED;
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {

         writeLock.unlock();
      }
      return Status.NOT_EXIST;
   }

   private static class Node <K, V> {

      K index;
      V value;

      Node(K index, V value) {
         this.index = index;
         this.value = value;
      }
   }
}
