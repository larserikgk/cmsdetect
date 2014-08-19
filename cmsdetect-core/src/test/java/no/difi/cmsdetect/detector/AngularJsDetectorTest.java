package no.difi.cmsdetect.detector;


import static org.junit.Assert.*;

import no.difi.cmsdetect.TestUrls;
import no.difi.cmsdetect.repository.PageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application.xml")
public class AngularJsDetectorTest {

    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private AngularJsDetector angularJsDetector;

    @Test
    public void testControllerInBodyTag() {
        assertTrue(angularJsDetector.controllerInBodyTag(pageRepository.getPage(TestUrls.ANGULARJS)));
    }
}
