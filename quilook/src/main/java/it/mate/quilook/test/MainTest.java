package it.mate.quilook.test;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainTest {
  
  public static void main(String[] args) {

    MainTest test = new MainTest();
    
    try {
      test.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    
  }
  
  
  private void run() throws Exception {
    
    
    URL url = new URL("http://localhost:8080/_ah/api/qu/v1/message");
    
    URLConnection conn = url.openConnection();
    
    InputStream is = conn.getInputStream();
    byte[] b = new byte[1024];
    int len;
    
    StringBuffer sb = new StringBuffer();
    
    while ( (len = is.read(b, 0, 1024)) > -1) {
      sb.append(new String(b));
    }

    System.out.println(sb.toString());
    
  }
  

}
