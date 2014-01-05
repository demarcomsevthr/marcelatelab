package it.mate.stickmail.server.services;

import it.mate.stickmail.shared.model.StickMail;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@Service
public class StickAdapter {
  
  private MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
  
  private final static String KEY_MAILS = "mails";
  
  @SuppressWarnings("unchecked")
  public void postNewMail(StickMail mail) throws AdapterException {
    List<StickMail> mails = (List<StickMail>)memcache.get(KEY_MAILS);
    if (mails == null) {
      mails = new ArrayList<StickMail>();
      memcache.put(KEY_MAILS, mails);
    }
    mails.add(mail);
  }

}
