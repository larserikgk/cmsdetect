package no.difi.cmsdetect.detector;

import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.repository.PageRepository;
import no.difi.cmsdetect.util.CmsDetector;
import no.difi.cmsdetect.util.FirstPass;
import no.difi.cmsdetect.util.Ignore;
import no.difi.cmsdetect.util.SecondPass;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by camp-shh on 05.08.2014.
 */
@CmsDetector
public class SilverStripeDetector {

    private static Logger logger = Logger.getLogger(SilverStripeDetector.class);

    @Autowired
    PageRepository pageRepository;

    @FirstPass(2)
    public boolean hasGenerator(Page page){
        return page.getDocument().head().getElementsByAttributeValue("name","generator").attr("content").toLowerCase().contains("silverstripe");
    }

    @SecondPass(2)
    public boolean checkAdmin(Page page){
        try{
            pageRepository.getPage(page.getUrl() + "Security/login").getDocument();
            if(pageRepository.getPage(page.getUrl() + "Security/login").getHeaders().get(null).contains("HTTP/1.1 301 Moved Permanently")){
                return false;
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Ignore
    @FirstPass
    public boolean hasAssetsPath(Page page){
        return !page.getDocument().getElementsByAttributeValueContaining("src", "/assets/").isEmpty();
    }
}
