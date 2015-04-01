package it.mate.copymob.server.services;

import it.mate.commons.server.dao.Dao;
import it.mate.commons.server.dao.ParameterDefinition;
import it.mate.commons.server.utils.CloneUtils;
import it.mate.commons.server.utils.DateUtils;
import it.mate.commons.server.utils.LoggingUtils;
import it.mate.commons.server.utils.StringUtils;
import it.mate.copymob.server.model.AccountDs;
import it.mate.copymob.server.model.CounterDs;
import it.mate.copymob.server.model.DevInfoDs;
import it.mate.copymob.server.model.MessageDs;
import it.mate.copymob.server.model.OrderDs;
import it.mate.copymob.server.model.OrderItemDs;
import it.mate.copymob.server.model.OrderItemRowDs;
import it.mate.copymob.server.model.TimbroDs;
import it.mate.copymob.shared.model.Account;
import it.mate.copymob.shared.model.DevInfo;
import it.mate.copymob.shared.model.Message;
import it.mate.copymob.shared.model.Order;
import it.mate.copymob.shared.model.OrderItem;
import it.mate.copymob.shared.model.OrderItemRow;
import it.mate.copymob.shared.model.Timbro;
import it.mate.copymob.shared.model.impl.AccountTx;
import it.mate.copymob.shared.model.impl.DevInfoTx;
import it.mate.copymob.shared.model.impl.OrderTx;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.Base64Utils;

@Service
public class RemoteAdapterImpl implements RemoteAdapter {
  
  @Autowired private Dao dao;
  
  private List<Timbro> cacheTimbri;
  
  private static byte[] nonalfanum = new byte[48];
  
  @PostConstruct
  public void postConstruct() {
    LoggingUtils.debug(getClass(), "initialized " + this);
    if (dao == null) {
      LoggingUtils.error(getClass(), "error", new InstantiationException("dao is null!"));
    }
  }

  @Override
  public void scheduledChecks() {

  }

  @Override
  public DevInfo sendDevInfo(DevInfo devInfo) {
    DevInfoDs ds = null;
    if (devInfo.getDevUuid() != null) {
      ds = dao.findSingle(DevInfoDs.class, "devUuid == devUuidParam" ,
          Dao.Utils.buildParameters(new ParameterDefinition[] {
              new ParameterDefinition(String.class, "devUuidParam")
          }) , null, devInfo.getDevUuid());
    }
    if (ds == null) {
      ds = CloneUtils.clone(devInfo, DevInfoDs.class);
      LoggingUtils.debug(getClass(), "creating " + ds);
      ds = dao.create(ds);
    }
    return CloneUtils.clone(ds, DevInfoTx.class);
  }

  @Override
  public Account saveAccount(Account tx) {
    // controllo se arriva una create doppia dallo stesso device
    if (tx.getId() == null && tx.getDevInfoId() != null) {
      Key devInfoKey = KeyFactory.stringToKey(tx.getDevInfoId());
      AccountDs ds = dao.findSingle(AccountDs.class, "devInfoId == devInfoIdParam", 
          Dao.Utils.buildParameters(new ParameterDefinition[] {
              new ParameterDefinition(Key.class, "devInfoIdParam")
          }), 
        null, devInfoKey );
      if (ds != null) {
        tx.setId(ds.getId());
      }
    }
    AccountDs ds = CloneUtils.clone(tx, AccountDs.class);
    if (tx.getId() == null) {
      ds = dao.create(ds);
      LoggingUtils.debug(getClass(), "created account " + ds);
    } else {
      ds = dao.update(ds);
      LoggingUtils.debug(getClass(), "updated account " + ds);
    }
    return CloneUtils.clone (ds, AccountTx.class);
  }

