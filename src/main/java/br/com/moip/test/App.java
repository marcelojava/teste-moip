package br.com.moip.test;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner
{
	private LogService service;
	
	@Autowired
	public App(LogService service) {
		this.service = service;
	}
	
    public static void main(String... args)
    {
     SpringApplication.run(App.class, args);
    }

	@Override
	public void run(String... arg0) throws Exception {
		List<String> convertFileLogToString = this.service.convertFileLogToString();
		Map<String, Long> emails = this.service.getEmails(convertFileLogToString);
		Map<Long, Long> statusCodes = this.service.getStatusCodes(convertFileLogToString);
		
		emails.entrySet().stream().limit(3).forEach(System.out::println);
		System.out.println(statusCodes);
	}
    
}
