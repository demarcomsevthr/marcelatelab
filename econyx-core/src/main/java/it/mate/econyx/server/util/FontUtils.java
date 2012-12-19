package it.mate.econyx.server.util;

import it.mate.econyx.shared.util.FontTypes;
import it.mate.gwtcommons.server.utils.SpringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class FontUtils {

  private static final Logger logger = Logger.getLogger(FontUtils.class);
  
  public Map<String, File> getFontResourceMap() {
    Map<String, File> result = new HashMap<String, File>();
    try {
      result.put(FontTypes.ARIAL, SpringUtils.getClassPathResource("META-INF/fonts/ARIAL.TTF").getFile());
      result.put(FontTypes.ARIAL_BOLD, SpringUtils.getClassPathResource("META-INF/fonts/ARIALBD.TTF").getFile());
      result.put(FontTypes.ARIAL_BOLD_ITALIC, SpringUtils.getClassPathResource("META-INF/fonts/ARIALBI.TTF").getFile());
      result.put(FontTypes.ARIAL_ITALIC, SpringUtils.getClassPathResource("META-INF/fonts/ARIALI.TTF").getFile());
      
      result.put(FontTypes.ARIAL_NARROW, SpringUtils.getClassPathResource("META-INF/fonts/ARIALN.TTF").getFile());
      result.put(FontTypes.ARIAL_NARROW_BOLD, SpringUtils.getClassPathResource("META-INF/fonts/ARIALNB.TTF").getFile());
      result.put(FontTypes.ARIAL_NARROW_BOLD_ITALIC, SpringUtils.getClassPathResource("META-INF/fonts/ARIALNBI.TTF").getFile());
      result.put(FontTypes.ARIAL_NARROW_ITALIC, SpringUtils.getClassPathResource("META-INF/fonts/ARIALNI.TTF").getFile());
      
      result.put(FontTypes.ARIAL_BLACK, SpringUtils.getClassPathResource("META-INF/fonts/ARIBLK.TTF").getFile());

      result.put(FontTypes.COMIC, SpringUtils.getClassPathResource("META-INF/fonts/COMIC.TTF").getFile());
      result.put(FontTypes.COMIC_BOLD, SpringUtils.getClassPathResource("META-INF/fonts/COMICBD.TTF").getFile());
      
      result.put(FontTypes.COURIER, SpringUtils.getClassPathResource("META-INF/fonts/COUR.TTF").getFile());
      result.put(FontTypes.COURIER_BOLD, SpringUtils.getClassPathResource("META-INF/fonts/COURBD.TTF").getFile());
      result.put(FontTypes.COURIER_BOLD_ITALIC, SpringUtils.getClassPathResource("META-INF/fonts/COURBI.TTF").getFile());
      result.put(FontTypes.COURIER_ITALIC, SpringUtils.getClassPathResource("META-INF/fonts/COURI.TTF").getFile());
      
      result.put(FontTypes.GARAMOND, SpringUtils.getClassPathResource("META-INF/fonts/GARA.TTF").getFile());
      result.put(FontTypes.GARAMOND_BOLD, SpringUtils.getClassPathResource("META-INF/fonts/GARABD.TTF").getFile());
      result.put(FontTypes.GARAMOND_ITALIC, SpringUtils.getClassPathResource("META-INF/fonts/GARAIT.TTF").getFile());
      
      result.put(FontTypes.GEORGIA, SpringUtils.getClassPathResource("META-INF/fonts/GEORGIA.TTF").getFile());
      result.put(FontTypes.GEORGIA_BOLD, SpringUtils.getClassPathResource("META-INF/fonts/GEORGIAB.TTF").getFile());
      result.put(FontTypes.GEORGIA_BOLD_ITALIC, SpringUtils.getClassPathResource("META-INF/fonts/GEORGIAI.TTF").getFile());
      result.put(FontTypes.GEORGIA_ITALIC, SpringUtils.getClassPathResource("META-INF/fonts/GEORGIAZ.TTF").getFile());
      
      result.put(FontTypes.TAHOMA, SpringUtils.getClassPathResource("META-INF/fonts/TAHOMA.TTF").getFile());
      result.put(FontTypes.TAHOMA_BOLD, SpringUtils.getClassPathResource("META-INF/fonts/TAHOMABD.TTF").getFile());
      
      result.put(FontTypes.TIMES, SpringUtils.getClassPathResource("META-INF/fonts/TIMES.TTF").getFile());
      result.put(FontTypes.TIMES_BOLD, SpringUtils.getClassPathResource("META-INF/fonts/TIMESBD.TTF").getFile());
      result.put(FontTypes.TIMES_BOLD_ITALIC, SpringUtils.getClassPathResource("META-INF/fonts/TIMESBI.TTF").getFile());
      result.put(FontTypes.TIMES_ITALIC, SpringUtils.getClassPathResource("META-INF/fonts/TIMESI.TTF").getFile());
      
      result.put(FontTypes.TREBUCHET, SpringUtils.getClassPathResource("META-INF/fonts/TREBUC.TTF").getFile());
      result.put(FontTypes.TREBUCHET_BOLD, SpringUtils.getClassPathResource("META-INF/fonts/TREBUCBD.TTF").getFile());
      result.put(FontTypes.TREBUCHET_BOLD_ITALIC, SpringUtils.getClassPathResource("META-INF/fonts/TREBUCBI.TTF").getFile());
      result.put(FontTypes.TREBUCHET_ITALIC, SpringUtils.getClassPathResource("META-INF/fonts/TREBUCIT.TTF").getFile());
      
      result.put(FontTypes.VERDANA, SpringUtils.getClassPathResource("META-INF/fonts/verdana.ttf").getFile());
      result.put(FontTypes.VERDANA_BOLD, SpringUtils.getClassPathResource("META-INF/fonts/verdanab.ttf").getFile());
      result.put(FontTypes.VERDANA_ITALIC, SpringUtils.getClassPathResource("META-INF/fonts/verdanai.ttf").getFile());
      result.put(FontTypes.VERDANA_BOLD_ITALIC, SpringUtils.getClassPathResource("META-INF/fonts/verdanaz.ttf").getFile());
    } catch (IOException ex) {
      logger.error("error", ex);
    }
    return result;
  }
  
}
