package it.mate.copymob.server.services;

import it.mate.commons.server.dao.Dao;
import it.mate.commons.server.dao.ParameterDefinition;
import it.mate.commons.server.utils.CloneUtils;
import it.mate.commons.server.utils.LoggingUtils;
import it.mate.copymob.server.model.AccountDs;
import it.mate.copymob.server.model.DevInfoDs;
import it.mate.copymob.shared.model.Account;
import it.mate.copymob.shared.model.Timbro;
import it.mate.copymob.shared.model.impl.AccountTx;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.Base64Utils;

@Service
public class RemoteAdapterImpl implements RemoteAdapter {
  
  private static Logger logger = Logger.getLogger(RemoteFacadeImpl.class);
  
  @Autowired private Dao dao;
  
  @PostConstruct
  public void postConstruct() {
    logger.debug("initialized " + this);
    if (dao == null) {
      logger.error("error", new InstantiationException("dao is null!"));
    }
  }

  @Override
  public void scheduledChecks() {

  }

  @Override
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion, String devIp) {
    DevInfoDs ds = null;
    if (devUuid != null) {
      ds = dao.findSingle(DevInfoDs.class, "devUuid == devUuidParam" ,
          Dao.Utils.buildParameters(new ParameterDefinition[] {
              new ParameterDefinition(String.class, "devUuidParam")
          }) , null, devUuid);
    }
    if (ds == null) {
      ds = new DevInfoDs();
      ds.setOs(os);
      ds.setLayout(layout);
      ds.setDevName(devName);
      ds.setPhgVersion(phgVersion);
      ds.setPlatform(platform);
      ds.setDevUuid(devUuid);
      ds.setDevVersion(devVersion);
      ds.setCreated(new Date());
      ds.setDevIp(devIp);
      LoggingUtils.debug(getClass(), "creating " + ds);
      ds = dao.create(ds);
    }
    if (ds != null) {
      return ds.getId();
    } else {
      return null;
    }
  }

  @Override
  public Account createAccount(Account entity) {
    AccountDs ds = null;
    // controllo se per errore arrivano due create dallo stesso device
    if (entity.getDevInfoId() != null) {
      Key devInfoKey = KeyFactory.stringToKey(entity.getDevInfoId());
      ds = dao.findSingle(AccountDs.class, "devInfoId == devInfoIdParam", 
          Dao.Utils.buildParameters(new ParameterDefinition[] {
              new ParameterDefinition(Key.class, "devInfoIdParam")
          }), 
        null, devInfoKey );
      if (ds != null) {
        ds.setName(entity.getName());
        ds.setEmail(entity.getEmail());
        ds = dao.update(ds);
      }
    }
    if (ds == null) {
      ds = CloneUtils.clone(entity, AccountDs.class);
      ds = dao.create(ds);
      LoggingUtils.debug(getClass(), "created account " + ds);
    }
    return CloneUtils.clone (ds, AccountTx.class);
  }

  @Override
  public Account updateAccount(Account entity) {
    AccountDs ds = CloneUtils.clone(entity, AccountDs.class);
    ds = dao.update(ds);
    return CloneUtils.clone (ds, AccountTx.class);
  }
  
  
  public List<Timbro> getTimbri() throws Exception {
    List<Timbro> timbri = AdapterUtil.getInitAdapterBean().getTimbri();
    
    for (Timbro timbro : timbri) {
      LoggingUtils.debug(getClass(), "found " + timbro);
      String imageFileName = String.format("META-INF/setup-data/images/%s.jpg", timbro.getCodice() );
      File imageFile = getResourceFileAllowNull(imageFileName);
      LoggingUtils.debug(getClass(), "found file " + imageFile.getAbsolutePath());
      String imageText = inputStreamToBase64(new FileInputStream(imageFile));
      LoggingUtils.debug(getClass(), "content = " + imageText.substring(0, 200));
      timbro.setImage(imageText);
    }
    
    for (int ita = 0; ita < nonalfanum.length; ita++) {
      if (nonalfanum[ita] != 0) {
        LoggingUtils.debug(RestController.class, "FOUND NON ALFANUM CAR " + nonalfanum[ita]);
      }
    }
    
    return timbri;
  }
  
  private static File getResourceFileAllowNull(String filename) {
    File resourceFile = null;
    try {
      resourceFile = new ClassPathResource( filename ).getFile();
    } catch (Exception ex) {
      return null;
    }
    if (resourceFile == null || !resourceFile.exists()) {
      return null;
    }
    return resourceFile;
  }
  
  private static byte[] nonalfanum = new byte[48];
  
  private static String inputStreamToBase64 (InputStream is) throws IOException {
    byte[] buf = new byte[1024];
    int len;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    while ((len = is.read(buf)) > -1) {
      baos.write(buf, 0, len);
    }
    byte[] content = Base64Utils.toBase64(baos.toByteArray()).getBytes();
    for (int it = 0; it < content.length; it++) {
      
      if (content[it] >= '0' && content[it] <= '9') {
      } else if (content[it] >= 'A' && content[it] <= 'Z') {
      } else if (content[it] >= 'a' && content[it] <= 'z') {
      } else {
        boolean found = false;
        for (int ita = 0; ita < nonalfanum.length; ita++) {
          if (content[it] == nonalfanum[ita]) {
            found = true;
            break;
          }
        }
        if (!found) {
          for (int ita = 0; ita < nonalfanum.length; ita++) {
            if (nonalfanum[ita] == 0) {
              nonalfanum[ita] = content[it];
              break;
            }
          }
        }
      }
      
      if (content[it] == '_') {
        content[it] = '/';
      }
      if (content[it] == '$') {
        content[it] = '+';
      }
    }
    return new String(content);
  }
  
}
