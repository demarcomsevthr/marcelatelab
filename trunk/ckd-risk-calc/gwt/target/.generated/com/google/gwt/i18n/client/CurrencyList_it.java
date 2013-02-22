package com.google.gwt.i18n.client;

import com.google.gwt.i18n.client.impl.CurrencyDataImpl;
import com.google.gwt.core.client.JavaScriptObject;
import java.util.HashMap;

public class CurrencyList_it extends com.google.gwt.i18n.client.CurrencyList_ {
  
  @Override
  protected CurrencyData getDefaultJava() {
    return new CurrencyDataImpl("EUR", "€", 2, "€", "€");
  }
  
  @Override
  protected native CurrencyData getDefaultNative() /*-{
    return [ "EUR", "€", 2, "€", "€"];
  }-*/;
  
  @Override
  protected HashMap<String, CurrencyData> loadCurrencyMapJava() {
    HashMap<String, CurrencyData> result = super.loadCurrencyMapJava();
    // Peseta Andorrana
    result.put("ADP", new CurrencyDataImpl("ADP", "ADP", 128, "ADP", "ADP"));
    // Dirham degli Emirati Arabi Uniti
    result.put("AED", new CurrencyDataImpl("AED", "DH", 2, "DH", "dh"));
    // Afgani (1927-2002)
    result.put("AFA", new CurrencyDataImpl("AFA", "AFA", 130, "AFA", "AFA"));
    // Afgani
    result.put("AFN", new CurrencyDataImpl("AFN", "AFN", 0, "AFN", "Af."));
    // Lek Albanese
    result.put("ALL", new CurrencyDataImpl("ALL", "ALL", 0, "ALL", "Lek"));
    // Dram Armeno
    result.put("AMD", new CurrencyDataImpl("AMD", "AMD", 0, "AMD", "Dram"));
    // Fiorino delle Antille Olandesi
    result.put("ANG", new CurrencyDataImpl("ANG", "ANG", 2, "ANG", "ANG"));
    // Kwanza Angolano
    result.put("AOA", new CurrencyDataImpl("AOA", "AOA", 2, "AOA", "Kz"));
    // Kwanza Angolano (1977-1990)
    result.put("AOK", new CurrencyDataImpl("AOK", "AOK", 130, "AOK", "AOK"));
    // Nuovo Kwanza Angolano (1990-2000)
    result.put("AON", new CurrencyDataImpl("AON", "AON", 130, "AON", "AON"));
    // Kwanza Reajustado Angolano (1995-1999)
    result.put("AOR", new CurrencyDataImpl("AOR", "AOR", 130, "AOR", "AOR"));
    // Austral Argentino
    result.put("ARA", new CurrencyDataImpl("ARA", "ARA", 130, "ARA", "ARA"));
    // Peso Argentino (vecchio Cod.)
    result.put("ARP", new CurrencyDataImpl("ARP", "ARP", 130, "ARP", "ARP"));
    // Peso Argentino
    result.put("ARS", new CurrencyDataImpl("ARS", "AR$", 2, "AR$", "$"));
    // Scellino Austriaco
    result.put("ATS", new CurrencyDataImpl("ATS", "ATS", 130, "ATS", "ATS"));
    // Dollaro Australiano
    result.put("AUD", new CurrencyDataImpl("AUD", "AU$", 2, "AU$", "$"));
    // Fiorino di Aruba
    result.put("AWG", new CurrencyDataImpl("AWG", "AWG", 2, "AWG", "Afl."));
    // Manat Azero (1993-2006)
    result.put("AZM", new CurrencyDataImpl("AZM", "AZM", 130, "AZM", "AZM"));
    // Manat Azero
    result.put("AZN", new CurrencyDataImpl("AZN", "AZN", 2, "AZN", "man."));
    // Dinar Bosnia-Herzegovina
    result.put("BAD", new CurrencyDataImpl("BAD", "BAD", 130, "BAD", "BAD"));
    // Marco Conv. Bosnia-Erzegovina
    result.put("BAM", new CurrencyDataImpl("BAM", "BAM", 2, "BAM", "KM"));
    // Dollaro di Barbados
    result.put("BBD", new CurrencyDataImpl("BBD", "BBD", 2, "BBD", "$"));
    // Taka Bangladese
    result.put("BDT", new CurrencyDataImpl("BDT", "Tk", 2, "Tk", "৳"));
    // Franco Belga (convertibile)
    result.put("BEC", new CurrencyDataImpl("BEC", "BEC", 130, "BEC", "BEC"));
    // Franco Belga
    result.put("BEF", new CurrencyDataImpl("BEF", "BEF", 130, "BEF", "BEF"));
    // Franco Belga (finanziario)
    result.put("BEL", new CurrencyDataImpl("BEL", "BEL", 130, "BEL", "BEL"));
    // Lev Bulgaro
    result.put("BGL", new CurrencyDataImpl("BGL", "BGL", 130, "BGL", "BGL"));
    // Nuovo Lev Bulgaro
    result.put("BGN", new CurrencyDataImpl("BGN", "Lv", 2, "Lv", "lev"));
    // Dinaro del Bahraini
    result.put("BHD", new CurrencyDataImpl("BHD", "BHD", 3, "BHD", "din"));
    // Franco del Burundi
    result.put("BIF", new CurrencyDataImpl("BIF", "BIF", 0, "BIF", "FBu"));
    // Dollaro delle Bermuda
    result.put("BMD", new CurrencyDataImpl("BMD", "BMD", 2, "BMD", "$"));
    // Dollaro del Brunei
    result.put("BND", new CurrencyDataImpl("BND", "BND", 2, "BND", "$"));
    // Boliviano
    result.put("BOB", new CurrencyDataImpl("BOB", "BOB", 2, "BOB", "Bs"));
    // Peso Boliviano
    result.put("BOP", new CurrencyDataImpl("BOP", "BOP", 130, "BOP", "BOP"));
    // Mvdol Boliviano
    result.put("BOV", new CurrencyDataImpl("BOV", "BOV", 130, "BOV", "BOV"));
    // Cruzeiro Novo Brasiliano (1967-1986)
    result.put("BRB", new CurrencyDataImpl("BRB", "BRB", 130, "BRB", "BRB"));
    // Cruzado Brasiliano
    result.put("BRC", new CurrencyDataImpl("BRC", "BRC", 130, "BRC", "BRC"));
    // Cruzeiro Brasiliano (1990-1993)
    result.put("BRE", new CurrencyDataImpl("BRE", "BRE", 130, "BRE", "BRE"));
    // Real Brasiliano
    result.put("BRL", new CurrencyDataImpl("BRL", "R$", 2, "R$", "R$"));
    // Cruzado Novo Brasiliano
    result.put("BRN", new CurrencyDataImpl("BRN", "BRN", 130, "BRN", "BRN"));
    // Cruzeiro Brasiliano
    result.put("BRR", new CurrencyDataImpl("BRR", "BRR", 130, "BRR", "BRR"));
    // Dollaro delle Bahamas
    result.put("BSD", new CurrencyDataImpl("BSD", "BSD", 2, "BSD", "$"));
    // Ngultrum Butanese
    result.put("BTN", new CurrencyDataImpl("BTN", "BTN", 2, "BTN", "Nu."));
    // Kyat Birmano
    result.put("BUK", new CurrencyDataImpl("BUK", "BUK", 130, "BUK", "BUK"));
    // Pula del Botswana
    result.put("BWP", new CurrencyDataImpl("BWP", "BWP", 2, "BWP", "P"));
    // Nuovo Rublo Bielorussia (1994-1999)
    result.put("BYB", new CurrencyDataImpl("BYB", "BYB", 130, "BYB", "BYB"));
    // Rublo Bielorussia
    result.put("BYR", new CurrencyDataImpl("BYR", "BYR", 0, "BYR", "BYR"));
    // Dollaro Belize
    result.put("BZD", new CurrencyDataImpl("BZD", "BZD", 2, "BZD", "$"));
    // Dollaro Canadese
    result.put("CAD", new CurrencyDataImpl("CAD", "CA$", 2, "C$", "$"));
    // Franco Congolese
    result.put("CDF", new CurrencyDataImpl("CDF", "CDF", 2, "CDF", "FrCD"));
    // Franco Svizzero
    result.put("CHF", new CurrencyDataImpl("CHF", "CHF", 2, "CHF", "CHF"));
    // Unidades de Fomento Chilene
    result.put("CLF", new CurrencyDataImpl("CLF", "CLF", 128, "CLF", "CLF"));
    // Peso Cileno
    result.put("CLP", new CurrencyDataImpl("CLP", "CL$", 0, "CL$", "$"));
    // Renmimbi Cinese
    result.put("CNY", new CurrencyDataImpl("CNY", "CN¥", 2, "RMB¥", "¥"));
    // Peso Colombiano
    result.put("COP", new CurrencyDataImpl("COP", "COL$", 0, "COL$", "$"));
    // Colón Costaricano
    result.put("CRC", new CurrencyDataImpl("CRC", "CR₡", 0, "CR₡", "₡"));
    // Antico Dinaro Serbo
    result.put("CSD", new CurrencyDataImpl("CSD", "CSD", 130, "CSD", "CSD"));
    // Corona forte cecoslovacca
    result.put("CSK", new CurrencyDataImpl("CSK", "CSK", 130, "CSK", "CSK"));
    // Peso cubano convertibile
    result.put("CUC", new CurrencyDataImpl("CUC", "CUC", 2, "CUC", "$"));
    // Peso Cubano
    result.put("CUP", new CurrencyDataImpl("CUP", "$MN", 2, "$MN", "$"));
    // Escudo del Capo Verde
    result.put("CVE", new CurrencyDataImpl("CVE", "CVE", 2, "CVE", "CVE"));
    // Sterlina Cipriota
    result.put("CYP", new CurrencyDataImpl("CYP", "CYP", 130, "CYP", "CYP"));
    // Corona Ceca
    result.put("CZK", new CurrencyDataImpl("CZK", "Kč", 2, "Kč", "Kč"));
    // Ostmark della Germania Orientale
    result.put("DDM", new CurrencyDataImpl("DDM", "DDM", 130, "DDM", "DDM"));
    // Marco Tedesco
    result.put("DEM", new CurrencyDataImpl("DEM", "DEM", 130, "DEM", "DEM"));
    // Franco Gibutiano
    result.put("DJF", new CurrencyDataImpl("DJF", "Fdj", 0, "Fdj", "Fdj"));
    // Corona Danese
    result.put("DKK", new CurrencyDataImpl("DKK", "kr", 2, "kr", "kr"));
    // Peso Dominicano
    result.put("DOP", new CurrencyDataImpl("DOP", "RD$", 2, "RD$", "$"));
    // Dinaro Algerino
    result.put("DZD", new CurrencyDataImpl("DZD", "DZD", 2, "DZD", "din"));
    // Sucre dell’Ecuador
    result.put("ECS", new CurrencyDataImpl("ECS", "ECS", 130, "ECS", "ECS"));
    // Unidad de Valor Constante (UVC) dell’Ecuador
    result.put("ECV", new CurrencyDataImpl("ECV", "ECV", 130, "ECV", "ECV"));
    // Corona dell’Estonia
    result.put("EEK", new CurrencyDataImpl("EEK", "EEK", 130, "EEK", "EEK"));
    // Sterlina Egiziana
    result.put("EGP", new CurrencyDataImpl("EGP", "LE", 2, "LE", "E£"));
    // Nakfa Eritreo
    result.put("ERN", new CurrencyDataImpl("ERN", "ERN", 2, "ERN", "Nfk"));
    // Peseta Spagnola Account
    result.put("ESA", new CurrencyDataImpl("ESA", "ESA", 130, "ESA", "ESA"));
    // Peseta Spagnola Account Convertibile
    result.put("ESB", new CurrencyDataImpl("ESB", "ESB", 130, "ESB", "ESB"));
    // Peseta Spagnola
    result.put("ESP", new CurrencyDataImpl("ESP", "ESP", 128, "ESP", "ESP"));
    // Birr Etiopico
    result.put("ETB", new CurrencyDataImpl("ETB", "ETB", 2, "ETB", "Birr"));
    // Euro
    result.put("EUR", new CurrencyDataImpl("EUR", "€", 2, "€", "€"));
    // Markka Finlandese
    result.put("FIM", new CurrencyDataImpl("FIM", "FIM", 130, "FIM", "FIM"));
    // Dollaro delle Figi
    result.put("FJD", new CurrencyDataImpl("FJD", "FJD", 2, "FJD", "$"));
    // Sterlina delle Falkland
    result.put("FKP", new CurrencyDataImpl("FKP", "FKP", 2, "FKP", "£"));
    // Franco Francese
    result.put("FRF", new CurrencyDataImpl("FRF", "FRF", 130, "FRF", "FRF"));
    // Sterlina Inglese
    result.put("GBP", new CurrencyDataImpl("GBP", "UK£", 2, "GB£", "£"));
    // Kupon Larit Georgiano
    result.put("GEK", new CurrencyDataImpl("GEK", "GEK", 130, "GEK", "GEK"));
    // Lari Georgiano
    result.put("GEL", new CurrencyDataImpl("GEL", "GEL", 2, "GEL", "GEL"));
    // Cedi del Ghana
    result.put("GHC", new CurrencyDataImpl("GHC", "GHC", 130, "GHC", "GHC"));
    // Cedi ghanese
    result.put("GHS", new CurrencyDataImpl("GHS", "GHS", 2, "GHS", "GHS"));
    // Sterlina di Gibilterra
    result.put("GIP", new CurrencyDataImpl("GIP", "GIP", 2, "GIP", "£"));
    // Dalasi del Gambia
    result.put("GMD", new CurrencyDataImpl("GMD", "GMD", 2, "GMD", "GMD"));
    // Franco della Guinea
    result.put("GNF", new CurrencyDataImpl("GNF", "GNF", 0, "GNF", "FG"));
    // Syli della Guinea
    result.put("GNS", new CurrencyDataImpl("GNS", "GNS", 130, "GNS", "GNS"));
    // Ekwele della Guinea Equatoriale
    result.put("GQE", new CurrencyDataImpl("GQE", "GQE", 130, "GQE", "GQE"));
    // Dracma Greca
    result.put("GRD", new CurrencyDataImpl("GRD", "GRD", 130, "GRD", "GRD"));
    // Quetzal Guatemalteco
    result.put("GTQ", new CurrencyDataImpl("GTQ", "GTQ", 2, "GTQ", "Q"));
    // Escudo della Guinea portoghese
    result.put("GWE", new CurrencyDataImpl("GWE", "GWE", 130, "GWE", "GWE"));
    // Peso della Guinea-Bissau
    result.put("GWP", new CurrencyDataImpl("GWP", "GWP", 130, "GWP", "GWP"));
    // Dollaro della Guyana
    result.put("GYD", new CurrencyDataImpl("GYD", "GYD", 0, "GYD", "$"));
    // Dollaro di Hong Kong
    result.put("HKD", new CurrencyDataImpl("HKD", "HK$", 2, "HK$", "$"));
    // Lempira Hoduregno
    result.put("HNL", new CurrencyDataImpl("HNL", "L", 2, "L", "L"));
    // Dinaro Croato
    result.put("HRD", new CurrencyDataImpl("HRD", "HRD", 130, "HRD", "HRD"));
    // Kuna Croata
    result.put("HRK", new CurrencyDataImpl("HRK", "HRK", 2, "HRK", "kn"));
    // Gourde Haitiano
    result.put("HTG", new CurrencyDataImpl("HTG", "HTG", 2, "HTG", "HTG"));
    // Fiorino Ungherese
    result.put("HUF", new CurrencyDataImpl("HUF", "HUF", 0, "HUF", "Ft"));
    // Rupia Indonesiana
    result.put("IDR", new CurrencyDataImpl("IDR", "IDR", 0, "IDR", "Rp"));
    // Sterlina irlandese
    result.put("IEP", new CurrencyDataImpl("IEP", "IEP", 130, "IEP", "IEP"));
    // Sterlina Israeliana
    result.put("ILP", new CurrencyDataImpl("ILP", "ILP", 130, "ILP", "ILP"));
    // Nuovo sheqel israeliano
    result.put("ILS", new CurrencyDataImpl("ILS", "₪", 2, "IL₪", "₪"));
    // Rupia Indiana
    result.put("INR", new CurrencyDataImpl("INR", "Rs.", 2, "Rs", "₹"));
    // Dinaro Iracheno
    result.put("IQD", new CurrencyDataImpl("IQD", "IQD", 0, "IQD", "din"));
    // Rial Iraniano
    result.put("IRR", new CurrencyDataImpl("IRR", "IRR", 0, "IRR", "Rial"));
    // Corona Islandese
    result.put("ISK", new CurrencyDataImpl("ISK", "Kr", 0, "kr", "kr"));
    // Lira Italiana
    result.put("ITL", new CurrencyDataImpl("ITL", "ITL", 128, "ITL", "ITL"));
    // Dollaro Giamaicano
    result.put("JMD", new CurrencyDataImpl("JMD", "JA$", 2, "JA$", "$"));
    // Dinaro Giordano
    result.put("JOD", new CurrencyDataImpl("JOD", "JOD", 3, "JOD", "din"));
    // Yen Giapponese
    result.put("JPY", new CurrencyDataImpl("JPY", "JP¥", 0, "JP¥", "¥"));
    // Scellino Keniota
    result.put("KES", new CurrencyDataImpl("KES", "Ksh", 2, "Ksh", "Ksh"));
    // Som Kirghiso
    result.put("KGS", new CurrencyDataImpl("KGS", "KGS", 2, "KGS", "KGS"));
    // Riel Cambogiano
    result.put("KHR", new CurrencyDataImpl("KHR", "KHR", 2, "KHR", "Riel"));
    // Franco Comoriano
    result.put("KMF", new CurrencyDataImpl("KMF", "KMF", 0, "KMF", "CF"));
    // Won Nordcoreano
    result.put("KPW", new CurrencyDataImpl("KPW", "KPW", 0, "KPW", "₩"));
    // Won Sudcoreano
    result.put("KRW", new CurrencyDataImpl("KRW", "₩", 0, "KR₩", "₩"));
    // Dinaro Kuwaitiano
    result.put("KWD", new CurrencyDataImpl("KWD", "KWD", 3, "KWD", "din"));
    // Dollaro delle Isole Cayman
    result.put("KYD", new CurrencyDataImpl("KYD", "KYD", 2, "KYD", "$"));
    // Tenge Kazaco
    result.put("KZT", new CurrencyDataImpl("KZT", "KZT", 2, "KZT", "₸"));
    // Kip Laotiano
    result.put("LAK", new CurrencyDataImpl("LAK", "LAK", 0, "LAK", "₭"));
    // Sterlina Libanese
    result.put("LBP", new CurrencyDataImpl("LBP", "LBP", 0, "LBP", "L£"));
    // Rupia di Sri Lanka
    result.put("LKR", new CurrencyDataImpl("LKR", "SLRs", 2, "SLRs", "Rs"));
    // Dollaro Liberiano
    result.put("LRD", new CurrencyDataImpl("LRD", "LRD", 2, "LRD", "$"));
    // Loti del Lesotho
    result.put("LSL", new CurrencyDataImpl("LSL", "LSL", 2, "LSL", "LSL"));
    // Lita Lituana
    result.put("LTL", new CurrencyDataImpl("LTL", "LTL", 2, "LTL", "Lt"));
    // Talonas Lituani
    result.put("LTT", new CurrencyDataImpl("LTT", "LTT", 130, "LTT", "LTT"));
    // Franco Convertibile del Lussemburgo
    result.put("LUC", new CurrencyDataImpl("LUC", "LUC", 130, "LUC", "LUC"));
    // Franco del Lussemburgo
    result.put("LUF", new CurrencyDataImpl("LUF", "LUF", 128, "LUF", "LUF"));
    // Franco Finanziario del Lussemburgo
    result.put("LUL", new CurrencyDataImpl("LUL", "LUL", 130, "LUL", "LUL"));
    // Lat Lettone
    result.put("LVL", new CurrencyDataImpl("LVL", "LVL", 2, "LVL", "Ls"));
    // Rublo Lettone
    result.put("LVR", new CurrencyDataImpl("LVR", "LVR", 130, "LVR", "LVR"));
    // Dinaro Libico
    result.put("LYD", new CurrencyDataImpl("LYD", "LYD", 3, "LYD", "din"));
    // Dirham Marocchino
    result.put("MAD", new CurrencyDataImpl("MAD", "MAD", 2, "MAD", "MAD"));
    // Franco Marocchino
    result.put("MAF", new CurrencyDataImpl("MAF", "MAF", 130, "MAF", "MAF"));
    // Leu Moldavo
    result.put("MDL", new CurrencyDataImpl("MDL", "MDL", 2, "MDL", "MDL"));
    // Ariary Malgascio
    result.put("MGA", new CurrencyDataImpl("MGA", "MGA", 0, "MGA", "Ar"));
    // Franco Malgascio
    result.put("MGF", new CurrencyDataImpl("MGF", "MGF", 128, "MGF", "MGF"));
    // Dinaro Macedone
    result.put("MKD", new CurrencyDataImpl("MKD", "MKD", 2, "MKD", "din"));
    // Franco di Mali
    result.put("MLF", new CurrencyDataImpl("MLF", "MLF", 130, "MLF", "MLF"));
    // Kyat di Myanmar
    result.put("MMK", new CurrencyDataImpl("MMK", "MMK", 0, "MMK", "K"));
    // Tugrik Mongolo
    result.put("MNT", new CurrencyDataImpl("MNT", "MN₮", 0, "MN₮", "₮"));
    // Pataca di Macao
    result.put("MOP", new CurrencyDataImpl("MOP", "MOP", 2, "MOP", "MOP"));
    // Ouguiya della Mauritania
    result.put("MRO", new CurrencyDataImpl("MRO", "MRO", 0, "MRO", "MRO"));
    // Lira Maltese
    result.put("MTL", new CurrencyDataImpl("MTL", "MTL", 130, "MTL", "MTL"));
    // Sterlina Maltese
    result.put("MTP", new CurrencyDataImpl("MTP", "MTP", 130, "MTP", "MTP"));
    // Rupia Mauriziana
    result.put("MUR", new CurrencyDataImpl("MUR", "MUR", 0, "MUR", "Rs"));
    // Rufiyaa delle Maldive
    result.put("MVR", new CurrencyDataImpl("MVR", "MVR", 2, "MVR", "MVR"));
    // Kwacha Malawiano
    result.put("MWK", new CurrencyDataImpl("MWK", "MWK", 2, "MWK", "MWK"));
    // Peso Messicano
    result.put("MXN", new CurrencyDataImpl("MXN", "MX$", 2, "Mex$", "$"));
    // Peso messicano d’argento (1861-1992)
    result.put("MXP", new CurrencyDataImpl("MXP", "MXP", 130, "MXP", "MXP"));
    // Unidad de Inversion (UDI) Messicana
    result.put("MXV", new CurrencyDataImpl("MXV", "MXV", 130, "MXV", "MXV"));
    // Ringgit della Malesia
    result.put("MYR", new CurrencyDataImpl("MYR", "RM", 2, "RM", "RM"));
    // Escudo del Mozambico
    result.put("MZE", new CurrencyDataImpl("MZE", "MZE", 130, "MZE", "MZE"));
    // Metical del Mozambico
    result.put("MZN", new CurrencyDataImpl("MZN", "MZN", 2, "MZN", "MTn"));
    // Dollaro Namibiano
    result.put("NAD", new CurrencyDataImpl("NAD", "NAD", 2, "NAD", "$"));
    // Naira Nigeriana
    result.put("NGN", new CurrencyDataImpl("NGN", "NGN", 2, "NGN", "₦"));
    // Cordoba Nicaraguense
    result.put("NIC", new CurrencyDataImpl("NIC", "NIC", 130, "NIC", "NIC"));
    // Córdoba oro nicaraguense
    result.put("NIO", new CurrencyDataImpl("NIO", "NIO", 2, "NIO", "C$"));
    // Fiorino Olandese
    result.put("NLG", new CurrencyDataImpl("NLG", "NLG", 130, "NLG", "NLG"));
    // Corona Norvegese
    result.put("NOK", new CurrencyDataImpl("NOK", "NOkr", 2, "NOkr", "kr"));
    // Rupia Nepalese
    result.put("NPR", new CurrencyDataImpl("NPR", "NPR", 2, "NPR", "Rs"));
    // Dollaro Neozelandese
    result.put("NZD", new CurrencyDataImpl("NZD", "NZ$", 2, "NZ$", "$"));
    // Rial Omanita
    result.put("OMR", new CurrencyDataImpl("OMR", "OMR", 3, "OMR", "Rial"));
    // Balboa di Panama
    result.put("PAB", new CurrencyDataImpl("PAB", "B/.", 2, "B/.", "B/."));
    // Inti Peruviano
    result.put("PEI", new CurrencyDataImpl("PEI", "PEI", 130, "PEI", "PEI"));
    // Sol Nuevo Peruviano
    result.put("PEN", new CurrencyDataImpl("PEN", "S/.", 2, "S/.", "S/."));
    // Sol Peruviano
    result.put("PES", new CurrencyDataImpl("PES", "PES", 130, "PES", "PES"));
    // Kina della Papua Nuova Guinea
    result.put("PGK", new CurrencyDataImpl("PGK", "K", 2, "K", "K"));
    // Peso delle Filippine
    result.put("PHP", new CurrencyDataImpl("PHP", "PHP", 2, "PHP", "₱"));
    // Rupia del Pakistan
    result.put("PKR", new CurrencyDataImpl("PKR", "PKRs.", 0, "PKRs.", "Rs"));
    // Zloty Polacco
    result.put("PLN", new CurrencyDataImpl("PLN", "PLN", 2, "PLN", "zł"));
    // Zloty Polacco (1950-1995)
    result.put("PLZ", new CurrencyDataImpl("PLZ", "PLZ", 130, "PLZ", "PLZ"));
    // Escudo Portoghese
    result.put("PTE", new CurrencyDataImpl("PTE", "PTE", 130, "PTE", "PTE"));
    // Guarani del Paraguay
    result.put("PYG", new CurrencyDataImpl("PYG", "PYG", 0, "PYG", "Gs"));
    // Rial del Qatar
    result.put("QAR", new CurrencyDataImpl("QAR", "QAR", 2, "QAR", "Rial"));
    // Dollaro della Rhodesia
    result.put("RHD", new CurrencyDataImpl("RHD", "RHD", 130, "RHD", "RHD"));
    // Leu della Romania
    result.put("ROL", new CurrencyDataImpl("ROL", "ROL", 130, "ROL", "ROL"));
    // Leu Rumeno
    result.put("RON", new CurrencyDataImpl("RON", "L", 2, "L", "RON"));
    // Dinaro serbo
    result.put("RSD", new CurrencyDataImpl("RSD", "RSD", 0, "RSD", "din"));
    // Rublo Russo
    result.put("RUB", new CurrencyDataImpl("RUB", "руб", 2, "руб", "Rup"));
    // Rublo della CSI
    result.put("RUR", new CurrencyDataImpl("RUR", "RUR", 130, "RUR", "RUR"));
    // Franco Ruandese
    result.put("RWF", new CurrencyDataImpl("RWF", "RWF", 0, "RWF", "RF"));
    // Ryal Saudita
    result.put("SAR", new CurrencyDataImpl("SAR", "SR", 2, "SR", "Rial"));
    // Dollaro delle Isole Solomon
    result.put("SBD", new CurrencyDataImpl("SBD", "SBD", 2, "SBD", "$"));
    // Rupia delle Seychelles
    result.put("SCR", new CurrencyDataImpl("SCR", "SCR", 2, "SCR", "SCR"));
    // Dinaro Sudanese
    result.put("SDD", new CurrencyDataImpl("SDD", "SDD", 130, "SDD", "SDD"));
    // Sterlina sudanese
    result.put("SDG", new CurrencyDataImpl("SDG", "SDG", 2, "SDG", "SDG"));
    // Corona Svedese
    result.put("SEK", new CurrencyDataImpl("SEK", "kr", 2, "kr", "kr"));
    // Dollaro di Singapore
    result.put("SGD", new CurrencyDataImpl("SGD", "S$", 2, "S$", "$"));
    // Sterlina di Sant’Elena
    result.put("SHP", new CurrencyDataImpl("SHP", "SHP", 2, "SHP", "£"));
    // Tallero Sloveno
    result.put("SIT", new CurrencyDataImpl("SIT", "SIT", 130, "SIT", "SIT"));
    // Corona Slovacca
    result.put("SKK", new CurrencyDataImpl("SKK", "SKK", 130, "SKK", "SKK"));
    // Leone della Sierra Leone
    result.put("SLL", new CurrencyDataImpl("SLL", "SLL", 0, "SLL", "SLL"));
    // Scellino Somalo
    result.put("SOS", new CurrencyDataImpl("SOS", "SOS", 0, "SOS", "SOS"));
    // Dollaro surinamese
    result.put("SRD", new CurrencyDataImpl("SRD", "SRD", 2, "SRD", "$"));
    // Fiorino del Suriname
    result.put("SRG", new CurrencyDataImpl("SRG", "SRG", 130, "SRG", "SRG"));
    // Dobra di Sao Tomé e Principe
    result.put("STD", new CurrencyDataImpl("STD", "STD", 0, "STD", "Db"));
    // Rublo Sovietico
    result.put("SUR", new CurrencyDataImpl("SUR", "SUR", 130, "SUR", "SUR"));
    // Colón Salvadoregno
    result.put("SVC", new CurrencyDataImpl("SVC", "SVC", 130, "SVC", "SVC"));
    // Sterlina Siriana
    result.put("SYP", new CurrencyDataImpl("SYP", "SYP", 0, "SYP", "£"));
    // Lilangeni dello Swaziland
    result.put("SZL", new CurrencyDataImpl("SZL", "SZL", 2, "SZL", "SZL"));
    // Baht Tailandese
    result.put("THB", new CurrencyDataImpl("THB", "฿", 2, "THB", "฿"));
    // Rublo del Tajikistan
    result.put("TJR", new CurrencyDataImpl("TJR", "TJR", 130, "TJR", "TJR"));
    // Somoni del Tajikistan
    result.put("TJS", new CurrencyDataImpl("TJS", "TJS", 2, "TJS", "Som"));
    // Manat Turkmeno (1993-2009)
    result.put("TMM", new CurrencyDataImpl("TMM", "TMM", 128, "TMM", "TMM"));
    // Manat Turkmeno
    result.put("TMT", new CurrencyDataImpl("TMT", "m", 2, "m", "m"));
    // Dinaro Tunisino
    result.put("TND", new CurrencyDataImpl("TND", "TND", 3, "TND", "din"));
    // Paʻanga di Tonga
    result.put("TOP", new CurrencyDataImpl("TOP", "TOP", 2, "TOP", "T$"));
    // Escudo di Timor
    result.put("TPE", new CurrencyDataImpl("TPE", "TPE", 130, "TPE", "TPE"));
    // Lira Turca
    result.put("TRL", new CurrencyDataImpl("TRL", "TRL", 128, "TRL", "TRL"));
    // Nuova Lira Turca
    result.put("TRY", new CurrencyDataImpl("TRY", "YTL", 2, "YTL", "YTL"));
    // Dollaro di Trinidad e Tobago
    result.put("TTD", new CurrencyDataImpl("TTD", "TTD", 2, "TTD", "$"));
    // Nuovo dollaro taiwanese
    result.put("TWD", new CurrencyDataImpl("TWD", "NT$", 2, "NT$", "NT$"));
    // Scellino della Tanzania
    result.put("TZS", new CurrencyDataImpl("TZS", "TZS", 0, "TZS", "TSh"));
    // Hrivna Ucraina
    result.put("UAH", new CurrencyDataImpl("UAH", "UAH", 2, "UAH", "₴"));
    // Karbovanetz Ucraino
    result.put("UAK", new CurrencyDataImpl("UAK", "UAK", 130, "UAK", "UAK"));
    // Scellino Ugandese (1966-1987)
    result.put("UGS", new CurrencyDataImpl("UGS", "UGS", 130, "UGS", "UGS"));
    // Scellino Ugandese
    result.put("UGX", new CurrencyDataImpl("UGX", "UGX", 0, "UGX", "UGX"));
    // Dollaro Statunitense
    result.put("USD", new CurrencyDataImpl("USD", "US$", 2, "US$", "$"));
    // Dollaro Statunitense (Next day)
    result.put("USN", new CurrencyDataImpl("USN", "USN", 130, "USN", "USN"));
    // Dollaro Statunitense (Same day)
    result.put("USS", new CurrencyDataImpl("USS", "USS", 130, "USS", "USS"));
    // Peso uruguaiano in unità indicizzate
    result.put("UYI", new CurrencyDataImpl("UYI", "UYI", 130, "UYI", "UYI"));
    // Peso Uruguaiano (1975-1993)
    result.put("UYP", new CurrencyDataImpl("UYP", "UYP", 130, "UYP", "UYP"));
    // Peso Uruguayo uruguaiano
    result.put("UYU", new CurrencyDataImpl("UYU", "UY$", 2, "UY$", "$"));
    // Sum dell’Uzbekistan
    result.put("UZS", new CurrencyDataImpl("UZS", "UZS", 0, "UZS", "soʼm"));
    // Bolivar Venezuelano
    result.put("VEB", new CurrencyDataImpl("VEB", "VEB", 130, "VEB", "VEB"));
    // Bolívar venezuelano forte
    result.put("VEF", new CurrencyDataImpl("VEF", "Bs.F", 2, "Bs.F", "Bs"));
    // Dong Vietnamita
    result.put("VND", new CurrencyDataImpl("VND", "₫", 24, "₫", "₫"));
    // Vatu di Vanuatu
    result.put("VUV", new CurrencyDataImpl("VUV", "VUV", 0, "VUV", "VUV"));
    // Tala della Samoa Occidentale
    result.put("WST", new CurrencyDataImpl("WST", "WST", 2, "WST", "WST"));
    // Franco CFA BEAC
    result.put("XAF", new CurrencyDataImpl("XAF", "FCFA", 0, "FCFA", "FCFA"));
    // Argento
    result.put("XAG", new CurrencyDataImpl("XAG", "XAG", 130, "XAG", "XAG"));
    // Oro
    result.put("XAU", new CurrencyDataImpl("XAU", "XAU", 130, "XAU", "XAU"));
    // Unità composita europea
    result.put("XBA", new CurrencyDataImpl("XBA", "XBA", 130, "XBA", "XBA"));
    // Unità monetaria europea
    result.put("XBB", new CurrencyDataImpl("XBB", "XBB", 130, "XBB", "XBB"));
    // Unità di acconto europea (XBC)
    result.put("XBC", new CurrencyDataImpl("XBC", "XBC", 130, "XBC", "XBC"));
    // Unità di acconto europea (XBD)
    result.put("XBD", new CurrencyDataImpl("XBD", "XBD", 130, "XBD", "XBD"));
    // Dollaro dei Caraibi Orientali
    result.put("XCD", new CurrencyDataImpl("XCD", "EC$", 2, "EC$", "$"));
    // Diritti Speciali di Incasso
    result.put("XDR", new CurrencyDataImpl("XDR", "XDR", 130, "XDR", "XDR"));
    // Franco Oro Francese
    result.put("XFO", new CurrencyDataImpl("XFO", "XFO", 130, "XFO", "XFO"));
    // Franco UIC Francese
    result.put("XFU", new CurrencyDataImpl("XFU", "XFU", 130, "XFU", "XFU"));
    // Franco CFA BCEAO
    result.put("XOF", new CurrencyDataImpl("XOF", "CFA", 0, "CFA", "CFA"));
    // Palladio
    result.put("XPD", new CurrencyDataImpl("XPD", "XPD", 130, "XPD", "XPD"));
    // Franco CFP
    result.put("XPF", new CurrencyDataImpl("XPF", "CFPF", 0, "CFPF", "FCFP"));
    // Platino
    result.put("XPT", new CurrencyDataImpl("XPT", "XPT", 130, "XPT", "XPT"));
    // Fondi RINET
    result.put("XRE", new CurrencyDataImpl("XRE", "XRE", 130, "XRE", "XRE"));
    // Codice di verifica della valuta
    result.put("XTS", new CurrencyDataImpl("XTS", "XTS", 130, "XTS", "XTS"));
    // Nessuna valuta
    result.put("XXX", new CurrencyDataImpl("XXX", "XXX", 130, "XXX", "XXX"));
    // Dinaro dello Yemen
    result.put("YDD", new CurrencyDataImpl("YDD", "YDD", 130, "YDD", "YDD"));
    // Rial dello Yemen
    result.put("YER", new CurrencyDataImpl("YER", "YER", 0, "YER", "Rial"));
    // Dinaro Forte Yugoslavo
    result.put("YUD", new CurrencyDataImpl("YUD", "YUD", 130, "YUD", "YUD"));
    // Dinaro Noviy Yugoslavo
    result.put("YUM", new CurrencyDataImpl("YUM", "YUM", 130, "YUM", "YUM"));
    // Dinaro Convertibile Yugoslavo
    result.put("YUN", new CurrencyDataImpl("YUN", "YUN", 130, "YUN", "YUN"));
    // Rand Sudafricano (finanziario)
    result.put("ZAL", new CurrencyDataImpl("ZAL", "ZAL", 130, "ZAL", "ZAL"));
    // Rand Sudafricano
    result.put("ZAR", new CurrencyDataImpl("ZAR", "ZAR", 2, "ZAR", "R"));
    // Kwacha dello Zambia
    result.put("ZMK", new CurrencyDataImpl("ZMK", "ZMK", 0, "ZMK", "ZWK"));
    // Nuovo Zaire dello Zaire
    result.put("ZRN", new CurrencyDataImpl("ZRN", "ZRN", 130, "ZRN", "ZRN"));
    // Zaire dello Zaire
    result.put("ZRZ", new CurrencyDataImpl("ZRZ", "ZRZ", 130, "ZRZ", "ZRZ"));
    // Dollaro dello Zimbabwe
    result.put("ZWD", new CurrencyDataImpl("ZWD", "ZWD", 128, "ZWD", "ZWD"));
    // Dollaro Zimbabwiano (2009)
    result.put("ZWL", new CurrencyDataImpl("ZWL", "ZWL", 130, "ZWL", "ZWL"));
    return result;
  }
  
