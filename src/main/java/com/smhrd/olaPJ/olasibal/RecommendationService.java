package com.smhrd.olaPJ.olasibal;

import com.smhrd.olaPJ.olasibal.Content;
import com.smhrd.olaPJ.olasibal.CSVLoader;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final CSVLoader csvLoader;
    private List<Content> contents;
    private List<String> allGenres;
    private List<String> allOtts;

    public RecommendationService(CSVLoader csvLoader) {
        this.csvLoader = csvLoader;
    }

    @PostConstruct
    public void init() throws IOException {
        contents = csvLoader.loadCSV("src/main/resources/OTT_contents_list.csv");
        allGenres = contents.stream().flatMap(c -> c.getGenres().stream()).distinct().collect(Collectors.toList());
        allOtts = contents.stream().flatMap(c -> c.getOttPlatforms().stream()).distinct().collect(Collectors.toList());
    }

    public List<Content> recommend(List<String> ott, List<String> genres, int totalNeeded) {
        double[] userVec = createUserVector(ott, genres);
        contents.forEach(c -> c.setSimilarity(cosineSimilarity(userVec, createContentVector(c))));
        return contents.stream()
                .filter(c -> c.getSimilarity() >= 0.7)
                .sorted(Comparator.comparingDouble(Content::getSimilarity).reversed())
                .limit(totalNeeded)
                .collect(Collectors.toList());
    }

    private double[] createUserVector(List<String> ott, List<String> genres) {
        double[] vec = new double[allOtts.size() + allGenres.size()];
        for (int i = 0; i < allOtts.size(); i++)
            vec[i] = ott.contains(allOtts.get(i)) ? 1 : 0;

        for (int i = 0; i < allGenres.size(); i++)
            vec[allOtts.size() + i] = genres.contains(allGenres.get(i)) ? 1 : 0;

        return vec;
    }

    private double[] createContentVector(Content content) {
        double[] vec = new double[allOtts.size() + allGenres.size()];
        for (int i = 0; i < allOtts.size(); i++)
            vec[i] = content.getOttPlatforms().contains(allOtts.get(i)) ? 1 : 0;

        for (int i = 0; i < allGenres.size(); i++)
            vec[allOtts.size() + i] = content.getGenres().contains(allGenres.get(i)) ? 1 : 0;

        return vec;
    }

    private double cosineSimilarity(double[] a, double[] b) {
        double dot = 0, normA = 0, normB = 0;
        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }
        return normA == 0 || normB == 0 ? 0 : dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
