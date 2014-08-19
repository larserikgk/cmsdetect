package no.difi.cmsdetect.detector;
import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.util.CmsDetector;
import no.difi.cmsdetect.util.FirstPass;

@CmsDetector
public class ContaoDetector {

    @FirstPass(2)
    public boolean checkComments(Page page){
        if (page.getDocument().getElementsByAttributeValue("name", "generator").attr("comments").toLowerCase().contains("poweredbycontao"))
            return true;
        return false;
    }

    @FirstPass(2)
    public boolean hasGenerator(Page page){
        if (page.getDocument().getElementsByAttributeValue("name", "generator").attr("content").toLowerCase().contains("contao"))
            return true;
        return false;
    }
}