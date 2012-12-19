package it.mate.econyx.server.services.impl;

import it.mate.econyx.server.services.ResourcesService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

public class ResourcesServiceImpl implements ResourcesService {
  
  private static Logger logger = Logger.getLogger(ResourcesServiceImpl.class);
	
  private Map<String, Theme> cache = new HashMap<String, Theme>();
  
  private File themesLocation;
  
  public void setThemesLocation(File themesLocation) {
    this.themesLocation = themesLocation;
//  logger.debug(themesLocation.getFile());
  }
  
  public byte[] getZippedThemeResourceAsStream(String themeId, String resourcePath) throws Exception {
    Theme theme = cache.get(themeId);
    if (theme == null) {
      theme = new Theme();
      cache.put(themeId, theme);
      theme.id = themeId;
      theme.file = new File(themesLocation, themeId + ".zip");
    }
    Resource resource = theme.resources.get(resourcePath);
    if (resource == null) {
      resource = new Resource();
      theme.resources.put(resourcePath, resource);
      ZipInputStream zis = new ZipInputStream(new FileInputStream(theme.file));
      ZipEntry entry;
      while ((entry = zis.getNextEntry()) != null) {
        if (resourcePath.equals(entry.getName())) {
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          byte[] buffer = new byte[1024];
          int len;
          while ((len = zis.read(buffer)) > -1) {
            baos.write(buffer, 0, len);
          }
          resource.stream = baos.toByteArray();
          baos.close();
          break;
        }
      }
      zis.close();
    }
    if (resource == null || resource.stream == null) {
      throw new NullPointerException(String.format("Impossibile reperire la risorsa %s dal tema %s", resourcePath, themeId));
    }
    return resource.stream;
  }
  
  private class Theme {
    String id;
    File file;
    Map<String, Resource> resources = new HashMap<String, Resource>();
  }
  
  private class Resource {
    String path;
    byte[] stream;
  }
  
}
