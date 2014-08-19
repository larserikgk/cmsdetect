package no.difi.cmsdetect.detector;

import no.difi.cmsdetect.TestUrls;
import no.difi.cmsdetect.repository.PageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application.xml")
public class ZopeDetectorTest {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private ZopeDetector zopeDetector;

    @Test
    public void testHeaderPoweredBy() throws Exception {
        assertTrue(zopeDetector.headerPoweredBy(pageRepository.getPage(TestUrls.DIFI)));
    }
}
