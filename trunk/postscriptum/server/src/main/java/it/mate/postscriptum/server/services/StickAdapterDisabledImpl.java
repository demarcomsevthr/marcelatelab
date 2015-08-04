package it.mate.postscriptum.server.services;

import it.mate.postscriptum.shared.model.RemoteUser;
import it.mate.postscriptum.shared.model.StickMail;
import it.mate.postscriptum.shared.model.StickMail2;
import it.mate.postscriptum.shared.model.StickSms;
import it.mate.postscriptum.shared.model.StickSms2;
import it.mate.postscriptum.shared.service.AdapterException;

import java.util.List;

import org.springframework.stereotype.Service;

/* @Service */
public class StickAdapterDisabledImpl implements StickAdapter {

  @Override
  public String sendDevInfo(String os, String layout, String devName, String phgVersion, String platform, String devUuid, String devVersion) {
    throw new RuntimeException("Service is temporary unavailable");
  }

  @Override
  public void sendSmsTest(String to, String msg) {
    throw new RuntimeException("Service is temporary unavailable");
  }

  @Override
  public void checkScheduledMails() {
    throw new RuntimeException("Service is temporary unavailable");
  }

  @Override
  public void checkScheduledSMSs() {
    throw new RuntimeException("Service is temporary unavailable");
  }

  @Override
  public StickMail create(StickMail entity, String devInfoId) {
    throw new RuntimeException("Service is temporary unavailable");
  }

  @Override
  public List<StickMail> findMailsByUser(RemoteUser user) {
    throw new RuntimeException("Service is temporary unavailable");
  }

  @Override
  public List<StickMail> findScheduledMailsByUser(RemoteUser user) {
    throw new RuntimeException("Service is temporary unavailable");
  }

  @Override
  public void delete(List<StickMail> entities) {
    throw new RuntimeException("Service is temporary unavailable");
  }

  @Override
  public StickMail2 createV2(StickMail2 entity) {
    throw new RuntimeException("Service is temporary unavailable");
  }

  @Override
  public List<StickMail2> findMailsByUserV2(RemoteUser user) {
    throw new RuntimeException("Service is temporary unavailable");
  }

  @Override
  public List<StickMail2> findScheduledMailsByUserV2(RemoteUser user) {
    throw new RuntimeException("Service is temporary unavailable");
  }

  @Override
  public StickSms createSMS(StickSms entity) throws AdapterException {
    throw new AdapterException("Service is temporary unavailable");
  }

  @Override
  public List<StickSms> findScheduledSMSsByUser(RemoteUser user) {
    throw new RuntimeException("Service is temporary unavailable");
  }

  @Override
  public void deleteSMS(List<StickSms> entities) {
    throw new RuntimeException("Service is temporary unavailable");
  }

  @Override
  public StickSms2 createOrUpdateSMSV2(StickSms2 entity) throws AdapterException {
    throw new AdapterException("Service is temporary unavailable");
  }

  @Override
  public List<StickSms2> findScheduledSMSsByUserV2(RemoteUser user) {
    throw new RuntimeException("Service is temporary unavailable");
  }

  @Override
  public void updateUserInfos() {
    throw new RuntimeException("Service is temporary unavailable");
  }

  @Override
  public void purgeNotifiedSMSs() {
    throw new RuntimeException("Service is temporary unavailable");
  }
  
}
