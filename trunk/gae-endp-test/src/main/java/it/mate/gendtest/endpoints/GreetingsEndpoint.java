package it.mate.gendtest.endpoints;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.appengine.api.users.User;

@Api (name="greetings", version="v1", description="Greetings Test API - build 2", 
      clientIds = {com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID,
          "929530856992.apps.googleusercontent.com",
          "929530856992-bb0ht17h9o9vbvg43sv0pvu6gfpinhr8.apps.googleusercontent.com",
          "929530856992-tgsgml6l0au4b4q12r296o0bdk6o7e4f.apps.googleusercontent.com",
          "929530856992-051orvog0ch2q68bmfggrksu7gp1ifap.apps.googleusercontent.com" }
   
    
)
public class GreetingsEndpoint {
  
  public static List<Greetings> greetings = new ArrayList<Greetings>();
  
  static {
    greetings.add(new Greetings("ciao mondo"));
    greetings.add(new Greetings("questa e' la build 2"));
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
  public VoidResult addGreetings(@Named("message") String message) {
    greetings.add(new Greetings(message));
    return VoidResult.VOID;
  }
  
  @ApiMethod (name="addUser", httpMethod=HttpMethod.PUT)
  public VoidResult addUser(User user) {
    greetings.add(new Greetings("Ciao " + user.getEmail()));
    return VoidResult.VOID;
  }

}
