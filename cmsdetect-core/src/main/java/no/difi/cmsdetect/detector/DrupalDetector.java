package no.difi.cmsdetect.detector;

import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.util.*;

@CmsDetector
@Metadata(name = "Drupal", homepage = "https://drupal.org/")
public class DrupalDetector {

    @FirstPass(2)
	public boolean headGenerator(Page page){
		return page.getDocument().getElementsByAttributeValue("name", "generator").attr("content").toLowerCase().contains("drupal");
	}

    @FirstPass
	public boolean hasFavIcon(Page page){
		return !page.getDocument().select("[href~=/themes/.*/favicon.*]").isEmpty();
	}
	
	//TODO: not finished
    @FirstPass
	public boolean hasDrupalJS(Page page){
		return page.getDocument().getElementsByTag("script").toString().contains("drupal.js");
	}

    @FirstPass
    public boolean headerDrupalCache(Page page) {
        return page.getHeaders().containsKey("X-Drupal-Cache");
    }

    @FirstPass(2)
    public boolean headerGenerator(Page page) {
        if (page.getHeaders().containsKey("X-Generator"))
            for (String value : page.getHeaders().get("X-Generator"))
                if (value.contains("Drupal"))
                    return true;

        return false;
    }
}
