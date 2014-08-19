package no.difi.cmsdetect.logic;

import no.difi.cmsdetect.TestUrls;
import no.difi.cmsdetect.detector.*;
import no.difi.cmsdetect.model.Result;
import static org.junit.Assert.*;

import no.difi.cmsdetect.model.Score;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application.xml")
public class DetectLogicTest {

    @Autowired
    private DetectLogic detectLogic;

    @Test
    public void testDifi() {
        Result result = detectLogic.run(TestUrls.DIFI);

        assertNotNull(result);
        checkScoreListHelper(result.getCmsScores(), ZopeDetector.class);
        checkScoreListHelper(result.getFeatureScores(), AddThisDetector.class, Html5Detector.class, JavascriptDetector.class, JQueryDetector.class, StylesheetDetector.class);
    }

    @Test
    public void testKvalitet() {
        Result result = detectLogic.run(TestUrls.KVALITET);

        assertNotNull(result);
        checkScoreListHelper(result.getCmsScores(), DrupalDetector.class);
        checkScoreListHelper(result.getFeatureScores(), Html5Detector.class, JavascriptDetector.class, JQueryDetector.class, StylesheetDetector.class);
    }

    private void checkScoreListHelper(List<Score> scoreList, Class... clss) {
        List<Class> clsList = new ArrayList<Class>();
        for (Score score : scoreList)
            clsList.add(score.getDetector());

        for (Class cls : clss)
            assertTrue(clsList.contains(cls));
    }
}
