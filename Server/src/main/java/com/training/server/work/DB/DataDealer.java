package com.training.server.work.DB;

import com.training.server.work.Status;
import com.training.server.work.DB.repositories.Repository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A Dealer that deals data with
 * 2 defined repositories
 * 1st repository should be faster than the 2nd one
 */

public class DataDealer {

   private Repository repo1 ;
   private Repository repo2 ;
   private final ExecutorService pool;

   /**
    * constructs a new dataDealer instance and
    * initiate the repositories to deals with,
    * also a fixed thread pool with #20 thread
    * to run 2nd repository operations
    * @param repo1 1st repository
    * @param repo2 2nd repository
    */

   public DataDealer(Repository repo1, Repository repo2) {
      this.repo1 = repo1;
      this.repo2 = repo2;
      pool = Executors.newFixedThreadPool(20);
   }

   /**
    * saves data to first repository
    * and starts a new thread for saving in 2nd repository
    * @param tableName the table which the entry belongs to
    * @param id the id if that entry
    * @param object value object
    */

   public void saveData (String tableName, String id, Object object) {

      repo1.add(tableName,id,object);

      pool.execute(() -> repo2.add(tableName, id, object));

   }

   /**
    * retrieves the value object from 1st repository first
    * if found then return it, (hit)
    * if not (miss) see if its in 2nd repository, if so then
    * add to 1st repository as a most recently used and return it
    * otherwise its not exist
    * @param tableName table which the entry belongs to
    * @param id the id of that entry
    * @return the object if found,
    * {@code NOT_EXIST} if not found
    */

   public Object retrieveData (String tableName,String id) {

      Object obj = repo1.get(tableName, id);

      //  HIT
      if (obj != Status.NOT_EXIST)
         return obj;

      //  MISS
      else {

         obj = repo2.get(tableName, id);

         if (obj !=  Status.NOT_EXIST) {

            // make most recently used
            repo1.add(tableName, id, obj);
            return obj;
         }
         return Status.NOT_EXIST;
      }
   }

   /**
    * deletes a specified entry from first repository and starts a new thread
    * to delete it from 2nd one
    * @param tableName table that the entry belongs to
    * @param id the id of that entry
    * @return the status of the operation
    */

   public Status deleteData (String tableName, String id) {

      repo1.remove(tableName, id);

      pool.execute(() -> repo2.remove(tableName, id));

      return Status.DELETED;
   }
}
