package com.training.server.work.DB.cache;

import com.training.server.work.Status;

/**
 * defines the operations applied
 * to a cacheable entries.
 */

public interface Cacheable {

   void add (String name, Object obj) ;

   Object retrieve (String name);

   Status deleteLRU ();

   boolean removeObj (String name);

}
