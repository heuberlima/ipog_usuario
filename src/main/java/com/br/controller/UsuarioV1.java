package com.br.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.exception.ResourceNotFoundException;
import com.br.model.*;
import com.br.repository.*;

//http://localhost:8080/<request mapping>
//http://meuendereco.com.br/<request mapping>
//http://localhost:8080/usuariocontroller

@RestController
@RequestMapping("/usuario/v1.0/")
@CrossOrigin(origins = "*")
public class UsuarioV1 {
	
	
	@Autowired
	private UsuarioRepository usuarioRep;
	
	
	//GET > http://localhost:8080/usuario/v1.0/listar
	@GetMapping("/listar")
	public List<Usuario> listar(){
		
		return this.usuarioRep.findAll();
	}
	
	//GET > http://localhost:8080/usuario/v1.0/consultar/<id>
	@GetMapping("/consultar/{id}")
	public ResponseEntity<Usuario> consultar(@PathVariable Long id){
		
		Usuario user = this.usuarioRep.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado - ID: " + id) );
							
		return ResponseEntity.ok(user);
		
	}
	
	//POST > http://localhost:8080/usuario/v1.0/inserir
	@PostMapping("/inserir")
	public Usuario inserir(@RequestBody Usuario usuario) {
		
		return this.usuarioRep.save(usuario);
	}
	
	
	//DELETE > http://localhost:8080/usuario/v1.0/excluir
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Map<String, Boolean>> excluir(@PathVariable Long id) {
		

		Usuario user = this.usuarioRep.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado - ID: " + id) );
		
		this.usuarioRep.delete(user);
		
		Map<String, Boolean> resposta = new HashMap<>();
		resposta.put("Usuario excluido com sucesso!", Boolean.TRUE);
							
		return ResponseEntity.ok(resposta);
		
		
	}
	
		

}
