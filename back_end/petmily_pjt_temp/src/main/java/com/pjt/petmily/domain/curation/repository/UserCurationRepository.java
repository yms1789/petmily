package com.pjt.petmily.domain.curation.repository;

import com.pjt.petmily.domain.curation.dto.CurationBookmarkDto;
import com.pjt.petmily.domain.curation.entity.Curation;
import com.pjt.petmily.domain.curation.entity.Curationbookmark;
import com.pjt.petmily.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserCurationRepository extends JpaRepository<Curationbookmark, Long> {
    List<Curationbookmark> findByUser_UserId(Long userId);
    List<Curationbookmark> findByUser(User user);

    List<Curationbookmark> findByUser_UserIdAndCuration_cId(Long userid, Long cid);

    Curationbookmark findByUserAndCuration(User user, Curation curation);


}
