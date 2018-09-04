package com.training.server.work.memoryDB;

import com.training.server.work.dao.Status;
import com.training.server.work.memoryDB.repositories.CacheRepository;
import com.training.server.work.memoryDB.repositories.DiskRepository;
import com.training.server.work.memoryDB.repositories.Repository;

/**
 * A Dealer that deals data with
 * many repositories
 * 1- disk
 * 2- cache
 *
 * saving and setting data
 * 1st on disk
 * 2nd in cache
 *
 * reading data
 * 1st from memory , if not found
 * 2nd from cache , if not found
 * 3rd from disk , if not found , then doesn't exist at all
 */

public class DataDealer {

   private Repository cache ;
   private Repository disk ;

   public DataDealer(Repository cache, Repository disk) {
      this.cache = new CacheRepository();
      this.disk = new DiskRepository();
   }

   public void saveData (String tableName, String id, Object object) {

      cache.add(tableName,id,object);

      // adding to the disk in other thread,

   }

   public Object retrieveData (String tableName,String id) {

      // cache HIT
      if (cache.get(tableName,id) != Status.NOT_EXIST)
         return cache.get(tableName, id);

      // cache MISS
      else if (disk.get(tableName, id) !=  Status.NOT_EXIST) {

         cache.add(tableName, id, disk.get(tableName, id));
         return cache.get(tableName, id);

      } else
         return Status.NOT_EXIST;

   }

   public Status deleteData (String tableName, String id) {

      boolean status = cache.remove(tableName, id);

      // removing from disk in other thread

      return Status.MISSION_ACCOMPLISHED;

   }


}
