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

   public DataDealer() {
      cache = new CacheRepository();
      disk = new DiskRepository();
   }

   public void saveData (String tableName, String id, Object object) {

   }

   public Object retrieveData (String tableName,String id) {

      Object obj = cache.get(tableName,id);

      if (obj == null) {
         obj = disk.get(tableName,id);

         if (obj != null) {
            cache.add(tableName, id, obj);
            return obj;
         }
      }
      return Status.NOT_EXIST;
   }

   public Object findById (String id) {
      return null;
   }

}
