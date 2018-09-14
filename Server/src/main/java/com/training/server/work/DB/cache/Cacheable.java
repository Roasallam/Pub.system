package com.training.server.work.DB.cache;

/**
 * defines the behavior of a
 * cacheable objects
 */

public interface Cacheable {

   void add (String name, Object obj) ;

   Object retrieve (String name);

   boolean removeObj (String name);

}
