package com.rockgustavo.api;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rockgustavo.domain.Carro;
import com.rockgustavo.domain.CarroService;
import com.rockgustavo.domain.dto.CarroDTO;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

	@Autowired
	private CarroService service;

	@GetMapping
	public ResponseEntity<List<CarroDTO>> get() {
//		return new ResponseEntity<>(service.getCarros(), HttpStatus.OK);
		return ResponseEntity.ok(service.getCarros());
	}

	@GetMapping("/{id}")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<CarroDTO> getById(@PathVariable("id") Long id) {
		CarroDTO carro = service.getCarroById(id);

		return ResponseEntity.ok(carro);

//		if (carro.isPresent()) {
//			return ResponseEntity.ok(carro.get());
//		} else {
//			return ResponseEntity.notFound().build();
//		}

	}

	@GetMapping("/tipo/{tipo}")
	public ResponseEntity<List<CarroDTO>> getByTipo(@PathVariable("tipo") String tipo) {
		List<CarroDTO> carros = service.getCarroByTipo(tipo);

		return carros.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(carros);

	}

	@PostMapping
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<List<CarroDTO>> salvar(@RequestBody Carro carro) {
		CarroDTO c = service.save(carro);

		URI location = getUri(c.getId());
		return ResponseEntity.created(location).build();

	}

	private URI getUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}

	@PutMapping("/{id}")
	public ResponseEntity<CarroDTO> update(@PathVariable("id") Long id, @RequestBody Carro carro) {
		try {
			carro.setId(id);

			CarroDTO c = service.update(carro, id);
			return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CarroDTO> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();

	}

}
