package no.difi.cmsdetect.rest;

import java.net.URL;
import no.difi.cmsdetect.logic.DetectLogic;
import no.difi.cmsdetect.model.Result;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetectorController {

    ApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:application.xml");
    DetectLogic detectLogic = applicationContext.getBean(DetectLogic.class);

    @RequestMapping("/detectcms")
    public Result detectcms(@RequestParam(value="url") String url) {
        try{
            return (Result)detectLogic.run(new URL(url));
        }
        catch (Exception e) {
            return null;
        }

    }
}
