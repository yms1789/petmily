package com.pjt.petmily.domain.pet.walk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalkRepository extends JpaRepository<Walk, Long> {
    List<Walk> findAllByPet_PetId(Long petId);
}