  @Override
  protected JavaScriptObject loadCurrencyMapNative() {
    return overrideMap(super.loadCurrencyMapNative(), loadMyCurrencyMapOverridesNative());
  }
  
  private native JavaScriptObject loadMyCurrencyMapOverridesNative() /*-{
    return {
      // Peseta Andorrana
      "ADP": [ "ADP", "ADP", 128, "ADP", "ADP"],
      // Dirham degli Emirati Arabi Uniti
      "AED": [ "AED", "DH", 2, "DH", "dh"],
      // Afgani (1927-2002)
      "AFA": [ "AFA", "AFA", 130, "AFA", "AFA"],
      // Afgani
      "AFN": [ "AFN", "AFN", 0, "AFN", "Af."],
      // Lek Albanese
      "ALL": [ "ALL", "ALL", 0, "ALL", "Lek"],
      // Dram Armeno
      "AMD": [ "AMD", "AMD", 0, "AMD", "Dram"],
      // Fiorino delle Antille Olandesi
      "ANG": [ "ANG", "ANG", 2, "ANG", "ANG"],
      // Kwanza Angolano
      "AOA": [ "AOA", "AOA", 2, "AOA", "Kz"],
      // Kwanza Angolano (1977-1990)
      "AOK": [ "AOK", "AOK", 130, "AOK", "AOK"],
      // Nuovo Kwanza Angolano (1990-2000)
      "AON": [ "AON", "AON", 130, "AON", "AON"],
      // Kwanza Reajustado Angolano (1995-1999)
      "AOR": [ "AOR", "AOR", 130, "AOR", "AOR"],
      // Austral Argentino
      "ARA": [ "ARA", "ARA", 130, "ARA", "ARA"],
      // Peso Argentino (vecchio Cod.)
      "ARP": [ "ARP", "ARP", 130, "ARP", "ARP"],
      // Peso Argentino
      "ARS": [ "ARS", "AR$", 2, "AR$", "$"],
      // Scellino Austriaco
      "ATS": [ "ATS", "ATS", 130, "ATS", "ATS"],
      // Dollaro Australiano
      "AUD": [ "AUD", "AU$", 2, "AU$", "$"],
      // Fiorino di Aruba
      "AWG": [ "AWG", "AWG", 2, "AWG", "Afl."],
      // Manat Azero (1993-2006)
      "AZM": [ "AZM", "AZM", 130, "AZM", "AZM"],
      // Manat Azero
      "AZN": [ "AZN", "AZN", 2, "AZN", "man."],
      // Dinar Bosnia-Herzegovina
      "BAD": [ "BAD", "BAD", 130, "BAD", "BAD"],
      // Marco Conv. Bosnia-Erzegovina
      "BAM": [ "BAM", "BAM", 2, "BAM", "KM"],
      // Dollaro di Barbados
      "BBD": [ "BBD", "BBD", 2, "BBD", "$"],
      // Taka Bangladese
      "BDT": [ "BDT", "Tk", 2, "Tk", "৳"],
      // Franco Belga (convertibile)
      "BEC": [ "BEC", "BEC", 130, "BEC", "BEC"],
      // Franco Belga
      "BEF": [ "BEF", "BEF", 130, "BEF", "BEF"],
      // Franco Belga (finanziario)
      "BEL": [ "BEL", "BEL", 130, "BEL", "BEL"],
      // Lev Bulgaro
      "BGL": [ "BGL", "BGL", 130, "BGL", "BGL"],
      // Nuovo Lev Bulgaro
      "BGN": [ "BGN", "Lv", 2, "Lv", "lev"],
      // Dinaro del Bahraini
      "BHD": [ "BHD", "BHD", 3, "BHD", "din"],
      // Franco del Burundi
      "BIF": [ "BIF", "BIF", 0, "BIF", "FBu"],
      // Dollaro delle Bermuda
      "BMD": [ "BMD", "BMD", 2, "BMD", "$"],
      // Dollaro del Brunei
      "BND": [ "BND", "BND", 2, "BND", "$"],
      // Boliviano
      "BOB": [ "BOB", "BOB", 2, "BOB", "Bs"],
      // Peso Boliviano
      "BOP": [ "BOP", "BOP", 130, "BOP", "BOP"],
      // Mvdol Boliviano
      "BOV": [ "BOV", "BOV", 130, "BOV", "BOV"],
      // Cruzeiro Novo Brasiliano (1967-1986)
      "BRB": [ "BRB", "BRB", 130, "BRB", "BRB"],
      // Cruzado Brasiliano
      "BRC": [ "BRC", "BRC", 130, "BRC", "BRC"],
      // Cruzeiro Brasiliano (1990-1993)
      "BRE": [ "BRE", "BRE", 130, "BRE", "BRE"],
      // Real Brasiliano
      "BRL": [ "BRL", "R$", 2, "R$", "R$"],
      // Cruzado Novo Brasiliano
      "BRN": [ "BRN", "BRN", 130, "BRN", "BRN"],
      // Cruzeiro Brasiliano
      "BRR": [ "BRR", "BRR", 130, "BRR", "BRR"],
      // Dollaro delle Bahamas
      "BSD": [ "BSD", "BSD", 2, "BSD", "$"],
      // Ngultrum Butanese
      "BTN": [ "BTN", "BTN", 2, "BTN", "Nu."],
      // Kyat Birmano
      "BUK": [ "BUK", "BUK", 130, "BUK", "BUK"],
      // Pula del Botswana
      "BWP": [ "BWP", "BWP", 2, "BWP", "P"],
      // Nuovo Rublo Bielorussia (1994-1999)
      "BYB": [ "BYB", "BYB", 130, "BYB", "BYB"],
      // Rublo Bielorussia
      "BYR": [ "BYR", "BYR", 0, "BYR", "BYR"],
      // Dollaro Belize
      "BZD": [ "BZD", "BZD", 2, "BZD", "$"],
      // Dollaro Canadese
      "CAD": [ "CAD", "CA$", 2, "C$", "$"],
      // Franco Congolese
      "CDF": [ "CDF", "CDF", 2, "CDF", "FrCD"],
      // Franco Svizzero
      "CHF": [ "CHF", "CHF", 2, "CHF", "CHF"],
      // Unidades de Fomento Chilene
      "CLF": [ "CLF", "CLF", 128, "CLF", "CLF"],
      // Peso Cileno
      "CLP": [ "CLP", "CL$", 0, "CL$", "$"],
      // Renmimbi Cinese
      "CNY": [ "CNY", "CN¥", 2, "RMB¥", "¥"],
      // Peso Colombiano
      "COP": [ "COP", "COL$", 0, "COL$", "$"],
      // Colón Costaricano
      "CRC": [ "CRC", "CR₡", 0, "CR₡", "₡"],
      // Antico Dinaro Serbo
      "CSD": [ "CSD", "CSD", 130, "CSD", "CSD"],
      // Corona forte cecoslovacca
      "CSK": [ "CSK", "CSK", 130, "CSK", "CSK"],
      // Peso cubano convertibile
      "CUC": [ "CUC", "CUC", 2, "CUC", "$"],
      // Peso Cubano
      "CUP": [ "CUP", "$MN", 2, "$MN", "$"],
      // Escudo del Capo Verde
      "CVE": [ "CVE", "CVE", 2, "CVE", "CVE"],
      // Sterlina Cipriota
      "CYP": [ "CYP", "CYP", 130, "CYP", "CYP"],
      // Corona Ceca
      "CZK": [ "CZK", "Kč", 2, "Kč", "Kč"],
      // Ostmark della Germania Orientale
      "DDM": [ "DDM", "DDM", 130, "DDM", "DDM"],
      // Marco Tedesco
      "DEM": [ "DEM", "DEM", 130, "DEM", "DEM"],
      // Franco Gibutiano
      "DJF": [ "DJF", "Fdj", 0, "Fdj", "Fdj"],
      // Corona Danese
      "DKK": [ "DKK", "kr", 2, "kr", "kr"],
      // Peso Dominicano
      "DOP": [ "DOP", "RD$", 2, "RD$", "$"],
      // Dinaro Algerino
      "DZD": [ "DZD", "DZD", 2, "DZD", "din"],
      // Sucre dell’Ecuador
      "ECS": [ "ECS", "ECS", 130, "ECS", "ECS"],
      // Unidad de Valor Constante (UVC) dell’Ecuador
      "ECV": [ "ECV", "ECV", 130, "ECV", "ECV"],
      // Corona dell’Estonia
      "EEK": [ "EEK", "EEK", 130, "EEK", "EEK"],
      // Sterlina Egiziana
      "EGP": [ "EGP", "LE", 2, "LE", "E£"],
      // Nakfa Eritreo
      "ERN": [ "ERN", "ERN", 2, "ERN", "Nfk"],
      // Peseta Spagnola Account
      "ESA": [ "ESA", "ESA", 130, "ESA", "ESA"],
      // Peseta Spagnola Account Convertibile
      "ESB": [ "ESB", "ESB", 130, "ESB", "ESB"],
      // Peseta Spagnola
      "ESP": [ "ESP", "ESP", 128, "ESP", "ESP"],
      // Birr Etiopico
      "ETB": [ "ETB", "ETB", 2, "ETB", "Birr"],
      // Euro
      "EUR": [ "EUR", "€", 2, "€", "€"],
      // Markka Finlandese
      "FIM": [ "FIM", "FIM", 130, "FIM", "FIM"],
      // Dollaro delle Figi
      "FJD": [ "FJD", "FJD", 2, "FJD", "$"],
      // Sterlina delle Falkland
      "FKP": [ "FKP", "FKP", 2, "FKP", "£"],
      // Franco Francese
      "FRF": [ "FRF", "FRF", 130, "FRF", "FRF"],
      // Sterlina Inglese
      "GBP": [ "GBP", "UK£", 2, "GB£", "£"],
      // Kupon Larit Georgiano
      "GEK": [ "GEK", "GEK", 130, "GEK", "GEK"],
      // Lari Georgiano
      "GEL": [ "GEL", "GEL", 2, "GEL", "GEL"],
      // Cedi del Ghana
      "GHC": [ "GHC", "GHC", 130, "GHC", "GHC"],
      // Cedi ghanese
      "GHS": [ "GHS", "GHS", 2, "GHS", "GHS"],
      // Sterlina di Gibilterra
      "GIP": [ "GIP", "GIP", 2, "GIP", "£"],
      // Dalasi del Gambia
      "GMD": [ "GMD", "GMD", 2, "GMD", "GMD"],
      // Franco della Guinea
      "GNF": [ "GNF", "GNF", 0, "GNF", "FG"],
      // Syli della Guinea
      "GNS": [ "GNS", "GNS", 130, "GNS", "GNS"],
      // Ekwele della Guinea Equatoriale
      "GQE": [ "GQE", "GQE", 130, "GQE", "GQE"],
      // Dracma Greca
      "GRD": [ "GRD", "GRD", 130, "GRD", "GRD"],
      // Quetzal Guatemalteco
      "GTQ": [ "GTQ", "GTQ", 2, "GTQ", "Q"],
      // Escudo della Guinea portoghese
      "GWE": [ "GWE", "GWE", 130, "GWE", "GWE"],
      // Peso della Guinea-Bissau
      "GWP": [ "GWP", "GWP", 130, "GWP", "GWP"],
      // Dollaro della Guyana
      "GYD": [ "GYD", "GYD", 0, "GYD", "$"],
      // Dollaro di Hong Kong
      "HKD": [ "HKD", "HK$", 2, "HK$", "$"],
      // Lempira Hoduregno
      "HNL": [ "HNL", "L", 2, "L", "L"],
      // Dinaro Croato
      "HRD": [ "HRD", "HRD", 130, "HRD", "HRD"],
      // Kuna Croata
      "HRK": [ "HRK", "HRK", 2, "HRK", "kn"],
      // Gourde Haitiano
      "HTG": [ "HTG", "HTG", 2, "HTG", "HTG"],
      // Fiorino Ungherese
      "HUF": [ "HUF", "HUF", 0, "HUF", "Ft"],
      // Rupia Indonesiana
      "IDR": [ "IDR", "IDR", 0, "IDR", "Rp"],
      // Sterlina irlandese
      "IEP": [ "IEP", "IEP", 130, "IEP", "IEP"],
      // Sterlina Israeliana
      "ILP": [ "ILP", "ILP", 130, "ILP", "ILP"],
      // Nuovo sheqel israeliano
      "ILS": [ "ILS", "₪", 2, "IL₪", "₪"],
      // Rupia Indiana
      "INR": [ "INR", "Rs.", 2, "Rs", "₹"],
      // Dinaro Iracheno
      "IQD": [ "IQD", "IQD", 0, "IQD", "din"],
      // Rial Iraniano
      "IRR": [ "IRR", "IRR", 0, "IRR", "Rial"],
      // Corona Islandese
      "ISK": [ "ISK", "Kr", 0, "kr", "kr"],
      // Lira Italiana
      "ITL": [ "ITL", "ITL", 128, "ITL", "ITL"],
      // Dollaro Giamaicano
      "JMD": [ "JMD", "JA$", 2, "JA$", "$"],
      // Dinaro Giordano
      "JOD": [ "JOD", "JOD", 3, "JOD", "din"],
      // Yen Giapponese
      "JPY": [ "JPY", "JP¥", 0, "JP¥", "¥"],
      // Scellino Keniota
      "KES": [ "KES", "Ksh", 2, "Ksh", "Ksh"],
      // Som Kirghiso
      "KGS": [ "KGS", "KGS", 2, "KGS", "KGS"],
      // Riel Cambogiano
      "KHR": [ "KHR", "KHR", 2, "KHR", "Riel"],
      // Franco Comoriano
      "KMF": [ "KMF", "KMF", 0, "KMF", "CF"],
      // Won Nordcoreano
      "KPW": [ "KPW", "KPW", 0, "KPW", "₩"],
      // Won Sudcoreano
      "KRW": [ "KRW", "₩", 0, "KR₩", "₩"],
      // Dinaro Kuwaitiano
      "KWD": [ "KWD", "KWD", 3, "KWD", "din"],
      // Dollaro delle Isole Cayman
      "KYD": [ "KYD", "KYD", 2, "KYD", "$"],
      // Tenge Kazaco
      "KZT": [ "KZT", "KZT", 2, "KZT", "₸"],
      // Kip Laotiano
      "LAK": [ "LAK", "LAK", 0, "LAK", "₭"],
      // Sterlina Libanese
      "LBP": [ "LBP", "LBP", 0, "LBP", "L£"],
      // Rupia di Sri Lanka
      "LKR": [ "LKR", "SLRs", 2, "SLRs", "Rs"],
      // Dollaro Liberiano
      "LRD": [ "LRD", "LRD", 2, "LRD", "$"],
      // Loti del Lesotho
      "LSL": [ "LSL", "LSL", 2, "LSL", "LSL"],
      // Lita Lituana
      "LTL": [ "LTL", "LTL", 2, "LTL", "Lt"],
      // Talonas Lituani
      "LTT": [ "LTT", "LTT", 130, "LTT", "LTT"],
      // Franco Convertibile del Lussemburgo
      "LUC": [ "LUC", "LUC", 130, "LUC", "LUC"],
      // Franco del Lussemburgo
      "LUF": [ "LUF", "LUF", 128, "LUF", "LUF"],
      // Franco Finanziario del Lussemburgo
      "LUL": [ "LUL", "LUL", 130, "LUL", "LUL"],
      // Lat Lettone
      "LVL": [ "LVL", "LVL", 2, "LVL", "Ls"],
      // Rublo Lettone
      "LVR": [ "LVR", "LVR", 130, "LVR", "LVR"],
      // Dinaro Libico
      "LYD": [ "LYD", "LYD", 3, "LYD", "din"],
      // Dirham Marocchino
      "MAD": [ "MAD", "MAD", 2, "MAD", "MAD"],
      // Franco Marocchino
      "MAF": [ "MAF", "MAF", 130, "MAF", "MAF"],
      // Leu Moldavo
      "MDL": [ "MDL", "MDL", 2, "MDL", "MDL"],
      // Ariary Malgascio
      "MGA": [ "MGA", "MGA", 0, "MGA", "Ar"],
      // Franco Malgascio
      "MGF": [ "MGF", "MGF", 128, "MGF", "MGF"],
      // Dinaro Macedone
      "MKD": [ "MKD", "MKD", 2, "MKD", "din"],
      // Franco di Mali
      "MLF": [ "MLF", "MLF", 130, "MLF", "MLF"],
      // Kyat di Myanmar
      "MMK": [ "MMK", "MMK", 0, "MMK", "K"],
      // Tugrik Mongolo
      "MNT": [ "MNT", "MN₮", 0, "MN₮", "₮"],
      // Pataca di Macao
      "MOP": [ "MOP", "MOP", 2, "MOP", "MOP"],
      // Ouguiya della Mauritania
      "MRO": [ "MRO", "MRO", 0, "MRO", "MRO"],
      // Lira Maltese
      "MTL": [ "MTL", "MTL", 130, "MTL", "MTL"],
      // Sterlina Maltese
      "MTP": [ "MTP", "MTP", 130, "MTP", "MTP"],
      // Rupia Mauriziana
      "MUR": [ "MUR", "MUR", 0, "MUR", "Rs"],
      // Rufiyaa delle Maldive
      "MVR": [ "MVR", "MVR", 2, "MVR", "MVR"],
      // Kwacha Malawiano
      "MWK": [ "MWK", "MWK", 2, "MWK", "MWK"],
      // Peso Messicano
      "MXN": [ "MXN", "MX$", 2, "Mex$", "$"],
      // Peso messicano d’argento (1861-1992)
      "MXP": [ "MXP", "MXP", 130, "MXP", "MXP"],
      // Unidad de Inversion (UDI) Messicana
      "MXV": [ "MXV", "MXV", 130, "MXV", "MXV"],
      // Ringgit della Malesia
      "MYR": [ "MYR", "RM", 2, "RM", "RM"],
      // Escudo del Mozambico
      "MZE": [ "MZE", "MZE", 130, "MZE", "MZE"],
      // Metical del Mozambico
      "MZN": [ "MZN", "MZN", 2, "MZN", "MTn"],
      // Dollaro Namibiano
      "NAD": [ "NAD", "NAD", 2, "NAD", "$"],
      // Naira Nigeriana
      "NGN": [ "NGN", "NGN", 2, "NGN", "₦"],
      // Cordoba Nicaraguense
      "NIC": [ "NIC", "NIC", 130, "NIC", "NIC"],
      // Córdoba oro nicaraguense
      "NIO": [ "NIO", "NIO", 2, "NIO", "C$"],
      // Fiorino Olandese
      "NLG": [ "NLG", "NLG", 130, "NLG", "NLG"],
      // Corona Norvegese
      "NOK": [ "NOK", "NOkr", 2, "NOkr", "kr"],
      // Rupia Nepalese
      "NPR": [ "NPR", "NPR", 2, "NPR", "Rs"],
      // Dollaro Neozelandese
      "NZD": [ "NZD", "NZ$", 2, "NZ$", "$"],
      // Rial Omanita
      "OMR": [ "OMR", "OMR", 3, "OMR", "Rial"],
      // Balboa di Panama
      "PAB": [ "PAB", "B/.", 2, "B/.", "B/."],
      // Inti Peruviano
      "PEI": [ "PEI", "PEI", 130, "PEI", "PEI"],
      // Sol Nuevo Peruviano
      "PEN": [ "PEN", "S/.", 2, "S/.", "S/."],
      // Sol Peruviano
      "PES": [ "PES", "PES", 130, "PES", "PES"],
      // Kina della Papua Nuova Guinea
      "PGK": [ "PGK", "K", 2, "K", "K"],
      // Peso delle Filippine
      "PHP": [ "PHP", "PHP", 2, "PHP", "₱"],
      // Rupia del Pakistan
      "PKR": [ "PKR", "PKRs.", 0, "PKRs.", "Rs"],
      // Zloty Polacco
      "PLN": [ "PLN", "PLN", 2, "PLN", "zł"],
      // Zloty Polacco (1950-1995)
      "PLZ": [ "PLZ", "PLZ", 130, "PLZ", "PLZ"],
      // Escudo Portoghese
      "PTE": [ "PTE", "PTE", 130, "PTE", "PTE"],
      // Guarani del Paraguay
      "PYG": [ "PYG", "PYG", 0, "PYG", "Gs"],
      // Rial del Qatar
      "QAR": [ "QAR", "QAR", 2, "QAR", "Rial"],
      // Dollaro della Rhodesia
      "RHD": [ "RHD", "RHD", 130, "RHD", "RHD"],
      // Leu della Romania
      "ROL": [ "ROL", "ROL", 130, "ROL", "ROL"],
      // Leu Rumeno
      "RON": [ "RON", "L", 2, "L", "RON"],
      // Dinaro serbo
      "RSD": [ "RSD", "RSD", 0, "RSD", "din"],
      // Rublo Russo
      "RUB": [ "RUB", "руб", 2, "руб", "Rup"],
      // Rublo della CSI
      "RUR": [ "RUR", "RUR", 130, "RUR", "RUR"],
      // Franco Ruandese
      "RWF": [ "RWF", "RWF", 0, "RWF", "RF"],
      // Ryal Saudita
      "SAR": [ "SAR", "SR", 2, "SR", "Rial"],
      // Dollaro delle Isole Solomon
      "SBD": [ "SBD", "SBD", 2, "SBD", "$"],
      // Rupia delle Seychelles
      "SCR": [ "SCR", "SCR", 2, "SCR", "SCR"],
      // Dinaro Sudanese
      "SDD": [ "SDD", "SDD", 130, "SDD", "SDD"],
      // Sterlina sudanese
      "SDG": [ "SDG", "SDG", 2, "SDG", "SDG"],
      // Corona Svedese
      "SEK": [ "SEK", "kr", 2, "kr", "kr"],
      // Dollaro di Singapore
      "SGD": [ "SGD", "S$", 2, "S$", "$"],
      // Sterlina di Sant’Elena
      "SHP": [ "SHP", "SHP", 2, "SHP", "£"],
      // Tallero Sloveno
      "SIT": [ "SIT", "SIT", 130, "SIT", "SIT"],
      // Corona Slovacca
      "SKK": [ "SKK", "SKK", 130, "SKK", "SKK"],
      // Leone della Sierra Leone
      "SLL": [ "SLL", "SLL", 0, "SLL", "SLL"],
      // Scellino Somalo
      "SOS": [ "SOS", "SOS", 0, "SOS", "SOS"],
      // Dollaro surinamese
      "SRD": [ "SRD", "SRD", 2, "SRD", "$"],
      // Fiorino del Suriname
      "SRG": [ "SRG", "SRG", 130, "SRG", "SRG"],
      // Dobra di Sao Tomé e Principe
      "STD": [ "STD", "STD", 0, "STD", "Db"],
      // Rublo Sovietico
      "SUR": [ "SUR", "SUR", 130, "SUR", "SUR"],
      // Colón Salvadoregno
      "SVC": [ "SVC", "SVC", 130, "SVC", "SVC"],
      // Sterlina Siriana
      "SYP": [ "SYP", "SYP", 0, "SYP", "£"],
      // Lilangeni dello Swaziland
      "SZL": [ "SZL", "SZL", 2, "SZL", "SZL"],
      // Baht Tailandese
      "THB": [ "THB", "฿", 2, "THB", "฿"],
      // Rublo del Tajikistan
      "TJR": [ "TJR", "TJR", 130, "TJR", "TJR"],
      // Somoni del Tajikistan
      "TJS": [ "TJS", "TJS", 2, "TJS", "Som"],
      // Manat Turkmeno (1993-2009)
      "TMM": [ "TMM", "TMM", 128, "TMM", "TMM"],
      // Manat Turkmeno
      "TMT": [ "TMT", "m", 2, "m", "m"],
      // Dinaro Tunisino
      "TND": [ "TND", "TND", 3, "TND", "din"],
      // Paʻanga di Tonga
      "TOP": [ "TOP", "TOP", 2, "TOP", "T$"],
      // Escudo di Timor
      "TPE": [ "TPE", "TPE", 130, "TPE", "TPE"],
      // Lira Turca
      "TRL": [ "TRL", "TRL", 128, "TRL", "TRL"],
      // Nuova Lira Turca
      "TRY": [ "TRY", "YTL", 2, "YTL", "YTL"],
      // Dollaro di Trinidad e Tobago
      "TTD": [ "TTD", "TTD", 2, "TTD", "$"],
      // Nuovo dollaro taiwanese
      "TWD": [ "TWD", "NT$", 2, "NT$", "NT$"],
      // Scellino della Tanzania
      "TZS": [ "TZS", "TZS", 0, "TZS", "TSh"],
      // Hrivna Ucraina
      "UAH": [ "UAH", "UAH", 2, "UAH", "₴"],
      // Karbovanetz Ucraino
      "UAK": [ "UAK", "UAK", 130, "UAK", "UAK"],
      // Scellino Ugandese (1966-1987)
      "UGS": [ "UGS", "UGS", 130, "UGS", "UGS"],
      // Scellino Ugandese
      "UGX": [ "UGX", "UGX", 0, "UGX", "UGX"],
      // Dollaro Statunitense
      "USD": [ "USD", "US$", 2, "US$", "$"],
      // Dollaro Statunitense (Next day)
      "USN": [ "USN", "USN", 130, "USN", "USN"],
      // Dollaro Statunitense (Same day)
      "USS": [ "USS", "USS", 130, "USS", "USS"],
      // Peso uruguaiano in unità indicizzate
      "UYI": [ "UYI", "UYI", 130, "UYI", "UYI"],
      // Peso Uruguaiano (1975-1993)
      "UYP": [ "UYP", "UYP", 130, "UYP", "UYP"],
      // Peso Uruguayo uruguaiano
      "UYU": [ "UYU", "UY$", 2, "UY$", "$"],
      // Sum dell’Uzbekistan
      "UZS": [ "UZS", "UZS", 0, "UZS", "soʼm"],
      // Bolivar Venezuelano
      "VEB": [ "VEB", "VEB", 130, "VEB", "VEB"],
      // Bolívar venezuelano forte
      "VEF": [ "VEF", "Bs.F", 2, "Bs.F", "Bs"],
      // Dong Vietnamita
      "VND": [ "VND", "₫", 24, "₫", "₫"],
      // Vatu di Vanuatu
      "VUV": [ "VUV", "VUV", 0, "VUV", "VUV"],
      // Tala della Samoa Occidentale
      "WST": [ "WST", "WST", 2, "WST", "WST"],
      // Franco CFA BEAC
      "XAF": [ "XAF", "FCFA", 0, "FCFA", "FCFA"],
      // Argento
      "XAG": [ "XAG", "XAG", 130, "XAG", "XAG"],
      // Oro
      "XAU": [ "XAU", "XAU", 130, "XAU", "XAU"],
      // Unità composita europea
      "XBA": [ "XBA", "XBA", 130, "XBA", "XBA"],
      // Unità monetaria europea
      "XBB": [ "XBB", "XBB", 130, "XBB", "XBB"],
      // Unità di acconto europea (XBC)
      "XBC": [ "XBC", "XBC", 130, "XBC", "XBC"],
      // Unità di acconto europea (XBD)
      "XBD": [ "XBD", "XBD", 130, "XBD", "XBD"],
      // Dollaro dei Caraibi Orientali
      "XCD": [ "XCD", "EC$", 2, "EC$", "$"],
      // Diritti Speciali di Incasso
      "XDR": [ "XDR", "XDR", 130, "XDR", "XDR"],
      // Franco Oro Francese
      "XFO": [ "XFO", "XFO", 130, "XFO", "XFO"],
      // Franco UIC Francese
      "XFU": [ "XFU", "XFU", 130, "XFU", "XFU"],
      // Franco CFA BCEAO
      "XOF": [ "XOF", "CFA", 0, "CFA", "CFA"],
      // Palladio
      "XPD": [ "XPD", "XPD", 130, "XPD", "XPD"],
      // Franco CFP
      "XPF": [ "XPF", "CFPF", 0, "CFPF", "FCFP"],
      // Platino
      "XPT": [ "XPT", "XPT", 130, "XPT", "XPT"],
      // Fondi RINET
      "XRE": [ "XRE", "XRE", 130, "XRE", "XRE"],
      // Codice di verifica della valuta
      "XTS": [ "XTS", "XTS", 130, "XTS", "XTS"],
      // Nessuna valuta
      "XXX": [ "XXX", "XXX", 130, "XXX", "XXX"],
      // Dinaro dello Yemen
      "YDD": [ "YDD", "YDD", 130, "YDD", "YDD"],
      // Rial dello Yemen
      "YER": [ "YER", "YER", 0, "YER", "Rial"],
      // Dinaro Forte Yugoslavo
      "YUD": [ "YUD", "YUD", 130, "YUD", "YUD"],
      // Dinaro Noviy Yugoslavo
      "YUM": [ "YUM", "YUM", 130, "YUM", "YUM"],
      // Dinaro Convertibile Yugoslavo
      "YUN": [ "YUN", "YUN", 130, "YUN", "YUN"],
      // Rand Sudafricano (finanziario)
      "ZAL": [ "ZAL", "ZAL", 130, "ZAL", "ZAL"],
      // Rand Sudafricano
      "ZAR": [ "ZAR", "ZAR", 2, "ZAR", "R"],
      // Kwacha dello Zambia
      "ZMK": [ "ZMK", "ZMK", 0, "ZMK", "ZWK"],
      // Nuovo Zaire dello Zaire
      "ZRN": [ "ZRN", "ZRN", 130, "ZRN", "ZRN"],
      // Zaire dello Zaire
      "ZRZ": [ "ZRZ", "ZRZ", 130, "ZRZ", "ZRZ"],
      // Dollaro dello Zimbabwe
      "ZWD": [ "ZWD", "ZWD", 128, "ZWD", "ZWD"],
      // Dollaro Zimbabwiano (2009)
      "ZWL": [ "ZWL", "ZWL", 130, "ZWL", "ZWL"],
    };
  }-*/;
  
