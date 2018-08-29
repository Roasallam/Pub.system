package com.training.server.work.dao;

import com.training.server.work.entity.User;
import com.training.server.work.entity.UserType;

/**
 * DATA ACCESS OBJECT FOR User
 */

public interface UserDAO {

   User findByName(String userName);

   Status RegisterUser(String userName, String password, UserType userType);

   Status updateUserPassword(String userName, String newPassword);

   Status deleteUser(String userName);
}
