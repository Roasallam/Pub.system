package com.training.server;

import com.training.server.serving.Listener;

/**
 * start server
 * this class was implemented to
 * run the server and start listening
 * for clients requests
 */

public class Start {

   public static void main(String[] args) {

      Listener.start();
   }
}
