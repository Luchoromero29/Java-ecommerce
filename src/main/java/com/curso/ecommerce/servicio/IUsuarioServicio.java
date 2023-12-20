package com.curso.ecommerce.servicio;

import java.util.Optional;

import com.curso.ecommerce.entidades.Usuario;

//video 30
public interface IUsuarioServicio {
	Optional<Usuario> findById(Integer id);
}
