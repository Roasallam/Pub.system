package com.training.server.work;

import com.training.server.work.memoryDB.repositories.*;
import com.training.server.work.memoryDB.*;
import com.training.server.work.memoryDB.cache.*;
import com.training.server.work.entity.*;

/**
 * Singelton
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

      cache.addTable(Table.PUBLICATION.getTableName(), cachedPublications);
      cache.addTable(Table.LICENSE.getTableName(), cachedLicenses);
      cache.addTable(Table.USER.getTableName(), cachedUsers);

      dataDealer = new DataDealer(cache, fileSystem);
   }

   public static DataDealer getInstance () {
      return dataDealer;
   }

}
