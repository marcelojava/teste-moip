package br.com.moip.test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = App.class, loader = AnnotationConfigContextLoader.class)
public class LogServiceTest {
	
	@Autowired
	private LogService service;
	
	private List<String> convertFileLogToString;
	
	@Before
	public void init() throws Exception {
		convertFileLogToString = this.service.convertFileLogToString();
	}

	@Test
	public void verifica_tamanho_linhas_arquivo() throws Exception {
		Assert.assertEquals(this.convertFileLogToString.size(), 547);
	}
	
	@Test
	public void verifica_maior_ocorrencia_email() throws Exception{
		String emailExpected = "https://surrealostrich.com.br";
		Map<String, Long> emails = this.service.getEmails(this.convertFileLogToString);
		List<String> listEmail = emails.keySet().stream().limit(1).collect(Collectors.toList()); 
		String email = listEmail.get(0);
		Assert.assertEquals(email, emailExpected);
	}
	
	@Test
	public void verifica_maior_ocorrencia_status_code() throws Exception{
		Long statusCodeExpected = 500l;
		Map<Long, Long> statusCodes = this.service.getStatusCodes(this.convertFileLogToString);
		List<Long> listStatusCodes = statusCodes.keySet().stream().limit(1).collect(Collectors.toList());
		Long statusCode = listStatusCodes.get(0);
		Assert.assertEquals(statusCode, statusCodeExpected);
	}
}
