package it.mate.quilook.server.services.impl;

import it.mate.quilook.server.services.QuAdapter;

import org.springframework.stereotype.Service;

@Service
public class QuAdapterImpl implements QuAdapter {

  @Override
  public String getQuMessage() {
    return "Hello Qu World!";
  }
  
}
