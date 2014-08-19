package no.difi.cmsdetect.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.difi.cmsdetect.model.Page;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Repository
public class PageRepository {

    private static Logger logger = Logger.getLogger(PageRepository.class);
    private static Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    @Value("${cache.page.status}")
    private boolean cachePageStatus;

    public Page getPage(URL url) {
        try {
            String filename = url.toString().replace("/", "-").replace(":", "-");

            File locale = new File("sites/" + filename + ".json");
            if (locale.exists())
                return getFileDisk(locale);

            URL path = getClass().getResource("/sites/" + filename + ".json");
            if (path != null)
                return getFileDisk(new File(path.toURI()));

            Page page = getPageWeb(url);
            if (page != null && cachePageStatus)
                saveToFile(page, locale);

            return page;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public Page getPage(String url) {
        try {
            return getPage(new URL(url));
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public Page getPageWeb(URL url) {
        logger.debug("Fetching " + url.toString());

        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            HttpURLConnection.setFollowRedirects(false);
            conn.setRequestProperty("User-agent", "CmsDetect");


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(), byteArrayOutputStream);

            Page page = new Page(conn.getURL());
            page.setHeaders(conn.getHeaderFields());
            page.setContent(byteArrayOutputStream.toString());
            page.setDocument(Jsoup.parse(page.getContent(), url.toString()));
            return page;
        } catch (Exception e) {
            logger.warn(e);
        }

        return null;
    }

    public Page getFileDisk(File file) throws Exception {
        logger.debug("Fetching " + file.toString());

        FileReader fileReader = new FileReader(file);
        Page page = gson.fromJson(fileReader, Page.class);
        fileReader.close();

        page.setDocument(Jsoup.parse(page.getContent(), page.getUrl().toString()));

        return page;
    }

    public void saveToFile(Page page, File file) throws Exception {
        FileWriter fileWriter = new FileWriter(file);
        gson.toJson(page, fileWriter);
        fileWriter.close();
    }
}
