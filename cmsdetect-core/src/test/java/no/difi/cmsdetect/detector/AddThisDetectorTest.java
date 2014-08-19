package no.difi.cmsdetect.detector;


import no.difi.cmsdetect.TestUrls;
import no.difi.cmsdetect.repository.PageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application.xml")
public class AddThisDetectorTest {

    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private AddThisDetector addThisDetector;

    @Test
    public void testControllerInBodyTag() {
        assertTrue(addThisDetector.checkScriptTags(pageRepository.getPage(TestUrls.DIFI)));
        assertFalse(addThisDetector.checkScriptTags(pageRepository.getPage(TestUrls.ANGULARJS)));
    }
}
