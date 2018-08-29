package com.training.server.work.dao;

import com.training.server.work.entity.Publication;
import java.util.List;

/**
 *  DATA ACCESS OBJECT FOR Publication
 */

public interface PublicationDAO {


   Publication findById(int publicationId);

   List<Publication>  findByJournalName(); // it could return a list of publications

   int createPublication(String journalName, String content);

   Status updatePublication(int publicationId, String newContent);

   Status deletePublication(int publicationId);

}
