package com.training.server.work.dao;

import com.training.server.work.entity.License;


/**
 * DATA ACCESS OBJECT FOR License
 */

public interface LicenseDAO {


   License findByUserName(String userName);

   License createLicense(License license);

   License updateLicense(License license);

   License deleteLicense(License license);
}
