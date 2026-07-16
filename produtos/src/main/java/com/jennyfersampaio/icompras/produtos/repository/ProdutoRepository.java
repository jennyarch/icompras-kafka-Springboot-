package com.jennyfersampaio.icompras.produtos.repository;

import com.jennyfersampaio.icompras.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
