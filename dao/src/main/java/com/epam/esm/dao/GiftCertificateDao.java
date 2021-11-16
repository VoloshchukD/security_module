package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.dto.SortDataDto;

import java.util.List;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {

    boolean addCertificateToUser(Order order);

    List<GiftCertificate> findByTagName(String tagName);

    List<GiftCertificate> findCertificatesByTags(Integer limit, Integer offset, String... tagNames);

    List<GiftCertificate> findByNameAndDescription(GiftCertificate certificate, Integer limit, Integer offset);

    List<GiftCertificate> findSorted(SortDataDto sortData);

}
