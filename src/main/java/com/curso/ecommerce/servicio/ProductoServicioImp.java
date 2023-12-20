package com.curso.ecommerce.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.entidades.Producto;
import com.curso.ecommerce.repositorio.IProductoRepositorio;

@Service
public class ProductoServicioImp implements IProductoServicio{

	@Autowired
	private IProductoRepositorio productoRepositorio; 
	

	
	@Override
	public Producto save(Producto producto) {
		return productoRepositorio.save(producto) ;
	}

	@Override
	public Optional<Producto> get(Integer id) {
		
		return productoRepositorio.findById(id);
	}

	@Override
	public void update(Producto producto) {
		productoRepositorio.save(producto);
	}

	@Override
	public void delete(Integer id) {
		productoRepositorio.deleteById(id);
		
	}
	
	@Override
	public List<Producto> findAll() {
		// TODO Auto-generated method stub
		return productoRepositorio.findAll();
	}
	
}
