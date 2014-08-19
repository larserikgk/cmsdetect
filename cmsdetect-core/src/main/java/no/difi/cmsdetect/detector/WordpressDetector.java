package no.difi.cmsdetect.detector;

import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.util.CmsDetector;
import no.difi.cmsdetect.util.FirstPass;

@CmsDetector
public class WordpressDetector {

    @FirstPass
    public boolean checkIncludesLink(Page page){
        return !page.getDocument().select("[href~=/wp-includes]").isEmpty();
    }
    @FirstPass
    public boolean checkContentLink(Page page) {
        return !page.getDocument().select("[href~=/wp-content]").isEmpty();
    }

    @FirstPass
    public boolean checkFavicon(Page page){
        return !page.getDocument().select("[href~=/image/x-icon]").isEmpty();
    }

    @FirstPass
    private static boolean checkAdmin(Page page){
        return !page.getDocument().select("[href~=/wp-admin]").isEmpty();
    }
}
