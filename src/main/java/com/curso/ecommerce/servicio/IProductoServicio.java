package com.curso.ecommerce.servicio;

import java.util.List;
import java.util.Optional;

import com.curso.ecommerce.entidades.Producto;

public interface IProductoServicio {
	public Producto save(Producto producto);
	public Optional<Producto> get(Integer id);
	public void update(Producto producto);
	public void delete (Integer id);
	public List<Producto> findAll();
}
