package com.projetojpa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.projetojpa.entities.Produto;

@DataJpaTest
class ProdutoTesteRepository {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@DisplayName("Testando o Save")
	@Test
	void testSalvarRepository() {
		Produto Produto1 = new Produto(null, "Garrafa","plastico",10.000);
		
		Produto saveProduto = produtoRepository.save(Produto1);
		
		assertNotNull(saveProduto);
		assertTrue(saveProduto.getId()>0);
	}
	@DisplayName("Testando Get para todos os Produtos")
	@Test
	void testGetAllRepository() {
		Produto Produto1 = new Produto (null,"Garrafa","plastico",10.000);
		Produto Produto2 = new Produto (null,"Vaso","ceramica",10.000);
		
		produtoRepository.save(Produto1);
		produtoRepository.save(Produto2);
		
		List<Produto> ProdutoList = produtoRepository.findAll();
		
		assertNotNull(ProdutoList);
		assertEquals(2,ProdutoList.size());
	}
	@DisplayName("Testando Get By ID")
	@Test
	void testGetById() {
		Produto Produto1 = new Produto (null, "Garrafa","plastico",10.000);
		
		produtoRepository.save(Produto1);
		
		Produto saveProduto = produtoRepository.findById(Produto1.getId()).get();
		
		assertNotNull(saveProduto);
		assertEquals(Produto1.getId(),saveProduto.getId());
	}
	@DisplayName("Testando Update")
	@Test
	void testUpdateProduto() {
		Produto Produto1 = new Produto (null, "Garrafa","plastico",10.000);
		
		produtoRepository.save(Produto1);
		
		Produto saveProduto = produtoRepository.findById(Produto1.getId()).get();
		Produto1.setNome("Garrafa");
		Produto1.setDescricao("plastico");
		Produto1.setPreco(10.000);
		
		Produto updateProduto = produtoRepository.save(saveProduto);
		
		assertNotNull(updateProduto);
		assertEquals("Garrafa", updateProduto.getNome());
		assertEquals("plastico", updateProduto.getDescricao());
		assertEquals(10.000, updateProduto.getPreco());
	}
	@DisplayName("Testando Delete")
	@Test
	void testDeleteProduto() {
		Produto Produto1 = new Produto (null,"Garrafa","plastico",10.000);
		
		produtoRepository.save(Produto1);
		
		produtoRepository.deleteById(Produto1.getId());
		
		Optional<Produto> ProdutoOptional = produtoRepository.findById(Produto1.getId());
		
		assertTrue(ProdutoOptional.isEmpty());
	}
}
