package com.curso.ecommerce.controladores;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.entidades.Producto;
import com.curso.ecommerce.servicio.ProductoServicio;

@Controller
@RequestMapping("/")
public class HomeControlador {
	
	private final Logger LOGGER = LoggerFactory.getLogger(HomeControlador.class); 
	
	
	//video 19
	@Autowired
	private ProductoServicio productoServicio;
	
	
	//video 19
	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("productos", productoServicio.findAll()); //nos muestra los productos al cargar
		return "usuario/homeUser";
	}	
	
	//video 21
	@GetMapping("productohome/{id}")
	public String productoHome(@PathVariable Integer id, Model model) { //model video 22
		LOGGER.info("Id producto enviado como parametro {}", id);
		
		Producto producto = new Producto();
		Optional<Producto> productoOptional = productoServicio.get(id);
		producto = productoOptional.get();
		
		model.addAttribute("producto", producto);
		
		return "usuario/productohome";
	}
	
	@PostMapping("/cart")
	public String addCart() {
		return "usuario/carrito";
	}
}
