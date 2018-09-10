package com.training.server.work.dao;

import com.training.server.work.Status;
import com.training.server.work.entity.Publication;
import java.util.List;

/**
 *  DATA ACCESS OBJECT for Publication.
 */

public interface PublicationDAO {


   Publication findById(String publicationId);

   String createPublication(String journalName, String content);

   Status updatePublication(String publicationId, String newContent);

   Status deletePublication(String publicationId);

}
