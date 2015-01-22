package it.mate.copymob.client.logic;

import it.mate.copymob.shared.model.Order;
import it.mate.copymob.shared.model.OrderItem;
import it.mate.copymob.shared.model.OrderItemRow;
import it.mate.copymob.shared.model.Timbro;
import it.mate.copymob.shared.model.impl.OrderItemRowTx;
import it.mate.copymob.shared.model.impl.OrderItemTx;
import it.mate.copymob.shared.model.impl.OrderTx;
import it.mate.copymob.shared.model.impl.TimbroTx;
import it.mate.gwtcommons.client.utils.Delegate;
import it.mate.phgcommons.client.utils.PhonegapLog;
import it.mate.phgcommons.client.utils.WebSQLDao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**************************************************
 * 
 * [19/06/2014]
 * 
 * IMPORTANTISSIMO
 * 
 * NEI LOOP DI READ (iterateXXXForRead) NON CHIAMARE MAI METODI CHE CREANO NUOVE TRANSAZIONI
 * 
 * ES: iteratePrescrizioniForRead e findContattoById
 * 
 * DA JavaScriptException (InvalidStateError)
 * 
 *
 */

public class MainDao extends WebSQLDao {
  
  private final static boolean DROP_TABLES_ON_OPEN_DATABASE = false;
  
  private final static long ESTIMATED_SIZE = 5 * 1024 * 1024;
  
  // ATTENZIONE: LASCIARE IL CAMPO IMAGE IN FONDO (NELLA CREATE VIENE ACCODATO IL DATATYPE BLOB)
  private final static String TIMBRI_FIELDS_0 = "nome, codice, width, height, oval, image ";

  private final static String TIMBRI_FIELDS = TIMBRI_FIELDS_0;
  
  private final static String ORDER_FIELDS_0 = "codice, accountId, state ";

  private final static String ORDER_FIELDS = ORDER_FIELDS_0;
  
  private final static String ORDER_ITEM_FIELDS_0 = "orderId, timbroId, quantity ";

  private final static String ORDER_ITEM_FIELDS = ORDER_ITEM_FIELDS_0;
  
  private final static String ORDER_ITEM_ROW_FIELDS_0 = "orderItemId, text ";

  private final static String ORDER_ITEM_ROW_FIELDS = ORDER_ITEM_ROW_FIELDS_0;
  
  private List<Timbro> cacheTimbri;
  
  
  
