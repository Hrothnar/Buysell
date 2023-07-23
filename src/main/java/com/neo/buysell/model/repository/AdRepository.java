package com.neo.buysell.model.repository;

import com.neo.buysell.model.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdRepository extends JpaRepository<Ad, Long> {
    Optional<Ad> findById(long id);
    List<Ad> findAll();
    boolean deleteAdById(long id);

}
