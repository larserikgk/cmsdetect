package no.difi.cmsdetect.detector;

import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.repository.PageRepository;
import no.difi.cmsdetect.util.CmsDetector;
import no.difi.cmsdetect.util.FirstPass;
import no.difi.cmsdetect.util.Ignore;
import no.difi.cmsdetect.util.SecondPass;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

@CmsDetector
public class ModxDetector {

    @Autowired
    PageRepository pageRepository;

    @FirstPass
    public boolean checkManagerLink(Page page) {
        try {
            Document managerDoc = pageRepository.getPage(page.getUrl() + "/manager/").getDocument();
            return (managerDoc.select("title:contains(modx)").toString().length() > 0);
        }
        catch (Exception e) {
            return false;
        }
    }
    @Ignore
    @FirstPass
    public boolean checkTitle(Page page) {
        return page.getDocument().select("title:contains(modx)").toString().length() > 0;
    }

    @Ignore
    @FirstPass
    public boolean checkLicense(Page page) {
        return page.getDocument().select("p.loginLicense:contains(modx)").toString().length() > 0;
    }

    @Ignore
    @FirstPass
    public boolean checkConnectors(Page page) {
        try {
            return !pageRepository.getPage(page.getUrl() + "/connectors/").getDocument().toString().isEmpty();
        }
        catch (Exception e) {
            return false;
        }
    }

    @Ignore
    @SecondPass
    public boolean checkCookie(Page page) {
        return false;
    }
}
