package it.mate.postscriptum.server.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Countries implements Serializable {

  private static List<CountryItem> instances = null;

  public static String getInternationalPrefixFromLanguage(final String fLanguage) {
    if (fLanguage == null) {
      return null;
    }
    String language = fLanguage.toLowerCase();
    if (language.length() > 2) {
      language = language.substring(0, 2);
    }
    List<CountryItem> countries = Countries.getList();
    for (CountryItem country : countries) {
      if (language.contains(country.getCode().toLowerCase())) {
        String prefix = country.getInternationalPrefix();
        if (prefix != null) {
          return prefix;
        }
      }
    }
    return null;
  }
  
  public static List<CountryItem> getList() {
    if (instances != null) {
      return instances;
    }
    instances = new ArrayList<CountryItem>();
    instances.add(new CountryItem("Afghanistan","Kabul","af","93"));
    instances.add(new CountryItem("Albania","Tirana","al","355"));
    instances.add(new CountryItem("Algeria","Algiers","dz","213"));
    instances.add(new CountryItem("AmericanSamoa","PagoPago","as","684"));
    instances.add(new CountryItem("Andorra","Andorra","ad","376"));
    instances.add(new CountryItem("Angola","Luanda","ao","244"));
    instances.add(new CountryItem("Anguilla","TheValley","ai","1-264"));
    instances.add(new CountryItem("Antarctica","None","aq","672"));
    instances.add(new CountryItem("AntiguaandBarbuda","St.Johns","ag","1-268"));
    instances.add(new CountryItem("Argentina","BuenosAires","ar","54"));
    instances.add(new CountryItem("Armenia","Yerevan","am","374"));
    instances.add(new CountryItem("Aruba","Oranjestad","aw","297"));
    instances.add(new CountryItem("Australia","Canberra","au","61"));
    instances.add(new CountryItem("Austria","Vienna","at","43"));
    instances.add(new CountryItem("Azerbaijan","Baku","az","994"));
    instances.add(new CountryItem("Bahamas","Nassau","bs","1-242"));
    instances.add(new CountryItem("Bahrain","Al-Manamah","bh","973"));
    instances.add(new CountryItem("Bangladesh","Dhaka","bd","880"));
    instances.add(new CountryItem("Barbados","Bridgetown","bb","1-246"));
    instances.add(new CountryItem("Belarus","Minsk","by","375"));
    instances.add(new CountryItem("Belgium","Brussels","be","32"));
    instances.add(new CountryItem("Belize","Belmopan","bz","501"));
    instances.add(new CountryItem("Benin","Porto-Novo","bj","229"));
    instances.add(new CountryItem("Bermuda","Hamilton","bm","1-441"));
    instances.add(new CountryItem("Bhutan","Thimphu","bt","975"));
    instances.add(new CountryItem("Bolivia","LaPaz","bo","591"));
    instances.add(new CountryItem("Bosnia-Herzegovina","Sarajevo","ba","387"));
    instances.add(new CountryItem("Botswana","Gaborone","bw","267"));
    instances.add(new CountryItem("BouvetIsland","None","bv",""));
    instances.add(new CountryItem("Brazil","Brasilia","br","55"));
    instances.add(new CountryItem("BritishIndianOceanTerritory","None","io",""));
    instances.add(new CountryItem("BruneiDarussalam","BandarSeriBegawan","bn","673"));
    instances.add(new CountryItem("Bulgaria","Sofia","bg","359"));
    instances.add(new CountryItem("BurkinaFaso","Ouagadougou","bf","226"));
    instances.add(new CountryItem("Burundi","Bujumbura","bi","257"));
    instances.add(new CountryItem("Cambodia","PhnomPenh","kh","855"));
    instances.add(new CountryItem("Cameroon","Yaounde","cm","237"));
    instances.add(new CountryItem("Canada","Ottawa","ca","1"));
    instances.add(new CountryItem("CapeVerde","Praia","cv","238"));
    instances.add(new CountryItem("CaymanIslands","Georgetown","ky","1-345"));
    instances.add(new CountryItem("CentralAfricanRepublic","Bangui","cf","236"));
    instances.add(new CountryItem("Chad","NDjamena","td","235"));
    instances.add(new CountryItem("Chile","Santiago","cl","56"));
    instances.add(new CountryItem("China","Beijing","cn","86"));
    instances.add(new CountryItem("ChristmasIsland","TheSettlement","cx","61"));
    instances.add(new CountryItem("Cocos(Keeling)Islands","WestIsland","cc","61"));
    instances.add(new CountryItem("Colombia","Bogota","co","57"));
    instances.add(new CountryItem("Comoros","Moroni","km","269"));
    instances.add(new CountryItem("Congo","Brazzaville","cg","242"));
    instances.add(new CountryItem("Congo,Dem.Republic","Kinshasa","cd","243"));
    instances.add(new CountryItem("CookIslands","Avarua","ck","682"));
    instances.add(new CountryItem("CostaRica","SanJose","cr","506"));
    instances.add(new CountryItem("Croatia","Zagreb","hr","385"));
    instances.add(new CountryItem("Cuba","Havana","cu","53"));
    instances.add(new CountryItem("Cyprus","Nicosia","cy","357"));
    instances.add(new CountryItem("CzechRep.","Prague","cz","420"));
    instances.add(new CountryItem("Denmark","Copenhagen","dk","45"));
    instances.add(new CountryItem("Djibouti","Djibouti","dj","253"));
    instances.add(new CountryItem("Dominica","Roseau","dm","1-767"));
    instances.add(new CountryItem("DominicanRepublic","SantoDomingo","do","809"));
    instances.add(new CountryItem("Ecuador","Quito","ec","593"));
    instances.add(new CountryItem("Egypt","Cairo","eg","20"));
    instances.add(new CountryItem("ElSalvador","SanSalvador","sv","503"));
    instances.add(new CountryItem("EquatorialGuinea","Malabo","gq","240"));
    instances.add(new CountryItem("Eritrea","Asmara","er","291"));
    instances.add(new CountryItem("Estonia","Tallinn","ee","372"));
    instances.add(new CountryItem("Ethiopia","AddisAbaba","et","251"));
    instances.add(new CountryItem("EuropeanUnion","Brussels","eu.int",""));
    instances.add(new CountryItem("FalklandIslands(Malvinas)","Stanley","fk","500"));
    instances.add(new CountryItem("FaroeIslands","Torshavn","fo","298"));
    instances.add(new CountryItem("Fiji","Suva","fj","679"));
    instances.add(new CountryItem("Finland","Helsinki","fi","358"));
    instances.add(new CountryItem("France","Paris","fr","33"));
    instances.add(new CountryItem("FrenchGuiana","Cayenne","gf","594"));
    instances.add(new CountryItem("FrenchSouthernTerritories","None","tf",""));
    instances.add(new CountryItem("Gabon","Libreville","ga","241"));
    instances.add(new CountryItem("Gambia","Banjul","gm","220"));
    instances.add(new CountryItem("Georgia","Tbilisi","ge","995"));
    instances.add(new CountryItem("Germany","Berlin","de","49"));
    instances.add(new CountryItem("Ghana","Accra","gh","233"));
    instances.add(new CountryItem("Gibraltar","Gibraltar","gi","350"));
    instances.add(new CountryItem("GreatBritain","London","gb","44"));
    instances.add(new CountryItem("Greece","Athens","gr","30"));
    instances.add(new CountryItem("Greenland","Godthab","gl","299"));
    instances.add(new CountryItem("Grenada","St.Georges","gd","1-473"));
    instances.add(new CountryItem("Guadeloupe(French)","Basse-Terre","gp","590"));
    instances.add(new CountryItem("Guam(USA)","Agana","gu","1-671"));
    instances.add(new CountryItem("Guatemala","GuatemalaCity","gt","502"));
    instances.add(new CountryItem("Guernsey","St.PeterPort","gg",""));
    instances.add(new CountryItem("Guinea","Conakry","gn","224"));
    instances.add(new CountryItem("GuineaBissau","Bissau","gw","245"));
    instances.add(new CountryItem("Guyana","Georgetown","gy","592"));
    instances.add(new CountryItem("Haiti","Port-au-Prince","ht","509"));
    instances.add(new CountryItem("HeardIslandandMcDonaldIslands","None","hm",""));
    instances.add(new CountryItem("Honduras","Tegucigalpa","hn","504"));
    instances.add(new CountryItem("HongKong","Victoria","hk","852"));
    instances.add(new CountryItem("Hungary","Budapest","hu","36"));
    instances.add(new CountryItem("Iceland","Reykjavik","is","354"));
    instances.add(new CountryItem("India","NewDelhi","in","91"));
    instances.add(new CountryItem("Indonesia","Jakarta","id","62"));
    instances.add(new CountryItem("Iran","Tehran","ir","98"));
    instances.add(new CountryItem("Iraq","Baghdad","iq","964"));
    instances.add(new CountryItem("Ireland","Dublin","ie","353"));
    instances.add(new CountryItem("IsleofMan","Douglas","im",""));
    instances.add(new CountryItem("Israel","Jerusalem","il","972"));
    instances.add(new CountryItem("Italy","Rome","it","39"));
    instances.add(new CountryItem("IvoryCoast","Abidjan","ci","225"));
    instances.add(new CountryItem("Jamaica","Kingston","jm","1-876"));
    instances.add(new CountryItem("Japan","Tokyo","jp","81"));
    instances.add(new CountryItem("Jersey","SaintHelier","je",""));
    instances.add(new CountryItem("Jordan","Amman","jo","962"));
    instances.add(new CountryItem("Kazakhstan","Astana","kz","7"));
    instances.add(new CountryItem("Kenya","Nairobi","ke","254"));
    instances.add(new CountryItem("Kiribati","Tarawa","ki","686"));
    instances.add(new CountryItem("Korea-North","Pyongyang","kp","850"));
    instances.add(new CountryItem("Korea-South","Seoul","kr","82"));
    instances.add(new CountryItem("Kuwait","KuwaitCity","kw","965"));
    instances.add(new CountryItem("Kyrgyzstan","Bishkek","kg","996"));
    instances.add(new CountryItem("Laos","Vientiane","la","856"));
    instances.add(new CountryItem("Latvia","Riga","lv","371"));
    instances.add(new CountryItem("Lebanon","Beirut","lb","961"));
    instances.add(new CountryItem("Lesotho","Maseru","ls","266"));
    instances.add(new CountryItem("Liberia","Monrovia","lr","231"));
    instances.add(new CountryItem("Libya","Tripoli","ly","218"));
    instances.add(new CountryItem("Liechtenstein","Vaduz","li","423"));
    instances.add(new CountryItem("Lithuania","Vilnius","lt","370"));
    instances.add(new CountryItem("Luxembourg","Luxembourg","lu","352"));
    instances.add(new CountryItem("Macau","Macau","mo","853"));
    instances.add(new CountryItem("Macedonia","Skopje","mk","389"));
    instances.add(new CountryItem("Madagascar","Antananarivo","mg","261"));
    instances.add(new CountryItem("Malawi","Lilongwe","mw","265"));
    instances.add(new CountryItem("Malaysia","KualaLumpur","my","60"));
    instances.add(new CountryItem("Maldives","Male","mv","960"));
    instances.add(new CountryItem("Mali","Bamako","ml","223"));
    instances.add(new CountryItem("Malta","Valletta","mt","356"));
    instances.add(new CountryItem("MarshallIslands","Majuro","mh","692"));
    instances.add(new CountryItem("Martinique(French)","Fort-de-France","mq","596"));
    instances.add(new CountryItem("Mauritania","Nouakchott","mr","222"));
    instances.add(new CountryItem("Mauritius","PortLouis","mu","230"));
    instances.add(new CountryItem("Mayotte","Dzaoudzi","yt","269"));
    instances.add(new CountryItem("Mexico","MexicoCity","mx","52"));
    instances.add(new CountryItem("Micronesia","Palikir","fm","691"));
    instances.add(new CountryItem("Moldova","Kishinev","md","373"));
    instances.add(new CountryItem("Monaco","Monaco","mc","377"));
    instances.add(new CountryItem("Mongolia","UlanBator","mn","976"));
    instances.add(new CountryItem("Montenegro","Podgorica","me","382"));
    instances.add(new CountryItem("Montserrat","Plymouth","ms","1-664"));
    instances.add(new CountryItem("Morocco","Rabat","ma","212"));
    instances.add(new CountryItem("Mozambique","Maputo","mz","258"));
    instances.add(new CountryItem("Myanmar","Naypyidaw","mm","95"));
    instances.add(new CountryItem("Namibia","Windhoek","na","264"));
    instances.add(new CountryItem("Nauru","Yaren","nr","674"));
    instances.add(new CountryItem("Nepal","Kathmandu","np","977"));
    instances.add(new CountryItem("Netherlands","Amsterdam","nl","31"));
    instances.add(new CountryItem("NetherlandsAntilles","Willemstad","an","599"));
    instances.add(new CountryItem("NewCaledonia(French)","Noumea","nc","687"));
    instances.add(new CountryItem("NewZealand","Wellington","nz","64"));
    instances.add(new CountryItem("Nicaragua","Managua","ni","505"));
    instances.add(new CountryItem("Niger","Niamey","ne","227"));
    instances.add(new CountryItem("Nigeria","Lagos","ng","234"));
    instances.add(new CountryItem("Niue","Alofi","nu","683"));
    instances.add(new CountryItem("NorfolkIsland","Kingston","nf","672"));
    instances.add(new CountryItem("NorthernMarianaIslands","Saipan","mp","670"));
    instances.add(new CountryItem("Norway","Oslo","no","47"));
    instances.add(new CountryItem("Oman","Muscat","om","968"));
    instances.add(new CountryItem("Pakistan","Islamabad","pk","92"));
    instances.add(new CountryItem("Palau","Koror","pw","680"));
    instances.add(new CountryItem("Panama","PanamaCity","pa","507"));
    instances.add(new CountryItem("PapuaNewGuinea","PortMoresby","pg","675"));
    instances.add(new CountryItem("Paraguay","Asuncion","py","595"));
    instances.add(new CountryItem("Peru","Lima","pe","51"));
    instances.add(new CountryItem("Philippines","Manila","ph","63"));
    instances.add(new CountryItem("PitcairnIsland","Adamstown","pn",""));
    instances.add(new CountryItem("Poland","Warsaw","pl","48"));
    instances.add(new CountryItem("Polynesia(French)","Papeete","pf","689"));
    instances.add(new CountryItem("Portugal","Lisbon","pt","351"));
    instances.add(new CountryItem("PuertoRico","SanJuan","pr","1-787"));
    instances.add(new CountryItem("Qatar","Doha","qa","974"));
    instances.add(new CountryItem("Reunion(French)","Saint-Denis","re","262"));
    instances.add(new CountryItem("Romania","Bucharest","ro","40"));
    instances.add(new CountryItem("Russia","Moscow","ru","7"));
    instances.add(new CountryItem("Rwanda","Kigali","rw","250"));
    instances.add(new CountryItem("SaintHelena","Jamestown","sh","290"));
    instances.add(new CountryItem("SaintKitts&NevisAnguilla","Basseterre","kn","1-869"));
    instances.add(new CountryItem("SaintLucia","Castries","lc","1-758"));
    instances.add(new CountryItem("SaintPierreandMiquelon","St.Pierre","pm","508"));
    instances.add(new CountryItem("SaintVincent&Grenadines","Kingstown","vc","1-784"));
    instances.add(new CountryItem("Samoa","Apia","ws","684"));
    instances.add(new CountryItem("SanMarino","SanMarino","sm","378"));
    instances.add(new CountryItem("SaoTomeandPrincipe","SaoTome","st","239"));
    instances.add(new CountryItem("SaudiArabia","Riyadh","sa","966"));
    instances.add(new CountryItem("Senegal","Dakar","sn","221"));
    instances.add(new CountryItem("Serbia","Belgrade","rs","381"));
    instances.add(new CountryItem("Seychelles","Victoria","sc","248"));
    instances.add(new CountryItem("SierraLeone","Freetown","sl","232"));
    instances.add(new CountryItem("Singapore","Singapore","sg","65"));
    instances.add(new CountryItem("Slovakia","Bratislava","sk","421"));
    instances.add(new CountryItem("Slovenia","Ljubljana","si","386"));
    instances.add(new CountryItem("SolomonIslands","Honiara","sb","677"));
    instances.add(new CountryItem("Somalia","Mogadishu","so","252"));
    instances.add(new CountryItem("SouthAfrica","Pretoria","za","27"));
    instances.add(new CountryItem("SouthGeorgia&SouthSandwichIslands","None","gs",""));
    instances.add(new CountryItem("SouthSudan","Ramciel","ss",""));
    instances.add(new CountryItem("Spain","Madrid","es","34"));
    instances.add(new CountryItem("SriLanka","Colombo","lk","94"));
    instances.add(new CountryItem("Sudan","Khartoum","sd","249"));
    instances.add(new CountryItem("Suriname","Paramaribo","sr","597"));
    instances.add(new CountryItem("SvalbardandJanMayenIslands","Longyearbyen","sj",""));
    instances.add(new CountryItem("Swaziland","Mbabane","sz","268"));
    instances.add(new CountryItem("Sweden","Stockholm","se","46"));
    instances.add(new CountryItem("Switzerland","Bern","ch","41"));
    instances.add(new CountryItem("Syria","Damascus","sy","963"));
    instances.add(new CountryItem("Taiwan","Taipei","tw","886"));
    instances.add(new CountryItem("Tajikistan","Dushanbe","tj","992"));
    instances.add(new CountryItem("Tanzania","Dodoma","tz","255"));
    instances.add(new CountryItem("Thailand","Bangkok","th","66"));
    instances.add(new CountryItem("Togo","Lome","tg","228"));
    instances.add(new CountryItem("Tokelau","None","tk","690"));
    instances.add(new CountryItem("Tonga","Nukualofa","to","676"));
    instances.add(new CountryItem("TrinidadandTobago","PortofSpain","tt","1-868"));
    instances.add(new CountryItem("Tunisia","Tunis","tn","216"));
    instances.add(new CountryItem("Turkey","Ankara","tr","90"));
    instances.add(new CountryItem("Turkmenistan","Ashgabat","tm","993"));
    instances.add(new CountryItem("TurksandCaicosIslands","GrandTurk","tc","1-649"));
    instances.add(new CountryItem("Tuvalu","Funafuti","tv","688"));
    instances.add(new CountryItem("U.K.","London","uk","44"));
    instances.add(new CountryItem("Uganda","Kampala","ug","256"));
    instances.add(new CountryItem("Ukraine","Kiev","ua","380"));
    instances.add(new CountryItem("UnitedArabEmirates","AbuDhabi","ae","971"));
    instances.add(new CountryItem("Uruguay","Montevideo","uy","598"));
    instances.add(new CountryItem("USA","Washington","us","1"));
    instances.add(new CountryItem("USAMinorOutlyingIslands","None","um",""));
    instances.add(new CountryItem("Uzbekistan","Tashkent","uz","998"));
    instances.add(new CountryItem("Vanuatu","PortVila","vu","678"));
    instances.add(new CountryItem("Vatican","VaticanCity","va","39"));
    instances.add(new CountryItem("Venezuela","Caracas","ve","58"));
    instances.add(new CountryItem("Vietnam","Hanoi","vn","84"));
    instances.add(new CountryItem("VirginIslands(British)","RoadTown","vg","1-284"));
    instances.add(new CountryItem("VirginIslands(USA)","CharlotteAmalie","vi","1-340"));
    instances.add(new CountryItem("WallisandFutunaIslands","Mata-Utu","wf","681"));
    instances.add(new CountryItem("WesternSahara","ElAaiun","eh",""));
    instances.add(new CountryItem("Yemen","Sana","ye","967"));
    instances.add(new CountryItem("Zambia","Lusaka","zm","260"));
    instances.add(new CountryItem("Zimbabwe","Harare","zw","263"));
    return instances;
  }

  protected static class CountryItem  {
    
    private String name;
    
    private String capital;
    
    private String code;
    
    private String internationalPrefix;
    
    protected CountryItem(String name, String capital, String code, String prefixNumber) {
      super();
      this.name = name;
      this.capital = capital;
      this.code = code;
      this.internationalPrefix = prefixNumber;
    }

    public String getName() {
      return name;
    }

    public String getCapital() {
      return capital;
    }

    public String getCode() {
      return code;
    }

    public String getInternationalPrefix() {
      return internationalPrefix;
    }
    
  }
  
}
