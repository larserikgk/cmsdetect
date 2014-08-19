package no.difi.cmsdetect.detector;

import no.difi.cmsdetect.TestUrls;
import no.difi.cmsdetect.repository.PageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application.xml")
public class DrupalDetectorTest {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private DrupalDetector drupalDetector;

    @Test
    public void testHeadGenerator() {
        assertTrue(drupalDetector.headGenerator(pageRepository.getPage(TestUrls.KVALITET)));
    }

    public void testHasDrupalJS() {
        assertTrue(drupalDetector.hasDrupalJS(pageRepository.getPage(TestUrls.KVALITET)));
    }

    @Test
    public void testHeaderDrupalCache() {
        assertTrue(drupalDetector.headerDrupalCache(pageRepository.getPage(TestUrls.KVALITET)));
    }

    @Test
    public void testHeaderGenerator() {
        assertTrue(drupalDetector.headerGenerator(pageRepository.getPage(TestUrls.KVALITET)));
    }
}
