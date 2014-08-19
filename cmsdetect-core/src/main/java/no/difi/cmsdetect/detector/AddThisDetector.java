package no.difi.cmsdetect.detector;

import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.util.FeatureDetector;
import no.difi.cmsdetect.util.FirstPass;
import no.difi.cmsdetect.util.Metadata;

@FeatureDetector
@Metadata(name = "AddThis", homepage = "http://www.addthis.com/")
public class AddThisDetector {

    @FirstPass
    public boolean checkScriptTags(Page page) {
        return page.getDocument().select("script[src~=addthis.com/]").size() > 0;
    }
}
