package com.rockgustavo.domain.dto;

import org.modelmapper.ModelMapper;

import com.rockgustavo.domain.Carro;

import lombok.Data;

@Data
public class CarroDTO {
	
	private Long id;
	
	private String nome;
	private String tipo;
	
	public static CarroDTO create(Carro c) {
		ModelMapper modelMapper = new ModelMapper();
		CarroDTO carDTO = modelMapper.map(c, CarroDTO.class);
		
		return carDTO;
	}
}
