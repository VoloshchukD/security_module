package com.epam.esm.dao;

import com.epam.esm.entity.CertificateTagMap;
import com.epam.esm.entity.Tag;

public interface TagDao extends BaseDao<Tag> {

    boolean addTagToCertificate(CertificateTagMap certificateTagMap);

    Tag findPopularTag(Long userId);

    boolean deleteTagFromCertificate(Long certificateId, Long tagId);

}
