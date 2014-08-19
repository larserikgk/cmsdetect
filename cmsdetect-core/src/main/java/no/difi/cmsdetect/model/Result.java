package no.difi.cmsdetect.model;

import no.difi.cmsdetect.util.CmsDetector;
import no.difi.cmsdetect.util.FeatureDetector;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Result {

    private int firstpassThresholdHits;
    private double firstpassThresholdScore;

    private URL url;

    private List<Score> cmsScores;
    private List<Score> featureScores;

    public Result(int firstpassThresholdHits, double firstpassThresholdScore) {
        this.firstpassThresholdHits = firstpassThresholdHits;
        this.firstpassThresholdScore = firstpassThresholdScore;

        cmsScores = new ArrayList<Score>();
        featureScores = new ArrayList<Score>();
    }

    public Result(int firstpassThresholdHits, double firstpassThresholdScore, URL url) {
        this(firstpassThresholdHits, firstpassThresholdScore);
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public List<Score> getCmsScores() {
        Collections.sort(cmsScores);
        return cmsScores;
    }

    public List<Score> getFeatureScores() {
        Collections.sort(featureScores);
        return featureScores;
    }

    public void addScore(Score score) {
        if (score.getTests() > 0) {
            score.calculateScore();

            if (score.getScore() > firstpassThresholdScore) {
               if (score.getDetector().isAnnotationPresent(CmsDetector.class))
                    this.cmsScores.add(score);
                else if (score.getDetector().isAnnotationPresent(FeatureDetector.class))
                    this.featureScores.add(score);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Site: ").append(url).append("\n");

        stringBuilder.append("CMS:\n");
        for (Score score : getCmsScores()) {
            stringBuilder.append("* ").append(score);
        }

        stringBuilder.append("Features:\n");
        for (Score score : getFeatureScores()) {
            stringBuilder.append("* ").append(score);
        }

        return stringBuilder.toString();
    }
}
