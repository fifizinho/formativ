package com.projetojpa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.projetojpa.entities.Produto;
import com.projetojpa.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class ProdutoTesteService {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ProdutoRepository produtoRepository;

	@BeforeEach
	void setUp() {
		produtoRepository.deleteAll();
	}

	@DisplayName("Testando salvar Produto")
	@Test
	void testSalvarProduto() {
		Produto Produto = new Produto(null, "Garrafa", "plastico", 10.000);

		Produto resultado = produtoService.salvarProduto(Produto);

		assertNotNull(resultado);
		assertEquals("Garrafa", resultado.getNome());
		assertTrue(resultado.getId() > 0);
	}

	@DisplayName("Testando listar todos os Produtos")
	@Test
	void testListarTodos() {
		Produto Produto1 = new Produto(null, "Garrafa", "plastico", 10.000);
		Produto Produto2 = new Produto(null, "Vaso", "ceramica", 10.000);

		produtoService.salvarProduto(Produto1);
		produtoService.salvarProduto(Produto2);

		List<Produto> resultado = produtoService.listarTodos();

		assertNotNull(resultado);
		assertEquals(2, resultado.size());
	}

	@DisplayName("Testando Buscar Produto por Id")
	@Test
	void testBuscarPorId() {
		Produto Produto = new Produto(null, "Garrafa", "plastico", 10.000);

		Produto salvo = produtoService.salvarProduto(Produto);
		Optional<Produto> resultado = produtoService.buscarPorId(salvo.getId());

		assertTrue(resultado.isPresent());
		assertEquals("Garrafa", resultado.get().getNome());
		assertEquals("plastico", resultado.get().getDescricao());
		assertEquals(10.000, resultado.get().getPreco());

	}

	@DisplayName("Testando atualizar Produto")
	@Test
	void testAtualizarProduto() {
		Produto Produto = new Produto(null, "Garrafa", "plastico", 10.000);
		Produto salvo = produtoService.salvarProduto(Produto);

		salvo.setNome("Garrafa");
		salvo.setDescricao("plastico");
		salvo.setPreco(10.000);
		
		Produto atualizado = produtoService.salvarProduto(salvo);
		
		assertNotNull(atualizado);
		assertEquals("Garrafa", atualizado.getNome());
		assertEquals("plastico", atualizado.getDescricao());
		assertEquals(10.000, atualizado.getPreco());
	}
	@DisplayName("Testando Delete")
	@Test
	void testDeleteProduto() {
		Produto Produto = new Produto (null, "Garrafa", "plastico", 10.000);
		
		Produto salvo = produtoService.salvarProduto(Produto);
		produtoService.deletarProduto(salvo.getId());
		
		Optional<Produto> resultado = produtoService.buscarPorId(salvo.getId());
		
		assertTrue(resultado.isEmpty());
	}
}
