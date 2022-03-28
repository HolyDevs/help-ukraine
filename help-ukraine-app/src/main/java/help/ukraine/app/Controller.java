package help.ukraine.app;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class Controller {

   @GetMapping("hello")
   public String get() {
      log.debug("hello endpoint hit");
      return "<h2>Hello Ukraine</<h2>";
   }
}