package com.br.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.exception.ResourceNotFoundException;
import com.br.model.*;
import com.br.repository.*;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

//http://localhost:8080/<request mapping>
//http://meuendereco.com.br/<request mapping>
//http://localhost:8080/usuario/v2.0

@RestController
@RequestMapping("/usuario/v2.0/")
@CrossOrigin(origins = "*")
public class UsuarioV2 {
	
	
	@Autowired
	private UsuarioRepository usuarioRep;
	
	
	//GET > http://localhost:8080/usuario/v2.0/usuario
	@GetMapping("/usuario/{key}")
	public List<Usuario> listar(@PathVariable String key){
		
		String validKey = "key=e19bce1d92fd7c16592b976368c0169f";
		
		if (!key.equals(validKey)) {
			throw new ResourceNotFoundException("Chave inválida");
		}
		
		return this.usuarioRep.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}
	
	
//	//GET > http://localhost:8080/usuario/v2.0/usuario/<id>
//	@GetMapping("/usuario/{id}")
//	public ResponseEntity<Usuario> consultar(@PathVariable Long id){
//		
//		Usuario user = this.usuarioRep.findById(id)
//				.orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado - ID: " + id) );
//							
//		return ResponseEntity.ok(user);
//		
//	}
	
	//POST > http://localhost:8080/usuario/v2.0/usuario
	@PostMapping("/usuario")
	public Usuario inserir(@RequestBody Usuario usuario) {
		
		return this.usuarioRep.save(usuario);
	}
	
	
	//DELETE > http://localhost:8080/usuario/v2.0/usuario
	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<Map<String, Boolean>> excluir(@PathVariable Long id) {
		

		Usuario user = this.usuarioRep.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado - ID: " + id) );
		
		this.usuarioRep.delete(user);
		
		Map<String, Boolean> resposta = new HashMap<>();
		resposta.put("Usuario excluido com sucesso!", Boolean.TRUE);
							
		return ResponseEntity.ok(resposta);
		
		
	}
	
	
	//PUT > http://localhost:8080/usuario/v2.0/usuario/<id>
	@PutMapping("/usuario/{id}")
	public ResponseEntity<Usuario> alterar(@PathVariable Long id, @RequestBody Usuario user) {
		
		Usuario usuario = this.usuarioRep.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado - ID: " + id) );
		
		usuario.setId(user.getId());
		usuario.setNome(user.getNome());
		usuario.setEmail(user.getEmail());
		
		Usuario atualizado = this.usuarioRep.save(usuario);
		
		return ResponseEntity.ok(atualizado);
		
		
	}
	

}
