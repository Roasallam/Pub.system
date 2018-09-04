package com.training.server.work.memoryDB.cache;

import com.training.server.work.dao.Status;

public interface Cacheable {

   void add (String name, Object obj) ;

   Object retrieve (String name);

   Status deleteLRU ();

   boolean removeObj (String name);


}
