package com.curso.ecommerce.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.ecommerce.entidades.Producto;

@Repository
public interface IProductoRepositorio  extends JpaRepository<Producto , Integer>{
	
}
