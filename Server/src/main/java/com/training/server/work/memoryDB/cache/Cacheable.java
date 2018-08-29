package com.training.server.work.memoryDB.cache;

import com.training.server.work.dao.Status;

public interface Cacheable <K, V>{

   void add (K name, V obj) ;

   V get (K name);

   /**
    * if we want to delete some record completely
    * we have to delete it everywhere it could exist.
    * @param name key with which the specified value is to be associated
    * @return status of the operation
    */
   Status delete (K name);


}
