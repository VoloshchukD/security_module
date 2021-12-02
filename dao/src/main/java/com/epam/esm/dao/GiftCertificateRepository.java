package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {

    List<GiftCertificate> findGiftCertificatesByNameAndDescription(String name, String description, Pageable pageable);

    @Query("SELECT g FROM GiftCertificate g JOIN g.tags t WHERE t.name = :tagName")
    List<GiftCertificate> findAllByTagName(@Param("tagName") String tagName);

    @Query("SELECT DISTINCT g FROM GiftCertificate g " +
            "JOIN g.tags t WHERE t.name = ?1 AND g.id IN (SELECT DISTINCT g.id " +
            "FROM GiftCertificate g JOIN g.tags t WHERE t.name = ?2)")
    List<GiftCertificate> findAllByTagNames(Pageable pageable, String... tagNames);

}
