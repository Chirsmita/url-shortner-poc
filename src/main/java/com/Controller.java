package com;

import com.common.Utils;
import com.model.ShortCodeGenRequest;
import org.springframework.web.bind.annotation.*;


@RestController
public class Controller {
    /*
    function for generating short url
     */

    @RequestMapping(value = "genShortCode", method = RequestMethod.POST)
    public Object generateShortCode(@RequestBody ShortCodeGenRequest shortCodeGenRequest) {
        return Utils.genShortCode(shortCodeGenRequest);
    }

    @RequestMapping(value = "{shortCode}", method = RequestMethod.GET)
    public Object generateShortCode(@PathVariable String shortCode) {
        return Utils.redirectUrl(shortCode);
    }
}
