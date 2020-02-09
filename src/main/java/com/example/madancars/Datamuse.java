package com.example.madancars;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Datamuse {

    private String word;
    private Integer score;

    public Datamuse() {}
}
