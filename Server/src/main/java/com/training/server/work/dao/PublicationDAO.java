package com.training.server.work.dao;

import com.training.server.work.entity.Publication;
import java.util.List;

/**
 *  DATA ACCESS OBJECT FOR Publication
 */

public interface PublicationDAO {

   void findAll();
   Publication findById();
   void  findByJournalName(); // it could return a list of publications
   Publication createPublication(Publication publication);
   Publication updatePublication(Publication publication);
   Publication deletePublication(Publication publication);

}
