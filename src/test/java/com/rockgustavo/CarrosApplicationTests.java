package com.rockgustavo;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.rockgustavo.domain.Carro;
import com.rockgustavo.domain.CarroService;
import com.rockgustavo.domain.dto.CarroDTO;

@SpringBootTest
class CarrosApplicationTests {
	protected TestRestTemplate rest;

	@Autowired
	private CarroService service;
	
	@Test
	void contextLoads() {
		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("esportivos");
		
		CarroDTO c = service.save(carro);
		
		assertNotNull(c);
		Long id = c.getId();
		assertNotNull(id);
		
		// Buscar o objeto
		c = service.getCarroById(id);
		assertNotNull(c);
		
		assertEquals("Ferrari", c.getNome());
		assertEquals("esportivos", c.getTipo());
		
		// Deletar o objeto
		service.delete(id);
//		rest.withBasicAuth("admin", "admin").delete("locale");
		
		// Verificar se deletou
		try {
			assertNotNull(service.getCarroById(id));
			fail("O carro não foi excluído!");
		} catch (ObjectNotFoundException e) {
			// Ok
		}
	}
	
	@Test
	public void testLista() {
		
		List<CarroDTO> carros = service.getCarros();
		
		assertEquals(33, carros.size());
	}
	
	@Test
	public void testGet() {
		
		CarroDTO c = service.getCarroById(11L);
		assertNotNull(c);
		
		assertEquals("Ferrari FF", c.getNome());
	}

	
}
