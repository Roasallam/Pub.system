package com.training.server.work.memoryDB;

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

   private Repository cache;
   private Repository disk;

   public DataDealer(Repository cache, Repository disk) {
      this.cache = cache;
      this.disk = disk;
   }

   public void saveData (String tableName, String id, Object object) {

   }

   public <T> T retrieveData (String tableName,String id) {
      return null;
   }

   public <T> T findById (String id) {
      return null;
   }

}
