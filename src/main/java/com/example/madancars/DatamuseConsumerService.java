package com.example.madancars;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j

@Service
public class DatamuseConsumerService {

    private final String uri = "http://api.datamuse.com/words?ml=";

    private final RestTemplate restTemplate;

    public String relatedWords(String word) {
        Preconditions.checkNotNull(word);
        try {
            ResponseEntity<List<Datamuse>> data = restTemplate.exchange(uri+word,
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Datamuse>>() { });
            List<Datamuse> words = data.getBody();
            log.debug("data related to {} received from datamuse is {}", word, words);
            return words.stream()
                    .map(w -> w.getWord())
                    .collect(Collectors.joining(","));
        } catch (RestClientResponseException e) {
            log.error("Error occurred contacting Datamuse, code:{} response:{} - continuing flow with empty response",
                    e.getRawStatusCode(), e.getResponseBodyAsString());
        }
        return "";
    }
}
