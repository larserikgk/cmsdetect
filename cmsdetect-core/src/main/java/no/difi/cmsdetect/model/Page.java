package no.difi.cmsdetect.model;

import com.google.gson.annotations.Expose;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class Page {

    @Expose
    private URL url;
    @Expose
    private Map<String, List<String>> headers;
    @Expose
    private String content;
    private Document document;

    public Page() {
    }

    public Page(URL url) {
        this();
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
