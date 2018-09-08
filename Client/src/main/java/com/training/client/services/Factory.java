package com.training.client.services;

public abstract class Factory {

   public abstract Service provideService (Services request, String data);
}
