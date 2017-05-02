package br.com.moip.test;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class LogService {

	private static final String FILTER_EMAIL = "request_to=\"";
	private static final String FILTER_CODE = "response_status=\"";

	public List<String> convertFileLogToString() throws Exception {
		final String path = "log.txt";
		try (Stream<String> stream = Files.lines(Paths.get(path))) {
			return stream.filter(line -> line.contains("request_to") && line.contains("response_status"))
					.collect(toList());
		} catch (NoSuchFileException e) {
			throw new Exception("Erro ao abrir arquivo de log");
		}
	}

	public Map<String, Long> getEmails(final List<String> linhasLog) {
		final List<String> emails = new ArrayList<>();
		final Map<String, Long> emailCounting = new LinkedHashMap<>();
		linhasLog.forEach(linha -> emails.add(this.getEmail(linha)));

		emails.stream().collect(groupingBy(identity(), counting())).entrySet().stream()
				.sorted(Map.Entry.<String, Long>comparingByValue().reversed())
				.forEachOrdered(email -> emailCounting.put(email.getKey(), email.getValue()));
		return emailCounting;
	}

	public Map<Long, Long> getStatusCodes(final List<String> linhasLog) {
		final List<Long> emails = new ArrayList<>();
		final Map<Long, Long> statusCodes = new LinkedHashMap<>();
		linhasLog.forEach(linha -> emails.add(this.getCode(linha)));

		emails.stream().collect(groupingBy(identity(), counting())).entrySet().stream()
				.sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
				.forEachOrdered(email -> statusCodes.put(email.getKey(), email.getValue()));

		return statusCodes;
	}
	
	private String getEmail(String linha) {
		final int beginIndex = linha.indexOf(FILTER_EMAIL) + FILTER_EMAIL.length();
		final int endIndex = linha.indexOf("\"", beginIndex);
		return linha.substring(beginIndex, endIndex);
	}
	
	private Long getCode(String linha) {
		int initIndex = linha.indexOf(FILTER_CODE) + FILTER_CODE.length();
		int endIndex = linha.indexOf("\"", initIndex);
		return Long.valueOf(linha.substring(initIndex, endIndex));
	}
}
