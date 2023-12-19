package com.curso.ecommerce.controladores;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import com.curso.ecommerce.entidades.DetalleOrden;
import com.curso.ecommerce.entidades.Orden;
import com.curso.ecommerce.entidades.Producto;
import com.curso.ecommerce.servicio.ProductoServicio;

@Controller
@RequestMapping("/")
public class HomeControlador {
	
	private final Logger LOGGER = LoggerFactory.getLogger(HomeControlador.class); 
	
	
	//video 19
	@Autowired
	private ProductoServicio productoServicio;
	
	List<DetalleOrden> detalles = new ArrayList<>(); //para almacenar los detalles de la orden - video 24
	
	//almacena los datos de la orden - video 24
	Orden orden = new Orden();
	
	
	
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
	//video 23
	@PostMapping("/cart")
	public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
		
		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = new Producto();
		double sumaTotal = 0;
		
		//video 25
		Optional<Producto> optionalProducto = productoServicio.get(id);
		LOGGER.info("Producto aniadido: {}", optionalProducto.get());	
		LOGGER.info("Cantidad: {}", cantidad);
		producto = optionalProducto.get();
		
		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(producto.getPrecio());
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setTotal(producto.getPrecio()*cantidad);
		detalleOrden.setProducto(producto);
		
		detalles.add(detalleOrden);
		
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();// calcula el total del carrito
		orden.setTotal(sumaTotal);
		
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		return "usuario/carrito";
	}
}
