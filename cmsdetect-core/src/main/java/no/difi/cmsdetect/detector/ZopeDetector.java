package no.difi.cmsdetect.detector;

import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.util.CmsDetector;
import no.difi.cmsdetect.util.FirstPass;
import no.difi.cmsdetect.util.Metadata;

@CmsDetector
@Metadata(name = "Zope", homepage = "http://www.zope.org/")
public class ZopeDetector {

    @FirstPass(2)
    public boolean headerPoweredBy(Page page) {
        if (page.getHeaders().containsKey("X-Powered-By"))
            for (String value : page.getHeaders().get("X-Powered-By"))
                if (value.contains("Zope"))
                    return true;

        return false;
    }
}
