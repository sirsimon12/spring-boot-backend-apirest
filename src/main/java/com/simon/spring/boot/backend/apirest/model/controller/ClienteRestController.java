package com.simon.spring.boot.backend.apirest.model.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simon.spring.boot.backend.apirest.model.entity.Cliente;
import com.simon.spring.boot.backend.apirest.model.services.IClienteService;

//Aqui configura un Cross , se establece que urls le permite acceder, a que metodos que headers, etc.
@CrossOrigin(origins = {"http://localhost:4200"})
//Esta etiqueta indica que sera un controlador para un servicio REST.
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;

	@GetMapping("/clientes")
	public List<Cliente> listCliente(){
		return clienteService.findAll();
	}

	@GetMapping("/clientes/{id}")
	public Cliente show(@PathVariable Long id){
		return clienteService.findById(id);
	}

	@PostMapping("/clientes") //el request body indica qeu el objeto viaja en el cuerpo del request.
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente create(@RequestBody Cliente cliente) {
		return clienteService.save(cliente);
	}

	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {
		Cliente clienteEnBD = clienteService.findById(id);
		if(clienteEnBD!=null) {
			clienteEnBD.setApellido(cliente.getApellido());
			clienteEnBD.setEmail(cliente.getEmail());
			clienteEnBD.setNombre(cliente.getNombre());

			return clienteService.save(clienteEnBD);

		} else {
			return null;
		}
	}

	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delte(@PathVariable Long id) {
		clienteService.delete(id);
	}
}
