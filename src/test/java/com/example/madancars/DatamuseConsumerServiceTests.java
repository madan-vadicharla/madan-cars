package com.example.madancars;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DatamuseConsumerServiceTests {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DatamuseConsumerService datamuseConsumerService;

    @Test
    public void testRelatedWords() {
        Datamuse data = new Datamuse();
        data.setWord("hello");
        data.setScore(100);

        ResponseEntity<List<Datamuse>> myEntity = new ResponseEntity<>(Arrays.asList(new Datamuse[]{data}), HttpStatus.OK);
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                eq(HttpMethod.GET),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<Datamuse>>>any())
        ).thenReturn(myEntity);

        String result = datamuseConsumerService.relatedWords("La Ferrari");
        assertNotNull(result);
        assertTrue(result.contains("hello"));
    }


}
