package com.training.server.work.memoryDB.repositories;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

import com.training.server.work.dao.Status;
import com.training.server.work.entity.*;


public class DiskRepository implements Repository {

   private static JAXBContext jaxbContext;

   /**
    * why in Static block? JAXBContext>>
    * To avoid the overhead involved in creating a JAXBContext instance
    *   and it is thread safe.
    * while Marshallers and Unmarshallers are cheap and not thread safe,
    * so created new of those for each operation.
    */
   static {

      try {

         jaxbContext = JAXBContext.newInstance(User.class,Publication.class,License.class);

      } catch (JAXBException e ) {
         e.printStackTrace();
      }
   }

   @Override
   public void add(String folderName, String id, Object obj) {


      File xmlFile = generateFile(folderName,id);

      try (FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
           BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {

         Marshaller marshaller = jaxbContext.createMarshaller();
         marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
         marshaller.marshal(obj, bufferedOutputStream);

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   @Override
   public boolean remove(String folderName, String id) {

      File file = generateFile(folderName, id);

      return file.delete();
   }

   @Override
   public Object get(String folderName, String id) {

      File xmlFile = generateFile(folderName,id);

      if (!(xmlFile.exists()))
         return Status.NOT_EXIST;

      try (FileInputStream fileInputStream = new FileInputStream(xmlFile);
           BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)){

         Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
         return unmarshaller.unmarshal(bufferedInputStream);

      } catch (Exception e ) {
         e.printStackTrace();
         return null;
      }
   }

   private File generateFile (String folderName, String id) {

      String uri = "DiskRepo" + File.separator +
         folderName + File.separator + "records" + File.separator + id + ".xml";
      return new File(uri);
   }


}
