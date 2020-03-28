package com.simon.spring.boot.backend.apirest.model.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@CrossOrigin(origins = { "http://localhost:4200" })
//Esta etiqueta indica que sera un controlador para un servicio REST.
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;

	@GetMapping("/clientes")
	public List<Cliente> listCliente() {
		return clienteService.findAll();
	}

	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		System.out.println("Voy a buscar al cliente: " + id);

		Map<String, Object> response = new HashMap<>();
		ResponseEntity respEn = null;

		Cliente cli = null;

		try {
			cli = clienteService.findById(id);

			if (cli == null) {
				response.put("mensaje", "El cliente ID: ".concat(id.toString()).concat(" no existe en la BD"));
				respEn = new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			} else {
				respEn = new ResponseEntity<Cliente>(cli, HttpStatus.OK);
			}

		} catch (DataAccessException ex) {

			response.put("mensaje", "ERROR: " + ex.getMessage());
			response.put("error", ex.getMostSpecificCause().getMessage());
			respEn = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return respEn;
	}

	@PostMapping("/clientes") // el request body indica qeu el objeto viaja en el cuerpo del request.
	// @ResponseStatus(HttpStatus.CREATED) aqui es cuando es por default
	public ResponseEntity<?> create(@RequestBody Cliente cliente) {
		Cliente clienteNew = null;
		Map<String, Object> response = new HashMap<>();
		ResponseEntity respEn = null;

		try {
			clienteNew = clienteService.save(cliente);
			response.put("mensaje", "El cliente ha sido creado con exito!!!");
			response.put("cliente", clienteNew);

			respEn = new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException ex) {
			response.put("mensaje", "ERROR: al realizar el insert");
			response.put("error", ex.getMostSpecificCause().getMessage());
			respEn = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return respEn;
	}

	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id) {
		Cliente clienteEnBD = clienteService.findById(id);
		Cliente clienteUpdated = null;
		Map<String, Object> response = new HashMap<>();
		ResponseEntity respEn = null;

		if (clienteEnBD != null) {
			try {
				clienteEnBD.setApellido(cliente.getApellido());
				clienteEnBD.setEmail(cliente.getEmail());
				clienteEnBD.setNombre(cliente.getNombre());

				clienteUpdated = clienteService.save(clienteEnBD);

				response.put("mensaje", "El cliente se actualizo con exito");
				response.put("cliente", clienteUpdated);

				respEn = new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

			} catch (DataAccessException ex) {
				response.put("mensaje", "ERROR: al realizar el update");
				response.put("error", ex.getMostSpecificCause().getMessage());
				respEn = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

			}
		} else {
			response.put("mensaje", "El cliente ID: ".concat(id.toString()).concat(" no existe en la BD"));
			respEn = new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return respEn;
	}

	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delte(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		ResponseEntity respEn = null;
		try {
			clienteService.delete(id);
			response.put("mensaje", "El cliente se elimino con exito");
			respEn = new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (DataAccessException ex) {
			response.put("mensaje", "ERROR: al realizar la eliminacion");
			response.put("error", ex.getMostSpecificCause().getMessage());
			respEn = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return respEn;
	}
}
