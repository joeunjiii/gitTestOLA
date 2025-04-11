package com.smhrd.olaPJ.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContentRequest {

    private Long id;
    private String title;
    private String posterImg;
}
