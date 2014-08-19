package no.difi.cmsdetect.detector;

import no.difi.cmsdetect.util.FeatureDetector;
import no.difi.cmsdetect.util.FirstPass;
import no.difi.cmsdetect.util.Metadata;


@FeatureDetector
@Metadata(name = "SSL")
public class SecureConnectionDetector {

    @FirstPass
    public boolean connectionIsSecure() {
        return false;
    }
}
