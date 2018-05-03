package com.exemplo.vinhos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemplo.vinhos.model.Vinho;

public interface Vinhos extends JpaRepository<Vinho, Long> {

}
