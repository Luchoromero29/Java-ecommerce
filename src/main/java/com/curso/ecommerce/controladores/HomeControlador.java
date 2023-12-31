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
import com.curso.ecommerce.entidades.Usuario;
import com.curso.ecommerce.servicio.IProductoServicio;
import com.curso.ecommerce.servicio.IUsuarioServicio;

@Controller
@RequestMapping("/")
public class HomeControlador {

	private final Logger LOGGER = LoggerFactory.getLogger(HomeControlador.class);

	// video 19
	@Autowired
	private IProductoServicio productoServicio;
	
	@Autowired
	private IUsuarioServicio usuarioService;

	List<DetalleOrden> detalles = new ArrayList<>(); // para almacenar los detalles de la orden - video 24

	// almacena los datos de la orden - video 24
	Orden orden = new Orden();

	// video 19
	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("productos", productoServicio.findAll()); // nos muestra los productos al cargar
		return "usuario/homeUser";
	}

	// video 21
	@GetMapping("productohome/{id}")
	public String productoHome(@PathVariable Integer id, Model model) { // model video 22
		LOGGER.info("Id producto enviado como parametro {}", id);

		Producto producto = new Producto();
		Optional<Producto> productoOptional = productoServicio.get(id);
		producto = productoOptional.get();

		model.addAttribute("producto", producto);

		return "usuario/productohome";
	}

	// video 23
	@PostMapping("/cart")
	public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {

		DetalleOrden detalleOrden = new DetalleOrden();
		Producto producto = new Producto();
		double sumaTotal = 0;

		// video 25
		Optional<Producto> optionalProducto = productoServicio.get(id);
		LOGGER.info("Producto aniadido: {}", optionalProducto.get());
		LOGGER.info("Cantidad: {}", cantidad);
		producto = optionalProducto.get();

		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(producto.getPrecio());
		detalleOrden.setNombre(producto.getNombre());
		detalleOrden.setTotal(producto.getPrecio() * cantidad);
		detalleOrden.setProducto(producto);
		
		//validar producto repetido al aniadir
		Integer idProducto = producto.getId();
		boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);
		
		if(!ingresado) {
			detalles.add(detalleOrden);
		}else {
			//aumento la cantidad del producto que ya estaba en el carrito (se lo agregue yo)
			for (DetalleOrden dOrden : detalles) {
				if (dOrden.getProducto().getId() == idProducto) {
					double sumaCantidades = dOrden.getCantidad() + detalleOrden.getCantidad();
					if(sumaCantidades <= 5) {
						dOrden.setCantidad(sumaCantidades);
						dOrden.setTotal(dOrden.getCantidad() * dOrden.getPrecio());
					}
				}
			}
			
		}

		

		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();// calcula el total del carrito
		orden.setTotal(sumaTotal);

		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);

		return "usuario/carrito";
	}

	// video 27
	@GetMapping("/delete/cart/{id}")
	public String deleteProductCart(@PathVariable Integer id, Model model) {

		// liesta nueva de productos
		List<DetalleOrden> ordenNueva = new ArrayList<DetalleOrden>();

		for (DetalleOrden detalleOrden : detalles) {
			if (detalleOrden.getProducto().getId() != id) {
				ordenNueva.add(detalleOrden);
			}
			;
		}
		// nueva lista con los productos restantes
		detalles = ordenNueva;

		double sumaTotal = 0;
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();// calcula el total del - son funciones
																				// lambda
		orden.setTotal(sumaTotal);

		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		
		return "usuario/carrito";
	}
	
	//video 28
	@GetMapping("/getCart")
	public String getCart(Model model) {
		
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		return "/usuario/carrito";
	}
	
	//video 29
	@GetMapping("/orden")
	public String orden(Model model) {
		
		Usuario usuario = usuarioService.findById(1).get();
		
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		model.addAttribute("usuario", usuario);
		
		return "/usuario/resumenorden";
	}
}
