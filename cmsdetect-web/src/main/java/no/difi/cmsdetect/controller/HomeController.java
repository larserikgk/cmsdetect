package no.difi.cmsdetect.controller;

import no.difi.cmsdetect.logic.DetectLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private DetectLogic detectLogic;

    @RequestMapping(method = RequestMethod.GET)
    public String view(@RequestParam(value = "url", required = false) String url, ModelMap modelMap) {
        modelMap.put("url", url);

        if (url != null)
            modelMap.put("result", detectLogic.run(url));

        return "home";
    }
}
