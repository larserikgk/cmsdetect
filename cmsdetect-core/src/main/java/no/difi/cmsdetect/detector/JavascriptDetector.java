package no.difi.cmsdetect.detector;

import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.model.Score;
import no.difi.cmsdetect.util.FeatureDetector;
import no.difi.cmsdetect.util.FirstPass;
import no.difi.cmsdetect.util.Metadata;

@FeatureDetector
@Metadata(name = "JavaScript")
public class JavascriptDetector {

    @FirstPass
    public boolean detectScriptTags(Page page, Score score) {
        return page.getDocument().select("script").size() > 0;
    }
}
