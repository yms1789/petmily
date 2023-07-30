package com.pjt.petmily.domain.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findByPetId(Long petId);
}