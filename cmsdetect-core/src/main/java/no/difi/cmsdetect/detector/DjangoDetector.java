package no.difi.cmsdetect.detector;

import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.repository.PageRepository;
import no.difi.cmsdetect.util.CmsDetector;
import no.difi.cmsdetect.util.FirstPass;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

@CmsDetector
public class DjangoDetector {

    @Autowired
    PageRepository pageRepository;

    @FirstPass(2)
    public boolean hasDjangoAdminSite(Page page) {
        try {
            Document adminDoc = pageRepository.getPage(page.getUrl() + "/admin/").getDocument();
            return (adminDoc.select("title:contains(Django)").toString().length() > 0);
        }
        catch (Exception e) {
            return false;
        }
    }

    @FirstPass
    public boolean checkFaviconPath(Page page) {
        boolean result = false;
        if(!page.getDocument().head().select("[href~=/static/favicon.*]").isEmpty())
            result = true;
        if(!page.getDocument().head().select("[href~=/static/.*/favicon.*]").isEmpty())
           result = true;

        return result;
    }
}
