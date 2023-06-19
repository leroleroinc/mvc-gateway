package com.lerolero.gateway.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lerolero.gateway.services.NounService;
import com.lerolero.gateway.models.dto.LinkDTO;

@RestController
@RequestMapping("/nouns")
public class NounController {

	@Autowired
	private NounService nounService;

	@GetMapping
	public List<String> get(@RequestParam(defaultValue = "1") Integer size) {
		return nounService.randomNounList(size);
	}

	@GetMapping("/events")
	public LinkDTO getEvents(@RequestParam(defaultValue = "200") Integer interval) {
		return nounService.eventsLink(interval);
	}

}