  public List<Timbro> getTimbri() throws Exception {
    if (cacheTimbri == null) {
      List<Timbro> timbri;
      List<TimbroDs> timbriDs = dao.findAll(TimbroDs.class);
      if (timbriDs == null || timbriDs.size() == 0) {
        timbri = AdapterUtil.getInitAdapterBean().getTimbri();
        for (Timbro timbro : timbri) {
          TimbroDs timbroDs = (TimbroDs)timbro;
          timbroDs = dao.create(timbroDs);
          timbriDs.add(timbroDs);
        }
      }
      timbri = new ArrayList<Timbro>();
      for (TimbroDs timbroDs : timbriDs) {
        timbri.add(timbroDs);
      }
      timbri = loadTimbriImages(timbri);
      cacheTimbri = timbri;
    }
    return cacheTimbri;
  }
  
  private List<Timbro> loadTimbriImages(List<Timbro> timbri) throws Exception {
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
  

  /** >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> **/
  
  
  @Override
  public Order saveOrder(Order order) {
    OrderDs orderDs = CloneUtils.clone(order, OrderDs.class);
    
    if (orderDs.getState() == Order.STATE_IN_CART) {
      orderDs.setState(Order.STATE_RECEIVED);
      String codice = DateUtils.dateToString(new Date(), "yyyy");
      codice += StringUtils.formatNumber(getNextCounterValue(), 4);
      orderDs.setCodice(codice);
    }
    
    orderDs = createOrUpdateOrderDs(orderDs);
    return CloneUtils.clone (orderDs, OrderTx.class);
  }
  
  private OrderDs createOrUpdateOrderDs (OrderDs orderDs) {
    List<OrderItem> items = orderDs.getItems();
    if (items != null) {
      for (int it = 0; it < items.size(); it++) {
        OrderItemDs itemDs = (OrderItemDs)items.get(it);
        itemDs = createOrUpdateOrderItemDs(itemDs);
        items.set(it, itemDs);
      }
      orderDs.setItems(items);
    }
    if (orderDs.getKey() == null) {
      orderDs = dao.create(orderDs);
    } else {
      orderDs = dao.update(orderDs);
    }
    return orderDs;
  }
  
  private OrderItemDs createOrUpdateOrderItemDs (OrderItemDs itemDs) {
    List<OrderItemRow> rows = itemDs.getRows();
    if (rows != null) {
      for (int it = 0; it < rows.size(); it++) {
        OrderItemRowDs rowDs = (OrderItemRowDs)rows.get(it);
        rowDs = createOrUpdateOrderItemRowDs(rowDs);
        rows.set(it, rowDs);
      }
      itemDs.setRows(rows);
    }
    List<Message> messages = itemDs.getMessages();
    if (messages != null) {
      for (int it = 0; it < messages.size(); it++) {
        MessageDs messageDs = (MessageDs)messages.get(it);
        messageDs = createOrUpdateMessageDs(messageDs);
        messages.set(it, messageDs);
      }
      itemDs.setMessages(messages);
    }
    if (itemDs.getKey() == null) {
      itemDs = dao.create(itemDs);
    } else {
      itemDs = dao.update(itemDs);
    }
    return itemDs;
  }
  
  private OrderItemRowDs createOrUpdateOrderItemRowDs (OrderItemRowDs rowDs) {
    if (rowDs.getKey() == null) {
      rowDs = dao.create(rowDs);
    } else {
      rowDs = dao.update(rowDs);
    }
    return rowDs;
  }
  
  private MessageDs createOrUpdateMessageDs (MessageDs messageDs) {
    if (messageDs.getKey() == null) {
      messageDs = dao.create(messageDs);
    } else {
      messageDs = dao.update(messageDs);
    }
    return messageDs;
  }
  
  protected long getNextCounterValue () {
    long result = -1;
    List<CounterDs> results = dao.findAll(CounterDs.class);
    if (results != null && results.size() > 0) {
      CounterDs counter = results.get(0);
      result = counter.getValue();
      result ++;
      counter.setValue(result);
      counter = dao.update(counter);
    } else {
      result = 1;
      CounterDs counter = new CounterDs();
      counter.setValue(result);
      counter = dao.create(counter);
    }
    return result;
  }
  
}
