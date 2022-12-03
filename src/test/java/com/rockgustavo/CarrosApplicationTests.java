package com.rockgustavo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rockgustavo.domain.Carro;
import com.rockgustavo.domain.CarroService;
import com.rockgustavo.domain.dto.CarroDTO;

@SpringBootTest
class CarrosApplicationTests {

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
		Optional<CarroDTO> op = service.getCarroById(id);
		assertTrue(op.isPresent());
		
		c = op.get();
		assertEquals("Ferrari", c.getNome());
		assertEquals("esportivos", c.getTipo());
		
		// Deletar o objeto
		service.delete(id);
		
		// Verificar se deletou
		assertFalse(service.getCarroById(id).isPresent());
	}
	
	@Test
	public void testLista() {
		
		List<CarroDTO> carros = service.getCarros();
		
		assertEquals(32, carros.size());
	}
	
	@Test
	public void testGet() {
		
		Optional<CarroDTO> op = service.getCarroById(11L);
		System.out.println(op);
		
		assertTrue(op.isPresent());
		
		CarroDTO c = op.get();
		
		assertEquals("Ferrari FF", c.getNome());
	}

	
}
