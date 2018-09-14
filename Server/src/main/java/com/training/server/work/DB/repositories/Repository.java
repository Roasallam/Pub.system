package com.training.server.work.DB.repositories;

/**
 * defines the behavior
 * of a repository
 */

public interface Repository {

   void add (String name, String id, Object obj);

   boolean remove (String name, String id);

   Object get(String name, String id);

}
