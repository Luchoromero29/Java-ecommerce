package com.curso.ecommerce.controladores;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.curso.ecommerce.entidades.Producto;
import com.curso.ecommerce.entidades.Usuario;
import com.curso.ecommerce.servicio.ProductoServicio;
import com.curso.ecommerce.servicio.SubidaArchivosService;





@Controller
@RequestMapping("/productos")
public class ProductoControlador {
	
	//video 10
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoControlador.class);
	
	@Autowired
	private ProductoServicio productoServicio;
	
	@Autowired
	private SubidaArchivosService upload; //video 16
	
	
	@GetMapping("")
	public String show(Model model ) {
		model.addAttribute("productos", productoServicio.findAll());
		return "productos/show";
	}
	
	@GetMapping("/create")
	public String create() {
		return "productos/create";
	}
	
	//video 10
	@PostMapping("/save")
	public String save(Producto producto,@RequestParam("img") MultipartFile file) throws IOException { //video 16 (multipartfile)
		
		LOGGER.info("este es el objeto producto {}", producto);
		Usuario u = new Usuario(1, "", "" , "" , "" , "" , "" , "");
		producto.setUsuario(u);
		
		//imagen - Video 16
		if (producto.getId() == null) { //si se crea el producto el id viene null
			String nombreImagen = upload.saveImage(file);
			producto.setImagen(nombreImagen);
		} else {
			if (file.isEmpty()) { //cuando se edita el producto pero no cambia la imagen
				Producto p = new Producto();
				p = productoServicio.get(producto.getId()).get();
				producto.setImagen(p.getImagen());
			}else { //se edita el producto y se cambia la imagen
				String nombreImagen = upload.saveImage(file);
				producto.setImagen(nombreImagen);
			}
		}
		
		productoServicio.save(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		
		Producto producto = new Producto();
		Optional<Producto> optionalProducto = productoServicio.get(id);
		producto = optionalProducto.get();
		
		LOGGER.info("Producto buscado: {}", producto );
		model.addAttribute("producto", producto);
		
		return "productos/edit";
	}
	
	@PostMapping("/update")
	public String update(Producto producto) {
		
		productoServicio.update(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		productoServicio.delete(id);
		return "redirect:/productos";
	}
	
}
