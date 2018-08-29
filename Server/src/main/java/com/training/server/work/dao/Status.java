package com.training.server.work.dao;


/**
 * Like an Optional class
 * EFFECTIVE JAVA, ITEM 55
 * this class was implemented to help DAO's
 * Not returning the values in case they were NULL
 * Instead return a status of the operation
 */
public enum Status {

   ERROR , NOT_EXIST , MISSION_ACCOMPLISHED;
}
