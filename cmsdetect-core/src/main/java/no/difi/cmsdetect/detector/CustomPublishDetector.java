package no.difi.cmsdetect.detector;

import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.util.CmsDetector;
import no.difi.cmsdetect.util.FirstPass;

/**
 * Created by camp-she on 08.08.2014.
 */

@CmsDetector
public class CustomPublishDetector {

    @FirstPass(2)
    public boolean hasGenerator(Page page){
        return page.getDocument().head().getElementsByAttributeValue("name","generator").attr("content").toLowerCase().contains("custompublish");
    }
}
