package com.training.server.work.DB;

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
