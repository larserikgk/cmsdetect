package no.difi.cmsdetect.detector;

import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.repository.PageRepository;
import no.difi.cmsdetect.util.CmsDetector;
import no.difi.cmsdetect.util.FirstPass;
import no.difi.cmsdetect.util.Ignore;
import no.difi.cmsdetect.util.SecondPass;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by camp-shh on 05.08.2014.
 */
@CmsDetector
public class RadiantDetector {
	
	static Logger log = Logger.getLogger(RadiantDetector.class);

    @Autowired
    PageRepository pageRespository;
    
    int points = 0;

    @Ignore
    @FirstPass(1)
    public boolean hasHeader(Page page) {
    	return page.getDocument().getElementById("header") != null;
    }

    @Ignore
    @FirstPass(1)
    public boolean hasContent(Page page) {
    	return page.getDocument().getElementById("content") != null;
    }

    @Ignore
    @FirstPass(1)
    public boolean hasFooter(Page page) {
    	return page.getDocument().getElementById("footer") != null;
    }

    @FirstPass(4)
    public boolean hasPoweredByRadiant(Page page) {
        String rawPage = Jsoup.parse(page.getDocument().toString()).text();
        return rawPage.toLowerCase().contains("powered by radiant");
    }

    @SecondPass(4)
    public boolean checkAdmin(Page page) {
        try{
            return hasPoweredByRadiant(pageRespository.getPage(page.getUrl() + "/admin/login"));
        }catch(Exception e){
            return false;
        }
    }
    
    
//    @SecondPass(1)
//    public boolean checkLinks(Page page) {
//    	Elements links = page.getDocument().select("a[href]");
//    	print("\nLinks: (%d)", links.size());
//        for (Element link : links) {
//            print(" * a: <%s>  (%s)", link.attr("abs:href"), link.text());
//        }
//        return false;
//    }
//    
//    private void print(String msg, Object... args) {
//		log.log(Level.DEBUG ,String.format(msg, args));
//	}
}
