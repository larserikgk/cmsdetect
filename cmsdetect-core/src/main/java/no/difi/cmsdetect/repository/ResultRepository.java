package no.difi.cmsdetect.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import no.difi.cmsdetect.model.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;

@Repository
public class ResultRepository {

    private static Logger logger = Logger.getLogger(ResultRepository.class);
    private static Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    @Value("${firstpass.threshold.hits}")
    private int firstpassThresholdHits;
    @Value("${firstpass.threshold.score}")
    private double firstpassThresholdScore;

    public Result generateResult(URL url) {
        return new Result(firstpassThresholdHits, firstpassThresholdScore, url);
    }

    public void saveToFile(Result result) throws Exception {
        File file = new File("results/" + result.getUrl().toString().replace("/", "-").replace(":", "-") + ".json");

        FileWriter fileWriter = new FileWriter(file);
        gson.toJson(result, fileWriter);
        fileWriter.close();
    }
}
