package com.smhrd.olaPJ.olasibal;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    private String title;
    private List<String> genres;
    private List<String> ottPlatforms;
    private double rating;
    private double similarity;
}
