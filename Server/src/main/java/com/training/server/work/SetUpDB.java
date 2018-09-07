package com.training.server.work;

import com.training.server.work.DB.repositories.*;
import com.training.server.work.DB.*;
import com.training.server.work.DB.cache.*;
import com.training.server.work.entity.*;

/**
 * Singelton Data Dealer
 *
 * This class made to set up the repositories
 * that the data will be saved in and queried from
 * also defining the size of them.
 *
 * Then providing the data dealer with the repositories
 * that he will manage the data retrieving and saving
 * operations among those repositories.
 */

public class SetUpDB {

   private static DataDealer dataDealer ;

   static {
      DiskRepository fileSystem = new DiskRepository();
      CacheRepository cache = new CacheRepository();



      LRU <String,User> cachedUsers = new LRU<> (100);
      LRU <String,Publication> cachedPublications = new LRU<> (100);
      LRU <String,License> cachedLicenses = new LRU<> (100);

      // split the cache repository into tables, to avoid chaotic saving.

      cache.addTable(Table.CONTENT.getTableName(), cachedPublications);
      cache.addTable(Table.LICENSE.getTableName(), cachedLicenses);
      cache.addTable(Table.USER.getTableName(), cachedUsers);

      // provide the dealer with the repositories it will deals with.

      dataDealer = new DataDealer(cache, fileSystem);
   }

   public static DataDealer getInstance () {
      return dataDealer;
   }

}
