package no.difi.cmsdetect.detector;

/**
 * Created by camp-shh on 05.08.2014.
 */

import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.repository.PageRepository;
import no.difi.cmsdetect.util.CmsDetector;
import no.difi.cmsdetect.util.FirstPass;
import no.difi.cmsdetect.util.Ignore;
import no.difi.cmsdetect.util.SecondPass;
import org.springframework.beans.factory.annotation.Autowired;

@CmsDetector
public class JoomlaDetector {

    @Autowired
    PageRepository pageRepository;

    @FirstPass(2)
    public boolean hasGenerator(Page page){
        return page.getDocument().head().getElementsByAttributeValue("name", "generator").attr("content").toLowerCase().contains("joomla");
    }

    @FirstPass
    public boolean hasFavicon(Page page){
        return !page.getDocument().head().select("[href~=/templates/.*/favicon.*]").isEmpty();
    }

    @FirstPass
    public boolean hasMootools(Page page){
          return page.getDocument().head().select("[src~=/media/system/]").toString().contains("moo");
    }

    @SecondPass
    public boolean hasAdministrator(Page page){
        try{
            return hasGenerator(pageRepository.getPage(page.getUrl()+"/administrator/"));
        }catch(Exception e){
            return false;
        }
    }



}
