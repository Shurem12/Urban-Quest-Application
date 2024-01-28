package com.techelevator.controller;

//import com.techelevator.tenmo.model.Account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


//@RestController
//@PreAuthorize("isAuthenticated()")
public interface BaseController {
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/whoami", method = RequestMethod.GET)
    public String whoAmI();

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/info", method = RequestMethod.GET)
    public int currentUserId();

}
