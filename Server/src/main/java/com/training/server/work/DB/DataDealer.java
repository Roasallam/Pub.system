package com.training.server.work.DB;

import com.training.server.work.Status;
import com.training.server.work.DB.repositories.Repository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A Dealer that deals data with
 * many repositories
 * 1- disk
 * 2- cache
 *
 * Saving and setting data:
 * 1) On disk, (Non-Volatile),(data is saved in XML format using FileInput & FileOutput
 * and the help of JAXBContext), saving and removing operations were executed
 * in another thread, (Slow) (Waiting,Synchronous IO) (Physical Storage)
 * i.e: In synchronous file I/O, a thread starts an I/O operation
 * and immediately enters a wait state until the I/O request has completed.
 *
 * 2) In cache (Logical storage)
 *
 * Reading data:
 * 1st from cache (faster to get data from + it stores most recently used),
 * if not found >>
 * 2nd from disk , if not found , then it doesn't exist at all.
 */

public class DataDealer {

   private Repository cache ;
   private Repository disk ;
   private final ExecutorService pool;

   /**
    * constructs a new dataDealer instance and
    * initiate the repositories to deals with
    * also a fixed thread pool with #20 thread
    * to run disk operations
    * @param cache cache repository
    * @param disk disk repository
    */

   public DataDealer(Repository cache, Repository disk) {
      this.cache = cache;
      this.disk = disk;
      pool = Executors.newFixedThreadPool(20);
   }

   /**
    * saves data to cache repository
    * and starts a new thread for saving in disk
    * @param tableName the table which the entry belongs to
    * @param id the id if that entry
    * @param object value object
    */

   public void saveData (String tableName, String id, Object object) {

      cache.add(tableName,id,object);

      pool.execute(() -> disk.add(tableName, id, object));

   }

   /**
    * retrieves the value object from cache first
    * if found then return it, (cache hit)
    * if not (cache miss) see if its in disk, if so then
    * add to cache as a most recently used and return it
    * otherwise its not exist
    * @param tableName table which the entry belongs to
    * @param id the id of that entry
    * @return the object if found, NOT_EXIST if not
    */

   public Object retrieveData (String tableName,String id) {

      Object obj = cache.get(tableName, id);

      // cache HIT
      if (obj != Status.NOT_EXIST)
         return obj;

      // cache MISS
      else {

         obj = disk.get(tableName, id);

         if (obj !=  Status.NOT_EXIST) {

            // make most recently used
            cache.add(tableName, id, obj);
            return obj;
         }
         return Status.NOT_EXIST;
      }
   }

   /**
    * deletes a specified entry from cache and starts a new thread
    * to delete it from disk
    * @param tableName table that the entry belongs to
    * @param id the id of that entry
    * @return the status of the operation
    */

   public Status deleteData (String tableName, String id) {

      cache.remove(tableName, id);

      pool.execute(() -> disk.remove(tableName, id));

      return Status.DELETED;
   }
}
