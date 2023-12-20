package com.curso.ecommerce.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.entidades.Usuario;
import com.curso.ecommerce.repositorio.IUsuarioRepositorio;

//video 30
@Service
public class UsuarioServicioImp implements IUsuarioServicio{
	
	@Autowired
	private IUsuarioRepositorio usuarioRepositorio;
	
	@Override
	public Optional<Usuario> findById(Integer id) {
		return usuarioRepositorio.findById(id);  
	}

}
