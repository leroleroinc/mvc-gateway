package com.lerolero.gateway.controllers;

import java.util.List;
import java.util.LinkedList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/chat")
public class ChatController {

	private record Message(String from, String text) {};
	private record OutputMessage(String from, String text, String time) {};
	private static List<OutputMessage> messages = new LinkedList<>();

	@PostMapping
	public OutputMessage send(@RequestBody Message message) throws Exception {
		//Thread.sleep(1000); // simulated delay
		String time = new SimpleDateFormat("HH:mm").format(new Date());
		OutputMessage outputMessage = new OutputMessage(message.from(), message.text(), time);
		messages.add(outputMessage);
		return outputMessage;
	}

	@GetMapping
	public SseEmitter subscribe() {
		SseEmitter emitter = new SseEmitter(-1L);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(() -> {
			try {
				int i = messages.size();
				while (true) {
					if (i < messages.size()) {
						OutputMessage m = messages.get(i);
						//Thread.sleep(1000); // simulated delay
						emitter.send(
							"{\"from\":\"" + m.from() +
							"\",\"text\":\"" + m.text() +
							"\",\"time\":\"" + m.time() +
							"\"}");
						i++;
					}
				}
			} catch (Exception e) {
				emitter.completeWithError(e);
			} finally {
				emitter.complete();
			}
		});
		executor.shutdown();
		return emitter;
	}

}