  public MainDao() {
    super("CopyMobDB", ESTIMATED_SIZE, migrationCallbacks, new DatabaseCallback() {
      public void handleEvent(WindowDatabase db) {
        PhonegapLog.log("created db copymob");
      }
    }, new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        if (DROP_TABLES_ON_OPEN_DATABASE) {
          dropDBImpl(tr);
        }
      }
    });
  }
  
  public void dropDB(final Delegate<Void> delegate) {
    PhonegapLog.log("resetting db");
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        dropDBImpl(tr);
        db = null;
        initDB();
        delegate.execute(null);
      }
    });
  }
  
  private static void dropDBImpl(SQLTransaction tr) {
    PhonegapLog.log("dropping all tables");
    
    PhonegapLog.log("dropping table version");
    tr.doExecuteSql("DROP TABLE IF EXISTS version");
    
    PhonegapLog.log("dropping table timbri");
    tr.doExecuteSql("DROP TABLE IF EXISTS timbri");
    
    PhonegapLog.log("dropping table order");
    tr.doExecuteSql("DROP TABLE IF EXISTS orderHeader");
    
    PhonegapLog.log("dropping table orderItem");
    tr.doExecuteSql("DROP TABLE IF EXISTS orderItem");
    
    PhonegapLog.log("dropping table orderItemRow");
    tr.doExecuteSql("DROP TABLE IF EXISTS orderItemRow");
    
  }
  
  private final static MigratorCallback MIGRATION_CALLBACK_0 = new MigratorCallback() {
    public void doMigration(int number, SQLTransaction tr) {
      PhonegapLog.log("updating db copymob to version " + number);

      PhonegapLog.log("creating table timbri");
      tr.doExecuteSql("CREATE TABLE timbri (id "+SERIAL_ID+", " + TIMBRI_FIELDS_0 + " BLOB )");

      PhonegapLog.log("creating table order");
      tr.doExecuteSql("CREATE TABLE orderHeader (id "+SERIAL_ID+", " + ORDER_FIELDS_0 + " )");

      PhonegapLog.log("creating table orderItem");
      tr.doExecuteSql("CREATE TABLE orderItem (id "+SERIAL_ID+", " + ORDER_ITEM_FIELDS_0 + " )");

      PhonegapLog.log("creating table orderItemRow");
      tr.doExecuteSql("CREATE TABLE orderItemRow (id "+SERIAL_ID+", " + ORDER_ITEM_ROW_FIELDS_0 + " )");

    }
  };
  
  private static final MigratorCallback[] migrationCallbacks = new MigratorCallback[] {
    MIGRATION_CALLBACK_0 
  };
  
  private void ensureCacheTimbri(final Delegate<Void> delegate) {
    findAllTimbri(new Delegate<List<Timbro>>() {
      public void execute(List<Timbro> results) {
        delegate.execute(null);
      }
    });
  }
  
  public void findAllTimbri(final Delegate<List<Timbro>> delegate) {
    if (this.cacheTimbri != null) {
      delegate.execute(cloneList(cacheTimbri));
    } else {
      db.doReadTransaction(new SQLTransactionCallback() {
        public void handleEvent(SQLTransaction tr) {
          tr.doExecuteSql("SELECT id, " + TIMBRI_FIELDS + " FROM timbri ORDER BY id", null, new SQLStatementCallback() {
            public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
              List<Timbro> results = new ArrayList<Timbro>();
              if (rs.getRows().getLength() > 0) {
                for (int it = 0; it < rs.getRows().getLength(); it++) {
                  results.add(flushRSToTimbro(rs, it));
                }
              }
              cacheTimbri = results;
              delegate.execute(cloneList(results));
            }
          });
        }
      });
    }
  }
  
  public void findTimbro(final Integer id, final Delegate<Timbro> delegate) {
    if (this.cacheTimbri != null) {
      for (Timbro timbro : cacheTimbri) {
        if (timbro.getId().equals(id)) {
          delegate.execute(timbro);
          return;
        }
      }
    }
    db.doReadTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        tr.doExecuteSql("SELECT id, " + TIMBRI_FIELDS + " FROM timbri WHERE id = ?", 
            new Object[]{id}, new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            if (rs.getRows().getLength() > 0) {
              for (int it = 0; it < rs.getRows().getLength(); it++) {
                Timbro timbro = flushRSToTimbro(rs, it);
                delegate.execute(timbro);
              }
            }
          }
        });
      }
    });
  }
  
  private <T> List<T> cloneList(List<T> items) {
    if (items == null) {
      return null;
    }
    List<T> results = new ArrayList<T>();
    for (T item : items) {
      results.add(item);
    }
    return results;
  }
  
  private Timbro flushRSToTimbro(SQLResultSet rs, int it) {
    SQLResultSetRowList rows = rs.getRows();
    Timbro result = new TimbroTx();
    result.setId(rows.getValueInt(it, "id"));
    result.setNome(rows.getValueString(it, "nome"));
    result.setCodice(rows.getValueString(it, "codice"));
    result.setImage(rows.getValueString(it, "image"));
    result.setWidth(rows.getValueDouble(it, "width"));
    result.setHeight(rows.getValueDouble(it, "height"));
    result.setOvalInt(rows.getValueInt(it, "oval"));
    return result;
  }
  
  public void saveTimbro(final Timbro entity, final Delegate<Timbro> delegate) {
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        if (entity.getId() == null) {
          tr.doExecuteSql("INSERT INTO timbri (" + TIMBRI_FIELDS + ") VALUES (?, ?, ?, ?, ?, ?)", 
              new Object[] {
                entity.getNome(), 
                entity.getCodice(),
                entity.getWidth(),
                entity.getHeight(),
                entity.getOvalInt(),
                entity.getImage()
              }, new SQLStatementCallback() {
                public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                  entity.setId(rs.getInsertId());
                  PhonegapLog.log("Inserted " + entity);
                  delegate.execute(entity);
                }
              });
        } else {
          String sql = "UPDATE timbri SET ";
          sql += " nome = ?";
          sql += " ,codice = ?";
          sql += " ,image = ?";
          sql += " ,width = ?";
          sql += " ,height = ?";
          sql += " ,oval = ?";
          sql += " WHERE id = ?";
          tr.doExecuteSql(sql, new Object[] {
              entity.getNome(), 
              entity.getCodice(),
              entity.getImage(),
              entity.getWidth(),
              entity.getHeight(),
              entity.getOvalInt(),
              entity.getId()
            }, new SQLStatementCallback() {
              public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                PhonegapLog.log("Updated " + entity);
                delegate.execute(entity);
              }
            });
        }
      }
    });
  }
  
  /*******************************************************************************
   *   ORDERS
   *******************************************************************************/
  
  public void findAllOrders(final Delegate<List<Order>> delegate) {
    db.doReadTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        tr.doExecuteSql("SELECT id, " + ORDER_FIELDS + " FROM orderHeader ORDER BY id", null, new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            new RSToOrderIterator(tr, rs, new Delegate<List<Order>>() {
              public void execute(List<Order> results) {
                delegate.execute(results);
              }
            });
          }
        });
      }
    });
  }
  
  public void findOpenOrder(final Delegate<List<Order>> delegate) {
    db.doReadTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        tr.doExecuteSql("SELECT id, " + ORDER_FIELDS + " FROM orderHeader WHERE orderHeader.state = ?", 
            new Object[]{Order.STATE_OPEN}, new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            new RSToOrderIterator(tr, rs, new Delegate<List<Order>>() {
              public void execute(List<Order> results) {
                delegate.execute(results);
              }
            });
          }
        });
      }
    });
  }
  
  protected class RSToOrderIterator {
    SQLResultSet rs;
    SQLTransaction tr;
    Delegate<List<Order>> delegate;
    List<Order> results = new ArrayList<Order>();
    public RSToOrderIterator(SQLTransaction tr, SQLResultSet rs, Delegate<List<Order>> delegate) {
      this.tr = tr;
      this.rs = rs;
      this.delegate = delegate;
      iterate(0);
    }
    private void iterate(final int it) {
      if (it < rs.getRows().getLength()) {
        final Order result = flushRS(rs, it);
        results.add(result);
        findOrderItems(tr, result.getId(), new Delegate<List<OrderItem>>() {
          public void execute(List<OrderItem> items) {
            result.setItems(items);
            iterate(it + 1);
          }
        });
      } else {
        delegate.execute(results);
      }
    }
    private Order flushRS(SQLResultSet rs, int it) {
      SQLResultSetRowList rows = rs.getRows();
      Order result = new OrderTx();
      result.setId(rows.getValueInt(it, "id"));
      result.setCodice(rows.getValueString(it, "codice"));
      result.setAccountId(rows.getValueInt(it, "accountId"));
      result.setState(rows.getValueInt(it, "state"));
      return result;
    }
    protected List<Order> getResults() {
      return results;
    }
  }
  
  protected void findOrderItems(SQLTransaction tr, Integer orderId, final Delegate<List<OrderItem>> delegate) {
    tr.doExecuteSql("SELECT id, " + ORDER_ITEM_FIELDS + " FROM orderItem ORDER BY id", null, new SQLStatementCallback() {
      public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
        new RSToOrderItemIterator(tr, rs, new Delegate<List<OrderItem>>() {
          public void execute(List<OrderItem> results) {
            delegate.execute(results);
          }
        });
      }
    });
  }
  
  protected class RSToOrderItemIterator {
    SQLResultSet rs;
    SQLTransaction tr;
    Delegate<List<OrderItem>> delegate;
    List<OrderItem> results = new ArrayList<OrderItem>();
    public RSToOrderItemIterator(SQLTransaction tr, SQLResultSet rs, Delegate<List<OrderItem>> delegate) {
      this.tr = tr;
      this.rs = rs;
      this.delegate = delegate;
      iterate(0);
    }
    private void iterate(final int it) {
      if (it < rs.getRows().getLength()) {
        final OrderItem result = flushRS(rs, it);
        results.add(result);
        
        findTimbro(result.getTimbroId(), new Delegate<Timbro>() {
          public void execute(Timbro timbro) {
            result.setTimbro(timbro);
            findOrderItemRows(tr, result.getId(), new Delegate<List<OrderItemRow>>() {
              public void execute(List<OrderItemRow> rows) {
                result.setRows(rows);
                iterate(it + 1);
              }
            });
          }
        });
        
      } else {
        delegate.execute(results);
      }
    }
    private OrderItem flushRS(SQLResultSet rs, int it) {
      SQLResultSetRowList rows = rs.getRows();
      OrderItem result = new OrderItemTx();
      result.setId(rows.getValueInt(it, "id"));
      result.setOrderId(rows.getValueInt(it, "orderId"));
      result.setTimbroId(rows.getValueInt(it, "timbroId"));
      result.setQuantity(rows.getValueDouble(it, "quantity"));
      return result;
    }
    protected List<OrderItem> getResults() {
      return results;
    }
  }
  
  protected void findOrderItemRows(SQLTransaction tr, Integer orderItemId, final Delegate<List<OrderItemRow>> delegate) {
    tr.doExecuteSql("SELECT id, " + ORDER_ITEM_ROW_FIELDS + " FROM orderItemRow ORDER BY id", null, new SQLStatementCallback() {
      public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
        new RSToOrderItemRowIterator(tr, rs, new Delegate<List<OrderItemRow>>() {
          public void execute(List<OrderItemRow> results) {
            delegate.execute(results);
          }
        });
      }
    });
  }
  
  protected class RSToOrderItemRowIterator {
    SQLResultSet rs;
    SQLTransaction tr;
    Delegate<List<OrderItemRow>> delegate;
    List<OrderItemRow> results = new ArrayList<OrderItemRow>();
    public RSToOrderItemRowIterator(SQLTransaction tr, SQLResultSet rs, Delegate<List<OrderItemRow>> delegate) {
      this.tr = tr;
      this.rs = rs;
      this.delegate = delegate;
      iterate(0);
    }
    private void iterate(final int it) {
      if (it < rs.getRows().getLength()) {
        OrderItemRow result = flushRS(rs, it);
        results.add(result);
        iterate(it + 1);
      } else {
        delegate.execute(results);
      }
    }
    private OrderItemRow flushRS(SQLResultSet rs, int it) {
      SQLResultSetRowList rows = rs.getRows();
      OrderItemRow result = new OrderItemRowTx();
      result.setId(rows.getValueInt(it, "id"));
      result.setOrderItemId(rows.getValueInt(it, "orderItemId"));
      result.setText(rows.getValueString(it, "text"));
      return result;
    }
    protected List<OrderItemRow> getResults() {
      return results;
    }
  }
  
  
  public void saveOrder(final Order entity, final Delegate<Order> delegate) {
    db.doTransaction(new SQLTransactionCallback() {
      public void handleEvent(SQLTransaction tr) {
        if (entity.getId() == null) {
          tr.doExecuteSql("INSERT INTO orderHeader (" + ORDER_FIELDS + ") VALUES (?, ?, ?)", 
              new Object[] {
                entity.getCodice(), 
                entity.getAccountId(),
                entity.getState()
              }, new SQLStatementCallback() {
                public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                  entity.setId(rs.getInsertId());
                  iterateOrderItemsForUpdate(tr, entity.getItems().iterator(), new Delegate<Void>() {
                    public void execute(Void element) {
                      PhonegapLog.log("Inserted " + entity);
                      if (delegate != null) {
                        delegate.execute(entity);
                      }
                    }
                  });
                }
              });
        } else {
          String sql = "UPDATE orderHeader SET ";
          sql += "  codice = ?";
          sql += " ,accountId = ?";
          sql += " ,state = ?";
          sql += " WHERE id = ?";
          tr.doExecuteSql(sql, new Object[] {
              entity.getCodice(), 
              entity.getAccountId(),
              entity.getState(),
              entity.getId()
            }, new SQLStatementCallback() {
              public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
                iterateOrderItemsForUpdate(tr, entity.getItems().iterator(), new Delegate<Void>() {
                  public void execute(Void element) {
                    PhonegapLog.log("Updated " + entity);
                    if (delegate != null) {
                      delegate.execute(entity);
                    }
                  }
                });
              }
            });
        }
      }
    });
  }
  
  protected void iterateOrderItemsForUpdate(final SQLTransaction tr, final Iterator<OrderItem> it, final Delegate<Void> delegate) {
    if (it.hasNext()) {
      final OrderItem item = it.next();
      updateOrderItem(tr, item, new Delegate<OrderItem>() {
        public void execute(OrderItem item) {
          iterateOrderItemsForUpdate(tr, it, delegate);
        }
      });
    } else {
      delegate.execute(null);
    }
  }

  protected void updateOrderItem(SQLTransaction tr, final OrderItem entity, final Delegate<OrderItem> delegate) {
    if (entity.getId() == null) {
      tr.doExecuteSql("INSERT INTO orderItem (" + ORDER_ITEM_FIELDS + ") VALUES (?, ?, ?)", 
          new Object[] {
            entity.getOrderId(), 
            entity.getTimbroId(),
            entity.getQuantity()
          }, new SQLStatementCallback() {
            public void handleEvent(final SQLTransaction tr, SQLResultSet rs) {
              entity.setId(rs.getInsertId());
              purgeOrderItemRows(tr, entity, new Delegate<Void>() {
                public void execute(Void element) {
                  iterateOrderItemRowsForUpdate(tr, entity.getRows().iterator(), new Delegate<Void>() {
                    public void execute(Void element) {
                      PhonegapLog.log("Inserted " + entity);
                      delegate.execute(entity);
                    }
                  });
                }
              });
            }
          });
    } else {
      String sql = "UPDATE orderItem SET ";
      sql += "  orderId = ?";
      sql += " ,timbroId = ?";
      sql += " ,quantity = ?";
      sql += " WHERE id = ?";
      tr.doExecuteSql(sql, new Object[] {
          entity.getOrderId(), 
          entity.getTimbroId(),
          entity.getQuantity(),
          entity.getId()
        }, new SQLStatementCallback() {
          public void handleEvent(final SQLTransaction tr, SQLResultSet rs) {
            purgeOrderItemRows(tr, entity, new Delegate<Void>() {
              public void execute(Void element) {
                iterateOrderItemRowsForUpdate(tr, entity.getRows().iterator(), new Delegate<Void>() {
                  public void execute(Void element) {
                    PhonegapLog.log("Updated " + entity);
                    delegate.execute(entity);
                  }
                });
              }
            });
          }
        });
    }
  }
  
  protected void purgeOrderItemRows(SQLTransaction tr, OrderItem item, final Delegate<Void> delegate) {
    if (item.getId() == null) {
      delegate.execute(null);
      return;
    }
    String idsList = "";
    for (OrderItemRow row : item.getRows()) {
      if (idsList.length() > 0) {
        idsList += ",";
      }
      idsList += "" + row.getId();
    }
    tr.doExecuteSql("DELETE FROM orderItemRow WHERE orderItemId = ? AND id NOT IN (" + idsList + ")", 
        new Object[] {
          item.getId()
        }, new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            delegate.execute(null);
          }
        });
  }
  
  protected void iterateOrderItemRowsForUpdate(final SQLTransaction tr, final Iterator<OrderItemRow> it, final Delegate<Void> delegate) {
    if (it.hasNext()) {
      final OrderItemRow item = it.next();
      updateOrderItemRow(tr, item, new Delegate<OrderItemRow>() {
        public void execute(OrderItemRow item) {
          iterateOrderItemRowsForUpdate(tr, it, delegate);
        }
      });
    } else {
      delegate.execute(null);
    }
  }

  protected void updateOrderItemRow(SQLTransaction tr, final OrderItemRow entity, final Delegate<OrderItemRow> delegate) {
    if (entity.getId() == null) {
      tr.doExecuteSql("INSERT INTO orderItemRow (" + ORDER_ITEM_ROW_FIELDS + ") VALUES (?, ?)", 
          new Object[] {
            entity.getOrderItemId(), 
            entity.getText()
          }, new SQLStatementCallback() {
            public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
              entity.setId(rs.getInsertId());
              PhonegapLog.log("Inserted " + entity);
              delegate.execute(entity);
            }
          });
    } else {
      String sql = "UPDATE orderItemRow SET ";
      sql += "  orderItemId = ?";
      sql += " ,text = ?";
      sql += " WHERE id = ?";
      tr.doExecuteSql(sql, new Object[] {
          entity.getOrderItemId(), 
          entity.getText(),
          entity.getId()
        }, new SQLStatementCallback() {
          public void handleEvent(SQLTransaction tr, SQLResultSet rs) {
            PhonegapLog.log("Updated " + entity);
            delegate.execute(entity);
          }
        });
    }
  }
  
}
