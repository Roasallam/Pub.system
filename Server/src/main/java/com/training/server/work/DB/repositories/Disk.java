package com.training.server.work.DB.repositories;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

import com.training.server.work.Status;
import com.training.server.work.entity.*;

/**
 * A Repository which stores data based on xml format
 * using JAXBContext, Marshaller and Unmarshaller.
 * (Waiting,Synchronous IO) (FileOutputStream, FileInputStream..)
 * (Physical Storage)
 * each entity ,, User/Publication/License
 * are XML root elements
 * and each instance of them is stored to disk in XML files
 */

public class Disk implements Repository {

   private static JAXBContext jaxbContext;

   // To avoid the overhead involved in creating a JAXBContext instance
   static {
      try {

         jaxbContext = JAXBContext.newInstance(User.class,Publication.class,License.class);

      } catch (JAXBException e ) {
         e.printStackTrace();
      }
   }

   /**
    * adds an entry with the specified folder name
    * which it belongs to, using marshaller to save it in XML file
    * @param folderName the folder(table) that entry belongs to
    * @param id the id of the entry
    * @param obj value object of the entry
    */

   @Override
   public void add(String folderName, String id, Object obj) {


      File xmlFile = generateFile(folderName,id);

      try (FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
           BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {

         Marshaller marshaller = jaxbContext.createMarshaller();
         marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
         marshaller.marshal(obj, bufferedOutputStream);

      } catch (IOException|JAXBException e) {
         e.printStackTrace();
      }
   }

   /**
    * removes the file that the entry is saved in
    * @param folderName the folder which the entry belongs to
    * @param id the id of the entry
    * @return {@code true} if the file was found and successfully deleted
    * {@code false} otherwise
    */

   @Override
   public boolean remove(String folderName, String id) {

      File file = generateFile(folderName, id);

      return file.delete();
   }

   /**
    * gets the entry value objects using
    * unmarshaller, with the specified folder which
    * it belongs to
    * @param folderName the folder which the entry belongs to
    * @param id the id of the entry
    * @return the value object of that entry,
    * {@code NOT_EXIST} if its not exist
    */

   @Override
   public Object get(String folderName, String id) {

      File xmlFile = generateFile(folderName,id);

      if (!(xmlFile.exists()))
         return Status.NOT_EXIST;

      try (FileInputStream fileInputStream = new FileInputStream(xmlFile);
           BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)){

         Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
         return unmarshaller.unmarshal(bufferedInputStream);

      } catch (IOException|JAXBException e) {
         e.printStackTrace();
         return Status.NOT_EXIST;
      }
   }

   /**
    * generates a new file
    * with the specified uri
    * @param folderName folder which the file belongs to
    * @param id the id for the file
    * @return a new file instance with the specified uri
    */

   private File generateFile (String folderName, String id) {

      String uri = "DiskRepo" + File.separator +
         folderName + File.separator + "records" + File.separator + id + ".xml";
      return new File(uri);
   }
}
