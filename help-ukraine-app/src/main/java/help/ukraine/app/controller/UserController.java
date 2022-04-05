package help.ukraine.app.controller;

import help.ukraine.app.exception.DataNotExistsException;
import help.ukraine.app.model.UserModel;
import help.ukraine.app.security.constants.SecurityConstants;
import help.ukraine.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@Log4j2
public class UserController {

   private final UserService userService;

   @GetMapping("/hello")
   public String get() {
      log.debug("hello endpoint hit");
      return "<h2>Hello Ukraine</<h2>";
   }

   @GetMapping(value = "/user", produces = APPLICATION_JSON_VALUE)
   @Secured({SecurityConstants.REFUGEE_ROLE, SecurityConstants.HOST_ROLE})
   public ResponseEntity<UserModel> getUserByEmail(@RequestParam("email") String email) {
      try {
         log.debug("fetch user endpoint hit");
         UserModel userModel = userService.getUserByEmail(email);
         return ResponseEntity.ok().body(userModel);
      } catch (DataNotExistsException e) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
      }
   }
}