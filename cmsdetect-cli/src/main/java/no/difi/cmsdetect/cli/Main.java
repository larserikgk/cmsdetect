package no.difi.cmsdetect.cli;

import no.difi.cmsdetect.logic.DetectLogic;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.net.URL;

public class Main {

    public static void main(String... args) throws Exception {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:application.xml");
        DetectLogic detectLogic = applicationContext.getBean(DetectLogic.class);

        for (String url : args)
            System.out.println(detectLogic.run(new URL(url)));
    }
}
