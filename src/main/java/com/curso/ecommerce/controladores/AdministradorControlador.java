package com.curso.ecommerce.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.curso.ecommerce.entidades.Producto;
import com.curso.ecommerce.servicio.IProductoServicio;

@Controller
@RequestMapping("/administrador")
public class AdministradorControlador {

	@Autowired
	private IProductoServicio productoServicio;
	
	@GetMapping("")
	public String home(Model model) {
		
		//video 18
		List<Producto> productos = productoServicio.findAll();
		model.addAttribute("productos", productos);
		
		//video iniciales
		return "administrador/homeAdmin";
	}
	
	
}
