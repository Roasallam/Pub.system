package com.training.server.work.DB.cache;

import com.training.server.work.Status;

public interface Cacheable <K,V> {

   void add (String name, Object obj) ;

   Object retrieve (String name);

   Status deleteLRU ();

   boolean removeObj (String name);


}
