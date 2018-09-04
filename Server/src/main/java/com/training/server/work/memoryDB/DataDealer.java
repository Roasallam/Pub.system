package com.training.server.work.memoryDB;

import com.training.server.work.Status;
import com.training.server.work.memoryDB.repositories.CacheRepository;
import com.training.server.work.memoryDB.repositories.DiskRepository;
import com.training.server.work.memoryDB.repositories.Repository;

/**
 * A Dealer that deals data with
 * many repositories
 * 1- disk
 * 2- cache
 *
 * Saving and setting data:
 * 1st on disk, (Non-Volatile)
 * 2nd in cache (Volatile)
 *
 * Reading data:
 * 1st from cache (faster to get data from), if not found
 * 2nd from disk , if not found , then it doesn't exist at all.
 */

public class DataDealer {

   private Repository cache ;
   private Repository disk ;

   public DataDealer(Repository cache, Repository disk) {
      this.cache = cache;
      this.disk = disk;
   }

   public void saveData (String tableName, String id, Object object) {

      cache.add(tableName,id,object);

      // adding to the disk in other thread,

   }

   public Object retrieveData (String tableName,String id) {

      Object [] containType = {null};
      containType[0] = cache.get(tableName, id);

      // cache HIT
      if (containType[0] != Status.NOT_EXIST)
         return containType[0];

      // cache MISS
      else {

         containType[0] = disk.get(tableName, id);

         if (containType[0] !=  Status.NOT_EXIST) {

            cache.add(tableName, id, containType[0]);
            return containType[0];
         }

         return Status.NOT_EXIST;
      }
   }

   public Status deleteData (String tableName, String id) {

      // don't change from status to boolean please

      boolean status = cache.remove(tableName, id);

      // removing from disk in other thread

      return Status.MISSION_ACCOMPLISHED;

   }


}
