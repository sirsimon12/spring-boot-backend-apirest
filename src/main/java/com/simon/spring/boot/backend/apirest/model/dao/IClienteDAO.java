package com.simon.spring.boot.backend.apirest.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.simon.spring.boot.backend.apirest.model.entity.Cliente;

/**
 * Estas clases es un DAO o tambien llamada como Repository.
 * spring tiene un mecanismo robusto que permite tener operaciones CRUD con tan solo extender
 * CrudRepoitory.
 * 
 * @author jpsimon
 *
 */
public interface IClienteDAO extends CrudRepository<Cliente, Long>{

}