  @Override
  protected HashMap<String, String> loadNamesMapJava() {
    HashMap<String, String> result = super.loadNamesMapJava();
    result.put("ADP", "Peseta Andorrana");
    result.put("AED", "Dirham degli Emirati Arabi Uniti");
    result.put("AFA", "Afgani (1927-2002)");
    result.put("AFN", "Afgani");
    result.put("ALL", "Lek Albanese");
    result.put("AMD", "Dram Armeno");
    result.put("ANG", "Fiorino delle Antille Olandesi");
    result.put("AOA", "Kwanza Angolano");
    result.put("AOK", "Kwanza Angolano (1977-1990)");
    result.put("AON", "Nuovo Kwanza Angolano (1990-2000)");
    result.put("AOR", "Kwanza Reajustado Angolano (1995-1999)");
    result.put("ARA", "Austral Argentino");
    result.put("ARP", "Peso Argentino (vecchio Cod.)");
    result.put("ARS", "Peso Argentino");
    result.put("ATS", "Scellino Austriaco");
    result.put("AUD", "Dollaro Australiano");
    result.put("AWG", "Fiorino di Aruba");
    result.put("AZM", "Manat Azero (1993-2006)");
    result.put("AZN", "Manat Azero");
    result.put("BAD", "Dinar Bosnia-Herzegovina");
    result.put("BAM", "Marco Conv. Bosnia-Erzegovina");
    result.put("BBD", "Dollaro di Barbados");
    result.put("BDT", "Taka Bangladese");
    result.put("BEC", "Franco Belga (convertibile)");
    result.put("BEF", "Franco Belga");
    result.put("BEL", "Franco Belga (finanziario)");
    result.put("BGL", "Lev Bulgaro");
    result.put("BGN", "Nuovo Lev Bulgaro");
    result.put("BHD", "Dinaro del Bahraini");
    result.put("BIF", "Franco del Burundi");
    result.put("BMD", "Dollaro delle Bermuda");
    result.put("BND", "Dollaro del Brunei");
    result.put("BOB", "Boliviano");
    result.put("BOP", "Peso Boliviano");
    result.put("BOV", "Mvdol Boliviano");
    result.put("BRB", "Cruzeiro Novo Brasiliano (1967-1986)");
    result.put("BRC", "Cruzado Brasiliano");
    result.put("BRE", "Cruzeiro Brasiliano (1990-1993)");
    result.put("BRL", "Real Brasiliano");
    result.put("BRN", "Cruzado Novo Brasiliano");
    result.put("BRR", "Cruzeiro Brasiliano");
    result.put("BSD", "Dollaro delle Bahamas");
    result.put("BTN", "Ngultrum Butanese");
    result.put("BUK", "Kyat Birmano");
    result.put("BWP", "Pula del Botswana");
    result.put("BYB", "Nuovo Rublo Bielorussia (1994-1999)");
    result.put("BYR", "Rublo Bielorussia");
    result.put("BZD", "Dollaro Belize");
    result.put("CAD", "Dollaro Canadese");
    result.put("CDF", "Franco Congolese");
    result.put("CHF", "Franco Svizzero");
    result.put("CLF", "Unidades de Fomento Chilene");
    result.put("CLP", "Peso Cileno");
    result.put("CNY", "Renmimbi Cinese");
    result.put("COP", "Peso Colombiano");
    result.put("CRC", "Colón Costaricano");
    result.put("CSD", "Antico Dinaro Serbo");
    result.put("CSK", "Corona forte cecoslovacca");
    result.put("CUC", "Peso cubano convertibile");
    result.put("CUP", "Peso Cubano");
    result.put("CVE", "Escudo del Capo Verde");
    result.put("CYP", "Sterlina Cipriota");
    result.put("CZK", "Corona Ceca");
    result.put("DDM", "Ostmark della Germania Orientale");
    result.put("DEM", "Marco Tedesco");
    result.put("DJF", "Franco Gibutiano");
    result.put("DKK", "Corona Danese");
    result.put("DOP", "Peso Dominicano");
    result.put("DZD", "Dinaro Algerino");
    result.put("ECS", "Sucre dell’Ecuador");
    result.put("ECV", "Unidad de Valor Constante (UVC) dell’Ecuador");
    result.put("EEK", "Corona dell’Estonia");
    result.put("EGP", "Sterlina Egiziana");
    result.put("ERN", "Nakfa Eritreo");
    result.put("ESA", "Peseta Spagnola Account");
    result.put("ESB", "Peseta Spagnola Account Convertibile");
    result.put("ESP", "Peseta Spagnola");
    result.put("ETB", "Birr Etiopico");
    result.put("EUR", "Euro");
    result.put("FIM", "Markka Finlandese");
    result.put("FJD", "Dollaro delle Figi");
    result.put("FKP", "Sterlina delle Falkland");
    result.put("FRF", "Franco Francese");
    result.put("GBP", "Sterlina Inglese");
    result.put("GEK", "Kupon Larit Georgiano");
    result.put("GEL", "Lari Georgiano");
    result.put("GHC", "Cedi del Ghana");
    result.put("GHS", "Cedi ghanese");
    result.put("GIP", "Sterlina di Gibilterra");
    result.put("GMD", "Dalasi del Gambia");
    result.put("GNF", "Franco della Guinea");
    result.put("GNS", "Syli della Guinea");
    result.put("GQE", "Ekwele della Guinea Equatoriale");
    result.put("GRD", "Dracma Greca");
    result.put("GTQ", "Quetzal Guatemalteco");
    result.put("GWE", "Escudo della Guinea portoghese");
    result.put("GWP", "Peso della Guinea-Bissau");
    result.put("GYD", "Dollaro della Guyana");
    result.put("HKD", "Dollaro di Hong Kong");
    result.put("HNL", "Lempira Hoduregno");
    result.put("HRD", "Dinaro Croato");
    result.put("HRK", "Kuna Croata");
    result.put("HTG", "Gourde Haitiano");
    result.put("HUF", "Fiorino Ungherese");
    result.put("IDR", "Rupia Indonesiana");
    result.put("IEP", "Sterlina irlandese");
    result.put("ILP", "Sterlina Israeliana");
    result.put("ILS", "Nuovo sheqel israeliano");
    result.put("INR", "Rupia Indiana");
    result.put("IQD", "Dinaro Iracheno");
    result.put("IRR", "Rial Iraniano");
    result.put("ISK", "Corona Islandese");
    result.put("ITL", "Lira Italiana");
    result.put("JMD", "Dollaro Giamaicano");
    result.put("JOD", "Dinaro Giordano");
    result.put("JPY", "Yen Giapponese");
    result.put("KES", "Scellino Keniota");
    result.put("KGS", "Som Kirghiso");
    result.put("KHR", "Riel Cambogiano");
    result.put("KMF", "Franco Comoriano");
    result.put("KPW", "Won Nordcoreano");
    result.put("KRW", "Won Sudcoreano");
    result.put("KWD", "Dinaro Kuwaitiano");
    result.put("KYD", "Dollaro delle Isole Cayman");
    result.put("KZT", "Tenge Kazaco");
    result.put("LAK", "Kip Laotiano");
    result.put("LBP", "Sterlina Libanese");
    result.put("LKR", "Rupia di Sri Lanka");
    result.put("LRD", "Dollaro Liberiano");
    result.put("LSL", "Loti del Lesotho");
    result.put("LTL", "Lita Lituana");
    result.put("LTT", "Talonas Lituani");
    result.put("LUC", "Franco Convertibile del Lussemburgo");
    result.put("LUF", "Franco del Lussemburgo");
    result.put("LUL", "Franco Finanziario del Lussemburgo");
    result.put("LVL", "Lat Lettone");
    result.put("LVR", "Rublo Lettone");
    result.put("LYD", "Dinaro Libico");
    result.put("MAD", "Dirham Marocchino");
    result.put("MAF", "Franco Marocchino");
    result.put("MDL", "Leu Moldavo");
    result.put("MGA", "Ariary Malgascio");
    result.put("MGF", "Franco Malgascio");
    result.put("MKD", "Dinaro Macedone");
    result.put("MLF", "Franco di Mali");
    result.put("MMK", "Kyat di Myanmar");
    result.put("MNT", "Tugrik Mongolo");
    result.put("MOP", "Pataca di Macao");
    result.put("MRO", "Ouguiya della Mauritania");
    result.put("MTL", "Lira Maltese");
    result.put("MTP", "Sterlina Maltese");
    result.put("MUR", "Rupia Mauriziana");
    result.put("MVR", "Rufiyaa delle Maldive");
    result.put("MWK", "Kwacha Malawiano");
    result.put("MXN", "Peso Messicano");
    result.put("MXP", "Peso messicano d’argento (1861-1992)");
    result.put("MXV", "Unidad de Inversion (UDI) Messicana");
    result.put("MYR", "Ringgit della Malesia");
    result.put("MZE", "Escudo del Mozambico");
    result.put("MZN", "Metical del Mozambico");
    result.put("NAD", "Dollaro Namibiano");
    result.put("NGN", "Naira Nigeriana");
    result.put("NIC", "Cordoba Nicaraguense");
    result.put("NIO", "Córdoba oro nicaraguense");
    result.put("NLG", "Fiorino Olandese");
    result.put("NOK", "Corona Norvegese");
    result.put("NPR", "Rupia Nepalese");
    result.put("NZD", "Dollaro Neozelandese");
    result.put("OMR", "Rial Omanita");
    result.put("PAB", "Balboa di Panama");
    result.put("PEI", "Inti Peruviano");
    result.put("PEN", "Sol Nuevo Peruviano");
    result.put("PES", "Sol Peruviano");
    result.put("PGK", "Kina della Papua Nuova Guinea");
    result.put("PHP", "Peso delle Filippine");
    result.put("PKR", "Rupia del Pakistan");
    result.put("PLN", "Zloty Polacco");
    result.put("PLZ", "Zloty Polacco (1950-1995)");
    result.put("PTE", "Escudo Portoghese");
    result.put("PYG", "Guarani del Paraguay");
    result.put("QAR", "Rial del Qatar");
    result.put("RHD", "Dollaro della Rhodesia");
    result.put("ROL", "Leu della Romania");
    result.put("RON", "Leu Rumeno");
    result.put("RSD", "Dinaro serbo");
    result.put("RUB", "Rublo Russo");
    result.put("RUR", "Rublo della CSI");
    result.put("RWF", "Franco Ruandese");
    result.put("SAR", "Ryal Saudita");
    result.put("SBD", "Dollaro delle Isole Solomon");
    result.put("SCR", "Rupia delle Seychelles");
    result.put("SDD", "Dinaro Sudanese");
    result.put("SDG", "Sterlina sudanese");
    result.put("SEK", "Corona Svedese");
    result.put("SGD", "Dollaro di Singapore");
    result.put("SHP", "Sterlina di Sant’Elena");
    result.put("SIT", "Tallero Sloveno");
    result.put("SKK", "Corona Slovacca");
    result.put("SLL", "Leone della Sierra Leone");
    result.put("SOS", "Scellino Somalo");
    result.put("SRD", "Dollaro surinamese");
    result.put("SRG", "Fiorino del Suriname");
    result.put("STD", "Dobra di Sao Tomé e Principe");
    result.put("SUR", "Rublo Sovietico");
    result.put("SVC", "Colón Salvadoregno");
    result.put("SYP", "Sterlina Siriana");
    result.put("SZL", "Lilangeni dello Swaziland");
    result.put("THB", "Baht Tailandese");
    result.put("TJR", "Rublo del Tajikistan");
    result.put("TJS", "Somoni del Tajikistan");
    result.put("TMM", "Manat Turkmeno (1993-2009)");
    result.put("TMT", "Manat Turkmeno");
    result.put("TND", "Dinaro Tunisino");
    result.put("TOP", "Paʻanga di Tonga");
    result.put("TPE", "Escudo di Timor");
    result.put("TRL", "Lira Turca");
    result.put("TRY", "Nuova Lira Turca");
    result.put("TTD", "Dollaro di Trinidad e Tobago");
    result.put("TWD", "Nuovo dollaro taiwanese");
    result.put("TZS", "Scellino della Tanzania");
    result.put("UAH", "Hrivna Ucraina");
    result.put("UAK", "Karbovanetz Ucraino");
    result.put("UGS", "Scellino Ugandese (1966-1987)");
    result.put("UGX", "Scellino Ugandese");
    result.put("USD", "Dollaro Statunitense");
    result.put("USN", "Dollaro Statunitense (Next day)");
    result.put("USS", "Dollaro Statunitense (Same day)");
    result.put("UYI", "Peso uruguaiano in unità indicizzate");
    result.put("UYP", "Peso Uruguaiano (1975-1993)");
    result.put("UYU", "Peso Uruguayo uruguaiano");
    result.put("UZS", "Sum dell’Uzbekistan");
    result.put("VEB", "Bolivar Venezuelano");
    result.put("VEF", "Bolívar venezuelano forte");
    result.put("VND", "Dong Vietnamita");
    result.put("VUV", "Vatu di Vanuatu");
    result.put("WST", "Tala della Samoa Occidentale");
    result.put("XAF", "Franco CFA BEAC");
    result.put("XAG", "Argento");
    result.put("XAU", "Oro");
    result.put("XBA", "Unità composita europea");
    result.put("XBB", "Unità monetaria europea");
    result.put("XBC", "Unità di acconto europea (XBC)");
    result.put("XBD", "Unità di acconto europea (XBD)");
    result.put("XCD", "Dollaro dei Caraibi Orientali");
    result.put("XDR", "Diritti Speciali di Incasso");
    result.put("XFO", "Franco Oro Francese");
    result.put("XFU", "Franco UIC Francese");
    result.put("XOF", "Franco CFA BCEAO");
    result.put("XPD", "Palladio");
    result.put("XPF", "Franco CFP");
    result.put("XPT", "Platino");
    result.put("XRE", "Fondi RINET");
    result.put("XTS", "Codice di verifica della valuta");
    result.put("XXX", "Nessuna valuta");
    result.put("YDD", "Dinaro dello Yemen");
    result.put("YER", "Rial dello Yemen");
    result.put("YUD", "Dinaro Forte Yugoslavo");
    result.put("YUM", "Dinaro Noviy Yugoslavo");
    result.put("YUN", "Dinaro Convertibile Yugoslavo");
    result.put("ZAL", "Rand Sudafricano (finanziario)");
    result.put("ZAR", "Rand Sudafricano");
    result.put("ZMK", "Kwacha dello Zambia");
    result.put("ZRN", "Nuovo Zaire dello Zaire");
    result.put("ZRZ", "Zaire dello Zaire");
    result.put("ZWD", "Dollaro dello Zimbabwe");
    result.put("ZWL", "Dollaro Zimbabwiano (2009)");
    return result;
  }
  
