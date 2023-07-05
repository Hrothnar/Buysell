package com.neo.buysell.repository;

import com.neo.buysell.model.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdRepository extends JpaRepository<Ad, Long> {

    List<Ad> findAll();
    Optional<Ad> findById(long id);
    boolean deleteAdById(long id);

}
