package com.training.server.work;

import com.training.server.work.DB.repositories.*;
import com.training.server.work.DB.*;
import com.training.server.work.DB.cache.*;

/**
 * This class made to set up the repositories
 * that the data will be saved in and queried from
 * also defining the size of them.(for cache)
 *
 * Then providing the data dealer with the repositories
 * that he will manage the data retrieving and saving
 * operations among those repositories.
 */

public class Database {

   private static DataDealer dataDealer ;

   // setting up the DB

   static {
      Repository fileSystem = new Disk();
      Cache cache = new Cache();

      Cacheable cachedUsers = new LRU (100);
      Cacheable cachedPublications = new LRU (100);
      Cacheable cachedLicenses = new LRU (100);

      // split the cache repository into tables, to avoid chaotic saving.

      cache.addTable(Table.CONTENT.getTableName(), cachedPublications);
      cache.addTable(Table.LICENSE.getTableName(), cachedLicenses);
      cache.addTable(Table.USER.getTableName(), cachedUsers);

      // provide the dealer with the repositories it will deals with.

      dataDealer = new DataDealer(cache, fileSystem);
   }


   /**
    * Singleton Data Dealer for this database
    * @return DataDealer instance
    */

   public static DataDealer getInstance () {
      return dataDealer;
   }

}
