package com.pjt.petmily.domain.curation.repository;

import com.pjt.petmily.domain.curation.entity.Curationbookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface UserCurationRepository extends JpaRepository<Curationbookmark, Long> {
//    List<Curationbookmark> findByUserEmail(String userEamil);
}
