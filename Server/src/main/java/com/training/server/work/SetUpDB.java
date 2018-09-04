package com.training.server.work;

import com.training.server.work.memoryDB.repositories.*;
import com.training.server.work.memoryDB.*;
import com.training.server.work.memoryDB.cache.*;
import com.training.server.work.entity.*;

//singelton

public class SetUpDB {

   private static DataDealer dataDealer ;

   static {
      DiskRepository fileSystem = new DiskRepository();
      CacheRepository cache = new CacheRepository();

      LRU <String,User> cachedUsers = new LRU<> (100);
      LRU <String,Publication> cachedPublications = new LRU<> (100);
      LRU <String,License> cachedLicenses = new LRU<> (100);

      cache.addTable(Table.PUBLICATION.getTableName(), cachedPublications);
      cache.addTable(Table.LICENSE.getTableName(),cachedLicenses);
      cache.addTable(Table.USER.getTableName(),cachedUsers);

      dataDealer = new DataDealer(cache, fileSystem);
   }

   public static DataDealer getInstance () {
      return dataDealer;
   }

}
