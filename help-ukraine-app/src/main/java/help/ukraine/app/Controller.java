package help.ukraine.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

   @GetMapping("hello")
   public String get() {
      return "<h2>Hello Ukraine Kapitanie</h2>";
   }
}