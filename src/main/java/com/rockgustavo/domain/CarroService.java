package com.rockgustavo.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.rockgustavo.domain.dto.CarroDTO;

@Service
public class CarroService {

	@Autowired
	private CarroRepository repository;

	public List<CarroDTO> getCarros() {
		List<Carro> carros = repository.findAll();
		
		List<CarroDTO> list = carros.stream().map(c -> CarroDTO.create(c)).collect(Collectors.toList());
		
//		List<CarroDTO> list = new ArrayList<>();
//		for(Carro c : carros) {
//			list.add(new CarroDTO(c));
//		}
		return list;
	}

	public List<CarroDTO> getCarroByTipo(String tipo) {

		return repository.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
	}

	public Optional<CarroDTO> getCarroById(long id) {
//		Optional<Carro> carro = repository.findById(id);
//		if(carro.isPresent()) {
//			return Optional.of(new CarroDTO(carro.get()));
//		}else{
//			return null;
//		}
//		C/ lambdas
//		return carro.map(c -> Optional.of(new CarroDTO(carro.get())).orElse(null));

		return repository.findById(id).map(CarroDTO::create); //.map(c -> new CarroDTO(c))
		

	}

	public CarroDTO save(Carro carro) {
		Assert.isNull(carro.getId(), "Não foi possível atualizar o registro");
		
		return CarroDTO.create(repository.save(carro));
	}

	public CarroDTO update(Carro carro, Long id) {
		Assert.notNull(id, "ID NULO: Não foi possível atualizar o registro");

		// Busca o carro no banco de dados
		Optional<Carro> optional = repository.findById(id);
		if (optional.isPresent()) {
			Carro db = optional.get();
			// Copiar as propriedades
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			System.out.println("Carro ID: " + db.getId());

			// Atualiza o carro
			repository.save(db);
			return CarroDTO.create(db);
		} else {
			throw new RuntimeException("RuntimeException: Não foi possível atualizar o Registro");
		}
	}

	public boolean delete(Long id) {
		// Busca o carro no banco de dados
		Optional<CarroDTO> optional = getCarroById(id);
		if (optional.isPresent()) {
			repository.deleteById(id);
			return true;
		}
		return false;
		
	}

	/*
	public List<Carro> getCarrosFake() {
		List<Carro> carros = new ArrayList<>();

		carros.add(new Carro(1L, "Fusca", "classicos"));
		carros.add(new Carro(2L, "Brasilia", "classicos"));
		carros.add(new Carro(3L, "Chevete", "classicos"));

		return carros;
	}*/

}
