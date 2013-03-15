package it.mate.econyx.server.services;


public interface ResourcesService {

  public byte[] getZippedThemeResourceAsStream(String themeId, String resourcePath) throws Exception;

}
