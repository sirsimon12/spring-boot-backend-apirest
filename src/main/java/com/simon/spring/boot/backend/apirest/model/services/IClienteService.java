package com.simon.spring.boot.backend.apirest.model.services;

import java.util.List;

import com.simon.spring.boot.backend.apirest.model.entity.Cliente;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public Cliente findById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public void delete(Long id);
	
}
