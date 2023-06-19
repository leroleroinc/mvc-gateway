package com.lerolero.gateway.services;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

import com.lerolero.gateway.models.dto.LinkDTO;

@Service
public class AdverbService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${webservice.adverbs.baseurl}")
	private String baseURL;

	public List<String> randomAdverbList(Integer size) {
		String url = baseURL + "/adverbs?size=" + size;
		try {
			ResponseEntity<String[]> resp = restTemplate.getForEntity(url, String[].class);
			return Arrays.asList(resp.getBody());
		} catch (Exception e) {
			throw new RuntimeException("Couldn't get adverb list: " + e.getMessage());
		}
	}

	public LinkDTO eventsLink(Integer interval) {
		return new LinkDTO(
			baseURL + "/adverbs/events?interval=" + interval,
			"GET",
			"text/event-stream"
		);
	}

}
