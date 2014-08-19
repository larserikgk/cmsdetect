package no.difi.cmsdetect.detector;

import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.util.FeatureDetector;
import no.difi.cmsdetect.util.FirstPass;
import no.difi.cmsdetect.util.Metadata;

@FeatureDetector
@Metadata(name = "Stylesheet")
public class StylesheetDetector {

    @FirstPass
    public boolean detectTags(Page page) {
        return page.getDocument().select("style, link[rel=stylesheet]").size() > 0;
    }

}
