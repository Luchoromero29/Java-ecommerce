package com.curso.ecommerce.controladores;

import org.slf4j.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.entidades.Producto;
import com.curso.ecommerce.entidades.Usuario;
import com.curso.ecommerce.servicio.ProductoServicio;


@Controller
@RequestMapping("/productos")
public class ProductoControlador {
	
	//video 10
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoControlador.class);
	
	@Autowired
	private ProductoServicio productoServicio;
	
	
	@GetMapping("")
	public String show() {
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	
	//video 10
	@PostMapping("/save")
	public String save(Producto producto) {
		
		LOGGER.info("este es el objeto producto {}", producto);
		Usuario u = new Usuario(1, "", "" , "" , "" , "" , "" , "");
		producto.setUsuario(u);
		
		productoServicio.save(producto);
		return "redirect:/productos";
	}
	
	
}
