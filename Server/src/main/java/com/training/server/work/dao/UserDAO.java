package com.training.server.work.dao;

import com.training.server.work.Status;
import com.training.server.work.entity.User;
import com.training.server.work.entity.UserType;

/**
 * DATA ACCESS OBJECT for User.
 */

public interface UserDAO {

   User findByName(String userName);

   Status SignUp(String userName, String password, UserType userType);

   Status updatePassword(String userName, String newPassword);

   Status deleteUser(String userName);
}
