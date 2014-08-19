package no.difi.cmsdetect.detector;

import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.util.FeatureDetector;
import no.difi.cmsdetect.util.FirstPass;
import no.difi.cmsdetect.util.Metadata;

@FeatureDetector
@Metadata(name = "HTML 5")
public class Html5Detector {

    @FirstPass
    public boolean detectDoctype(Page page) {
        return page.getContent().trim().toLowerCase().startsWith("<!doctype html>");
    }

}
