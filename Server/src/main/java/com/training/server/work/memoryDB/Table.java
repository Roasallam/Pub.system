package com.training.server.work.memoryDB;

public enum Table {
   PUBLICATION("Publication"), LICENSE ("License"), USER ("User"), CONTENT ("Content");

   private String tableName;

   Table (String tableName) {
      this.tableName = tableName;
   }

   public String getTableName() {
      return tableName;
   }

   public void setTableName(String tableName) {
      this.tableName = tableName;
   }
}
