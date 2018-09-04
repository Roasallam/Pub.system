package com.training.server.work;

import com.training.server.work.dao.Status;

import java.io.File;
import java.nio.file.*;

//Singelton

// have to change this to RandomStringGenerator

/**
 * used to generate a unique id
 * for each specific instance of a model
 * such as publicationId
 */
public enum IdGenerator {
   ID_GENERATOR;


   // why implemented by hand,, didn't use a UUID?
   // easiest  to handle an int value than UUID type?
   // easiest searching? ..
   public static int generateId (String foldername) {

      String uri = "DiskRepo" + File.separator + foldername + File.separator + "records" ;
      Path source = Paths.get(uri);

      try {
         DirectoryStream <Path> registeredIds = Files.newDirectoryStream(source);
         int id = 0 ;
         for (Path path : registeredIds) {
            String temp = path.getFileName().toString().replace(".xml","");
            id = Math.max(id,Integer.parseInt(temp));
         }
         return id;

      } catch (Exception e ) {
         e.printStackTrace();
         return Status.ERROR.getStatusId();
      }
   }


}
