package br.com.moip.test;

import java.util.List;

import org.junit.Assert;
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

	@Test
	public void verifica_tamanho_arquivo() throws Exception {
		List<String> convertFileLogToString = this.service.convertFileLogToString();
		Assert.assertEquals(convertFileLogToString.size(), 547);
	}
}