  @Override
  protected JavaScriptObject loadNamesMapNative() {
    return overrideMap(super.loadNamesMapNative(), loadMyNamesMapOverridesNative());
  }
  
  private native JavaScriptObject loadMyNamesMapOverridesNative() /*-{
    return {
      "ADP": "Peseta Andorrana",
      "AED": "Dirham degli Emirati Arabi Uniti",
      "AFA": "Afgani (1927-2002)",
      "AFN": "Afgani",
      "ALL": "Lek Albanese",
      "AMD": "Dram Armeno",
      "ANG": "Fiorino delle Antille Olandesi",
      "AOA": "Kwanza Angolano",
      "AOK": "Kwanza Angolano (1977-1990)",
      "AON": "Nuovo Kwanza Angolano (1990-2000)",
      "AOR": "Kwanza Reajustado Angolano (1995-1999)",
      "ARA": "Austral Argentino",
      "ARP": "Peso Argentino (vecchio Cod.)",
      "ARS": "Peso Argentino",
      "ATS": "Scellino Austriaco",
      "AUD": "Dollaro Australiano",
      "AWG": "Fiorino di Aruba",
      "AZM": "Manat Azero (1993-2006)",
      "AZN": "Manat Azero",
      "BAD": "Dinar Bosnia-Herzegovina",
      "BAM": "Marco Conv. Bosnia-Erzegovina",
      "BBD": "Dollaro di Barbados",
      "BDT": "Taka Bangladese",
      "BEC": "Franco Belga (convertibile)",
      "BEF": "Franco Belga",
      "BEL": "Franco Belga (finanziario)",
      "BGL": "Lev Bulgaro",
      "BGN": "Nuovo Lev Bulgaro",
      "BHD": "Dinaro del Bahraini",
      "BIF": "Franco del Burundi",
      "BMD": "Dollaro delle Bermuda",
      "BND": "Dollaro del Brunei",
      "BOB": "Boliviano",
      "BOP": "Peso Boliviano",
      "BOV": "Mvdol Boliviano",
      "BRB": "Cruzeiro Novo Brasiliano (1967-1986)",
      "BRC": "Cruzado Brasiliano",
      "BRE": "Cruzeiro Brasiliano (1990-1993)",
      "BRL": "Real Brasiliano",
      "BRN": "Cruzado Novo Brasiliano",
      "BRR": "Cruzeiro Brasiliano",
      "BSD": "Dollaro delle Bahamas",
      "BTN": "Ngultrum Butanese",
      "BUK": "Kyat Birmano",
      "BWP": "Pula del Botswana",
      "BYB": "Nuovo Rublo Bielorussia (1994-1999)",
      "BYR": "Rublo Bielorussia",
      "BZD": "Dollaro Belize",
      "CAD": "Dollaro Canadese",
      "CDF": "Franco Congolese",
      "CHF": "Franco Svizzero",
      "CLF": "Unidades de Fomento Chilene",
      "CLP": "Peso Cileno",
      "CNY": "Renmimbi Cinese",
      "COP": "Peso Colombiano",
      "CRC": "Colón Costaricano",
      "CSD": "Antico Dinaro Serbo",
      "CSK": "Corona forte cecoslovacca",
      "CUC": "Peso cubano convertibile",
      "CUP": "Peso Cubano",
      "CVE": "Escudo del Capo Verde",
      "CYP": "Sterlina Cipriota",
      "CZK": "Corona Ceca",
      "DDM": "Ostmark della Germania Orientale",
      "DEM": "Marco Tedesco",
      "DJF": "Franco Gibutiano",
      "DKK": "Corona Danese",
      "DOP": "Peso Dominicano",
      "DZD": "Dinaro Algerino",
      "ECS": "Sucre dell’Ecuador",
      "ECV": "Unidad de Valor Constante (UVC) dell’Ecuador",
      "EEK": "Corona dell’Estonia",
      "EGP": "Sterlina Egiziana",
      "ERN": "Nakfa Eritreo",
      "ESA": "Peseta Spagnola Account",
      "ESB": "Peseta Spagnola Account Convertibile",
      "ESP": "Peseta Spagnola",
      "ETB": "Birr Etiopico",
      "EUR": "Euro",
      "FIM": "Markka Finlandese",
      "FJD": "Dollaro delle Figi",
      "FKP": "Sterlina delle Falkland",
      "FRF": "Franco Francese",
      "GBP": "Sterlina Inglese",
      "GEK": "Kupon Larit Georgiano",
      "GEL": "Lari Georgiano",
      "GHC": "Cedi del Ghana",
      "GHS": "Cedi ghanese",
      "GIP": "Sterlina di Gibilterra",
      "GMD": "Dalasi del Gambia",
      "GNF": "Franco della Guinea",
      "GNS": "Syli della Guinea",
      "GQE": "Ekwele della Guinea Equatoriale",
      "GRD": "Dracma Greca",
      "GTQ": "Quetzal Guatemalteco",
      "GWE": "Escudo della Guinea portoghese",
      "GWP": "Peso della Guinea-Bissau",
      "GYD": "Dollaro della Guyana",
      "HKD": "Dollaro di Hong Kong",
      "HNL": "Lempira Hoduregno",
      "HRD": "Dinaro Croato",
      "HRK": "Kuna Croata",
      "HTG": "Gourde Haitiano",
      "HUF": "Fiorino Ungherese",
      "IDR": "Rupia Indonesiana",
      "IEP": "Sterlina irlandese",
      "ILP": "Sterlina Israeliana",
      "ILS": "Nuovo sheqel israeliano",
      "INR": "Rupia Indiana",
      "IQD": "Dinaro Iracheno",
      "IRR": "Rial Iraniano",
      "ISK": "Corona Islandese",
      "ITL": "Lira Italiana",
      "JMD": "Dollaro Giamaicano",
      "JOD": "Dinaro Giordano",
      "JPY": "Yen Giapponese",
      "KES": "Scellino Keniota",
      "KGS": "Som Kirghiso",
      "KHR": "Riel Cambogiano",
      "KMF": "Franco Comoriano",
      "KPW": "Won Nordcoreano",
      "KRW": "Won Sudcoreano",
      "KWD": "Dinaro Kuwaitiano",
      "KYD": "Dollaro delle Isole Cayman",
      "KZT": "Tenge Kazaco",
      "LAK": "Kip Laotiano",
      "LBP": "Sterlina Libanese",
      "LKR": "Rupia di Sri Lanka",
      "LRD": "Dollaro Liberiano",
      "LSL": "Loti del Lesotho",
      "LTL": "Lita Lituana",
      "LTT": "Talonas Lituani",
      "LUC": "Franco Convertibile del Lussemburgo",
      "LUF": "Franco del Lussemburgo",
      "LUL": "Franco Finanziario del Lussemburgo",
      "LVL": "Lat Lettone",
      "LVR": "Rublo Lettone",
      "LYD": "Dinaro Libico",
      "MAD": "Dirham Marocchino",
      "MAF": "Franco Marocchino",
      "MDL": "Leu Moldavo",
      "MGA": "Ariary Malgascio",
      "MGF": "Franco Malgascio",
      "MKD": "Dinaro Macedone",
      "MLF": "Franco di Mali",
      "MMK": "Kyat di Myanmar",
      "MNT": "Tugrik Mongolo",
      "MOP": "Pataca di Macao",
      "MRO": "Ouguiya della Mauritania",
      "MTL": "Lira Maltese",
      "MTP": "Sterlina Maltese",
      "MUR": "Rupia Mauriziana",
      "MVR": "Rufiyaa delle Maldive",
      "MWK": "Kwacha Malawiano",
      "MXN": "Peso Messicano",
      "MXP": "Peso messicano d’argento (1861-1992)",
      "MXV": "Unidad de Inversion (UDI) Messicana",
      "MYR": "Ringgit della Malesia",
      "MZE": "Escudo del Mozambico",
      "MZN": "Metical del Mozambico",
      "NAD": "Dollaro Namibiano",
      "NGN": "Naira Nigeriana",
      "NIC": "Cordoba Nicaraguense",
      "NIO": "Córdoba oro nicaraguense",
      "NLG": "Fiorino Olandese",
      "NOK": "Corona Norvegese",
      "NPR": "Rupia Nepalese",
      "NZD": "Dollaro Neozelandese",
      "OMR": "Rial Omanita",
      "PAB": "Balboa di Panama",
      "PEI": "Inti Peruviano",
      "PEN": "Sol Nuevo Peruviano",
      "PES": "Sol Peruviano",
      "PGK": "Kina della Papua Nuova Guinea",
      "PHP": "Peso delle Filippine",
      "PKR": "Rupia del Pakistan",
      "PLN": "Zloty Polacco",
      "PLZ": "Zloty Polacco (1950-1995)",
      "PTE": "Escudo Portoghese",
      "PYG": "Guarani del Paraguay",
      "QAR": "Rial del Qatar",
      "RHD": "Dollaro della Rhodesia",
      "ROL": "Leu della Romania",
      "RON": "Leu Rumeno",
      "RSD": "Dinaro serbo",
      "RUB": "Rublo Russo",
      "RUR": "Rublo della CSI",
      "RWF": "Franco Ruandese",
      "SAR": "Ryal Saudita",
      "SBD": "Dollaro delle Isole Solomon",
      "SCR": "Rupia delle Seychelles",
      "SDD": "Dinaro Sudanese",
      "SDG": "Sterlina sudanese",
      "SEK": "Corona Svedese",
      "SGD": "Dollaro di Singapore",
      "SHP": "Sterlina di Sant’Elena",
      "SIT": "Tallero Sloveno",
      "SKK": "Corona Slovacca",
      "SLL": "Leone della Sierra Leone",
      "SOS": "Scellino Somalo",
      "SRD": "Dollaro surinamese",
      "SRG": "Fiorino del Suriname",
      "STD": "Dobra di Sao Tomé e Principe",
      "SUR": "Rublo Sovietico",
      "SVC": "Colón Salvadoregno",
      "SYP": "Sterlina Siriana",
      "SZL": "Lilangeni dello Swaziland",
      "THB": "Baht Tailandese",
      "TJR": "Rublo del Tajikistan",
      "TJS": "Somoni del Tajikistan",
      "TMM": "Manat Turkmeno (1993-2009)",
      "TMT": "Manat Turkmeno",
      "TND": "Dinaro Tunisino",
      "TOP": "Paʻanga di Tonga",
      "TPE": "Escudo di Timor",
      "TRL": "Lira Turca",
      "TRY": "Nuova Lira Turca",
      "TTD": "Dollaro di Trinidad e Tobago",
      "TWD": "Nuovo dollaro taiwanese",
      "TZS": "Scellino della Tanzania",
      "UAH": "Hrivna Ucraina",
      "UAK": "Karbovanetz Ucraino",
      "UGS": "Scellino Ugandese (1966-1987)",
      "UGX": "Scellino Ugandese",
      "USD": "Dollaro Statunitense",
      "USN": "Dollaro Statunitense (Next day)",
      "USS": "Dollaro Statunitense (Same day)",
      "UYI": "Peso uruguaiano in unità indicizzate",
      "UYP": "Peso Uruguaiano (1975-1993)",
      "UYU": "Peso Uruguayo uruguaiano",
      "UZS": "Sum dell’Uzbekistan",
      "VEB": "Bolivar Venezuelano",
      "VEF": "Bolívar venezuelano forte",
      "VND": "Dong Vietnamita",
      "VUV": "Vatu di Vanuatu",
      "WST": "Tala della Samoa Occidentale",
      "XAF": "Franco CFA BEAC",
      "XAG": "Argento",
      "XAU": "Oro",
      "XBA": "Unità composita europea",
      "XBB": "Unità monetaria europea",
      "XBC": "Unità di acconto europea (XBC)",
      "XBD": "Unità di acconto europea (XBD)",
      "XCD": "Dollaro dei Caraibi Orientali",
      "XDR": "Diritti Speciali di Incasso",
      "XFO": "Franco Oro Francese",
      "XFU": "Franco UIC Francese",
      "XOF": "Franco CFA BCEAO",
      "XPD": "Palladio",
      "XPF": "Franco CFP",
      "XPT": "Platino",
      "XRE": "Fondi RINET",
      "XTS": "Codice di verifica della valuta",
      "XXX": "Nessuna valuta",
      "YDD": "Dinaro dello Yemen",
      "YER": "Rial dello Yemen",
      "YUD": "Dinaro Forte Yugoslavo",
      "YUM": "Dinaro Noviy Yugoslavo",
      "YUN": "Dinaro Convertibile Yugoslavo",
      "ZAL": "Rand Sudafricano (finanziario)",
      "ZAR": "Rand Sudafricano",
      "ZMK": "Kwacha dello Zambia",
      "ZRN": "Nuovo Zaire dello Zaire",
      "ZRZ": "Zaire dello Zaire",
      "ZWD": "Dollaro dello Zimbabwe",
      "ZWL": "Dollaro Zimbabwiano (2009)",
    };
  }-*/;
}
