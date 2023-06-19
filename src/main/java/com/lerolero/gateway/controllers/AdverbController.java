package com.lerolero.gateway.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lerolero.gateway.services.AdverbService;
import com.lerolero.gateway.models.dto.LinkDTO;

@RestController
@RequestMapping("/adverbs")
public class AdverbController {

	@Autowired
	private AdverbService adverbService;

	@GetMapping
	public List<String> get(@RequestParam(defaultValue = "1") Integer size) {
		return adverbService.randomAdverbList(size);
	}

	@GetMapping("/events")
	public LinkDTO getEvents(@RequestParam(defaultValue = "200") Integer interval) {
		return adverbService.eventsLink(interval);
	}

}
