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
public class VerbService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${webservice.verbs.baseurl}")
	private String baseURL;

	public List<String> randomVerbList(Integer size) {
		String url = baseURL + "/verbs?size=" + size;
		try {
			ResponseEntity<String[]> resp = restTemplate.getForEntity(url, String[].class);
			return Arrays.asList(resp.getBody());
		} catch (Exception e) {
			throw new RuntimeException("Couldn't get verb list: " + e.getMessage());
		}
	}

	public LinkDTO eventsLink(Integer interval) {
		return new LinkDTO(
			baseURL + "/verbs/events?interval=" + interval,
			"GET",
			"text/event-stream"
		);
	}

}
