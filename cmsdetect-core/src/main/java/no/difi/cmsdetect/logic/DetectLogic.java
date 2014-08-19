package no.difi.cmsdetect.logic;

import no.difi.cmsdetect.model.Page;
import no.difi.cmsdetect.model.Result;
import no.difi.cmsdetect.model.Score;
import no.difi.cmsdetect.repository.PageRepository;
import no.difi.cmsdetect.repository.ResultRepository;
import no.difi.cmsdetect.util.Detector;
import no.difi.cmsdetect.util.FirstPass;
import no.difi.cmsdetect.util.Ignore;
import no.difi.cmsdetect.util.SecondPass;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;

@Service
public class DetectLogic {

    private static Logger logger = Logger.getLogger(DetectLogic.class);

    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private ApplicationContext applicationContext;

    public Result run(URL url) {
        Result result = resultRepository.generateResult(url);
        Page page = pageRepository.getPage(url);

        for (Object detector : applicationContext.getBeansWithAnnotation(Detector.class).values()) {
            if (!detector.getClass().isAnnotationPresent(Ignore.class)) {
                runDetector(detector, result, page, FirstPass.class);
                runDetector(detector, result, page, SecondPass.class);
            }
        }

        return result;
    }

    public Result run(String url) {
        try {
            return run(new URL(url));
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public void runDetector(Object detector, Result result, Page page, Class<? extends Annotation> annotation) {
        Score score = new Score(detector);

        // Run through all methods
        for (Method method : detector.getClass().getMethods()) {

            // Find methods annotated with correct annotation, and not annotated with annotation for ignoring
            if (method.isAnnotationPresent(annotation) && !method.isAnnotationPresent(Ignore.class)) {

                // Find points
                int points = 1;
                if (method.getAnnotation(FirstPass.class) != null)
                    points = method.getAnnotation(FirstPass.class).value();
                else if (method.getAnnotation(SecondPass.class) != null)
                    points = method.getAnnotation(SecondPass.class).value();

                // Generate arguments for method
                Object[] args = new Object[method.getGenericParameterTypes().length];
                for (int i = 0; i < args.length; i++) {
                    Type type = method.getGenericParameterTypes()[i];
                    if (type.equals(Page.class))
                        args[i] = page;
                    else if (type.equals(Score.class))
                        args[i] = score;
                    else if (type.equals(Result.class))
                        args[i] = result;
                }

                try {
                    // Invoke method and record result
                    if ("boolean".equals(method.getReturnType().getName()))
                        score.test((Boolean) method.invoke(detector, args), points);
                    else if ("void".equals(method.getReturnType().getName()))
                        method.invoke(detector, args);
                } catch (Exception e) {
                    logger.warn(e, e);
                }
            }
        }

        result.addScore(score);
    }

}
