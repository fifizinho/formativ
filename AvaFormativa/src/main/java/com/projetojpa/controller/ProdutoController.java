package com.projetojpa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetojpa.entities.Produto;
import com.projetojpa.services.ProdutoService;

@RestController
@RequestMapping("/api/Produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService ProdutoService;

	@PostMapping
	public ResponseEntity<Produto> salvarProduto(@RequestBody Produto Produto) {
		Produto novoProduto = ProdutoService.salvarProduto(Produto);
		return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<List<Produto>> listarTodos() {
		List<Produto> Produtos = ProdutoService.listarTodos();
		return new ResponseEntity<>(Produtos, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
		Optional<Produto> Produto = ProdutoService.buscarPorId(id);
		return Produto.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto Produto) {
		if (!ProdutoService.buscarPorId(id).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Produto.setId(id);
		Produto ProdutoAtualizado = ProdutoService.atualizarProduto(Produto);
		return new ResponseEntity<>(ProdutoAtualizado, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
		if (!ProdutoService.buscarPorId(id).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		ProdutoService.deletarProduto(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
