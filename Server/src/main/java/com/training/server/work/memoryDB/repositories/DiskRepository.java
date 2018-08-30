package com.training.server.work.memoryDB.repositories;

import java.io.*;


public class DiskRepository implements Repository {

   DiskRepository (String rootFolder) {

      new File ("/Users/ruaasallam/GitHup/me/Server/" + rootFolder).mkdir();
      new File ("/Users/ruaasallam/GitHup/me/Server/" + rootFolder + "/License/records").mkdirs();
      new File ("/Users/ruaasallam/GitHup/me/Server/" + rootFolder + "/Publication/records").mkdirs();
      new File ("/Users/ruaasallam/GitHup/me/Server/" + rootFolder + "/Content/records").mkdirs();
      new File ("/Users/ruaasallam/GitHup/me/Server/" + rootFolder + "/User/records").mkdirs();

   }

   @Override
   public void add(String tableName, String id, Object obj) {

   }

   @Override
   public boolean remove(String tableName, String id) {
      return false;
   }

   @Override
   public Object get(String tableName, String id) {
      return null;
   }
}
