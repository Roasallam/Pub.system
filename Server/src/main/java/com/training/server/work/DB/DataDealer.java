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
 * 2) In cache (Volatile) (Logical storage)
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

   public DataDealer(Repository cache, Repository disk) {
      this.cache = cache;
      this.disk = disk;
      pool = Executors.newFixedThreadPool(10);
   }


   public void saveData (String tableName, String id, Object object) {

      cache.add(tableName,id,object);

      pool.execute(() -> disk.add(tableName, id, object));

   }

   public Object retrieveData (String tableName,String id) {

      // this could contain a (Status or License or User or Publication) object

      Object [] containType = {null};
      containType[0] = cache.get(tableName, id);

      // cache HIT
      if (containType[0] != Status.NOT_EXIST)
         return containType[0];

      // cache MISS
      else {

         containType[0] = disk.get(tableName, id);

         if (containType[0] !=  Status.NOT_EXIST) {

            // make recently used
            cache.add(tableName, id, containType[0]);
            return containType[0];
         }
         return Status.NOT_EXIST;
      }
   }

   public Status deleteData (String tableName, String id) {

      cache.remove(tableName, id);

      pool.execute(() -> disk.remove(tableName, id));

      return Status.DELETED;

   }


}
