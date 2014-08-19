package no.difi.cmsdetect.detector;

import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.util.FeatureDetector;
import no.difi.cmsdetect.util.FirstPass;
import no.difi.cmsdetect.util.Metadata;

@FeatureDetector
@Metadata(name = "Bootstrap", homepage = "http://getbootstrap.com/")
public class BootstrapDetector {

    @FirstPass
    public boolean simpleContains(Page page) {
        return page.getContent().toLowerCase().contains("bootstrap");
    }
}
