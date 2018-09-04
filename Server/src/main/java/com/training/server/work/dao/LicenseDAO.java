package com.training.server.work.dao;

import com.training.server.work.Status;
import com.training.server.work.entity.License;
import com.training.server.work.entity.*;


/**
 * DATA ACCESS OBJECT FOR License
 */
public interface LicenseDAO {

   License findByUserName(String userName);

   Status updateTimeLicense(String userName, TimeLicense newTimeLicense);

   Status updatePrivilegesLicense(String userName, PrivilegesLicense newPrivilegesLicense);

   Status updateSlice (String userName, String newSlice);


}
