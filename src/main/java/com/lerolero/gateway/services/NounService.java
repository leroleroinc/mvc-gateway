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
public class NounService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${webservice.nouns.baseurl}")
	private String baseURL;

	public List<String> randomNounList(Integer size) {
		String url = baseURL + "/nouns?size=" + size;
		try {
			ResponseEntity<String[]> resp = restTemplate.getForEntity(url, String[].class);
			return Arrays.asList(resp.getBody());
		} catch (Exception e) {
			throw new RuntimeException("Couldn't get noun list: " + e.getMessage());
		}
	}

	public LinkDTO eventsLink(Integer interval) {
		return new LinkDTO(
			baseURL + "/nouns/events?interval=" + interval,
			"GET",
			"text/event-stream"
		);
	}

}
