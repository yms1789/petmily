package com.pjt.petmily.domain.curation.repository;

import com.pjt.petmily.domain.curation.entity.Curation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CurationRepository extends JpaRepository<Curation, Long> {

    boolean existsBycTitle(String title);


    List<Curation> findBycPetSpecies(String species);

    List<Curation> findBycCategory(String species);

    Optional<Curation> findBycId(Long cId);

}
