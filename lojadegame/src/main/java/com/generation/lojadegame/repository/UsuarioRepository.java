package com.generation.lojadegame.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.lojadegame.model.UsuarioModel;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long>{

	public Optional<UsuarioModel> findByUsuario(String usuario);
}

//Mantenha a Atenção aos Detalhes, tome muito cuidado pois se a escrita dos Métodos findBy 
//ou findAllBy estiver errada, pode aparecer um erro no console do STS