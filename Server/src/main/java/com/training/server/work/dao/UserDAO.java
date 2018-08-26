package com.training.server.work.dao;

import com.training.server.work.entity.User;


/**
 * DATA ACCESS OBJECT FOR User
 */

public interface UserDAO {

   User findByName(String userName);

   User RegisterUser(User user);

   User updateUserPassword(User user);

   User deleteUser(User user);
}
