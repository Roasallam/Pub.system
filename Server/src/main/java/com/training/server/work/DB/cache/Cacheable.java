package com.training.server.work.DB.cache;

/**
 * defines the operations applied
 * to a cacheable entries.
 */

public interface Cacheable {

   void add (String name, Object obj) ;

   Object retrieve (String name);

   boolean removeObj (String name);

}
