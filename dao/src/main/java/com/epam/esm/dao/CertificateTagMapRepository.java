package com.epam.esm.dao;

import com.epam.esm.entity.CertificateTagMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateTagMapRepository extends JpaRepository<CertificateTagMap, Long> {

    long deleteCertificateTagMapByCertificate_IdAndTag_Id(Long certificateId, Long tagId);

}
