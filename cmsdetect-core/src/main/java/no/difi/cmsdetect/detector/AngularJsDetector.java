package no.difi.cmsdetect.detector;


import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.util.FeatureDetector;
import no.difi.cmsdetect.util.FirstPass;
import no.difi.cmsdetect.util.Metadata;

@FeatureDetector
@Metadata(name = "AngularJS", homepage = "https://angularjs.org")
public class AngularJsDetector {

    @FirstPass
    public boolean controllerInBodyTag(Page page) {
        return page.getDocument().body().hasAttr("ng-controller");
    }
}
