package no.difi.cmsdetect.model;

import no.difi.cmsdetect.util.Metadata;
import org.apache.commons.math3.stat.interval.ConfidenceInterval;
import org.apache.commons.math3.stat.interval.WilsonScoreInterval;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Score implements Comparable<Score> {

    private static Logger logger = Logger.getLogger(Score.class);
    private static WilsonScoreInterval wilsonScoreInterval = new WilsonScoreInterval();

    private Class detector;
    private int tests;
    private int hits;
    private double score;
    private String info;

    public Score() {

    }

    public Score(Class detector) {
        this();
        this.detector = detector;
    }

    public Score(Object detector) {
        this(detector.getClass());
    }

    public Class getDetector() {
        return detector;
    }

    public void setDetector(Class detector) {
        this.detector = detector;
    }

    public void setDetector(Object detector) {
        this.detector = detector.getClass();
    }

    public int getTests() {
        return tests;
    }

    public int getHits() {
        return hits;
    }

    public double getScore() {
        return score;
    }

    public int getScorePercent() {
        return (int) (score * 100);
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void test(boolean hit, int points) {
        tests += points;

        if (hit)
            hits += points;
    }

    public String getName() {
        return detector.isAnnotationPresent(Metadata.class) ? ((Metadata) detector.getAnnotation(Metadata.class)).name() : detector.getSimpleName();
    }

    public String getDescription() {
        return detector.isAnnotationPresent(Metadata.class) ? ((Metadata) detector.getAnnotation(Metadata.class)).description() : "";
    }

    public String getHomepage() {
        return detector.isAnnotationPresent(Metadata.class) ? ((Metadata) detector.getAnnotation(Metadata.class)).homepage() : "";
    }

    public void calculateScore() {
        try {
            // Calculate score
            ConfidenceInterval confidenceInterval = wilsonScoreInterval.createInterval(tests, hits, 0.1);

            // Round score
            setScore(new BigDecimal(confidenceInterval.getLowerBound()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        // Name
        stringBuilder.append(getName()).append(":");

        // Hits
        stringBuilder.append(" ").append(getHits()).append("/").append(getTests());

        // Score
        stringBuilder.append(" (").append(getScorePercent()).append("%)");

        // Info
        if (info != null)
            stringBuilder.append(" [").append(getInfo()).append("]");

        return stringBuilder.append("\n").toString();
    }

    @Override
    public int compareTo(Score o) {
        return Double.compare(o.score, score);
    }
}
