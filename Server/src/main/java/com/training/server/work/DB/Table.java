package com.training.server.work.DB;

/**
 * each entity represents a table
 * each instance of User belongs to user table/ folder
 * each instance of Publication belongs to Publication table/ folder
 * each instance of License belongs to License table/ folder
 */

public enum Table {
   CONTENT("Content"), LICENSE ("License"), USER ("User");

   private String tableName;

   Table (String tableName) {
      this.tableName = tableName;
   }

   public String getTableName() {
      return tableName;
   }
}
