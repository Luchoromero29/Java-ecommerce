package com.curso.ecommerce.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.ecommerce.entidades.Usuario;

@Repository
public interface IUsuarioRepositorio extends JpaRepository<Usuario, Integer>{

}
