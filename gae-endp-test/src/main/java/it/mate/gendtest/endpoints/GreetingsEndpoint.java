package it.mate.gendtest.endpoints;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.appengine.api.users.User;


/*******************************************************************************
 * 
 * SEE API CONSOLE
 * 
 * FOR gendtest >> https://code.google.com/apis/console/#project:929530856992
 * 
 * 
 */

@Api (name="greetings", version="v1", description="Greetings Test API - build 2", 
      clientIds = {com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID,
          "929530856992.apps.googleusercontent.com",
          "929530856992-bb0ht17h9o9vbvg43sv0pvu6gfpinhr8.apps.googleusercontent.com",
          "929530856992-tgsgml6l0au4b4q12r296o0bdk6o7e4f.apps.googleusercontent.com",
          "929530856992-8sdg8clc0dk3is3hvn7aaoajvcgt07vr.apps.googleusercontent.com" }
)
public class GreetingsEndpoint {
  
  public static List<Greetings> greetings = new ArrayList<Greetings>();
  
  static {
    greetings.add(new Greetings("initial greeting uno"));
    greetings.add(new Greetings("initial greeting due"));
  }
  
  @ApiMethod (name="list", httpMethod=HttpMethod.GET)
  public List<Greetings> listGreetings() {
    return greetings;
  }

  @ApiMethod (name="get", httpMethod=HttpMethod.GET)
  public Greetings getGreetings(@Named("id") Integer id) {
    return greetings.get(id);
  }

  @ApiMethod (name="add", httpMethod=HttpMethod.PUT)
  public VoidResult addGreeting(@Named("message") String message) {
    greetings.add(new Greetings(message));
    return VoidResult.VOID;
  }
  
  @ApiMethod (name="addLogged", httpMethod=HttpMethod.PUT)
  public VoidResult addGreetingLogged(@Named("message") String message, User user) {
    greetings.add(new Greetings(message + " from " + user.getEmail()));
    return VoidResult.VOID;
  }

}
