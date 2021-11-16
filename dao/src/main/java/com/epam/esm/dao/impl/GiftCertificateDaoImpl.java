package com.epam.esm.dao.impl;

import com.epam.esm.dao.ConstantQuery;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.dto.SortDataDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import java.util.List;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean add(GiftCertificate giftCertificate) {
        return (entityManager.merge(giftCertificate) != null);
    }

    @Transactional
    @Override
    public boolean addCertificateToUser(Order order) {
        return (entityManager.merge(order) != null);
    }

    @Override
    public GiftCertificate find(Long id) {
        return entityManager.find(GiftCertificate.class, id);
    }

    @Transactional
    @Override
    public List<GiftCertificate> findAll(Integer limit, Integer offset) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery(ConstantQuery.FIND_ALL_GIFT_CERTIFICATES_QUERY,
                GiftCertificate.class);
        return query.setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Transactional
    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        entityManager.createQuery(ConstantQuery.DELETE_USERS_FROM_CERTIFICATE_BY_CERTIFICATE_ID_QUERY)
                .setParameter(ConstantQuery.CERTIFICATE_ID_PARAMETER_NAME, id)
                .executeUpdate();
        entityManager.createQuery(ConstantQuery.DELETE_TAG_FROM_CERTIFICATES_BY_CERTIFICATE_ID_QUERY)
                .setParameter(ConstantQuery.CERTIFICATE_ID_PARAMETER_NAME, id)
                .executeUpdate();
        int affectedRowNumber = entityManager.createQuery(ConstantQuery.DELETE_GIFT_CERTIFICATE_QUERY)
                .setParameter(ConstantQuery.CERTIFICATE_ID_PARAMETER_NAME, id)
                .executeUpdate();
        return (affectedRowNumber == 1);
    }

    @Override
    public List<GiftCertificate> findByTagName(String tagName) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery(ConstantQuery.FIND_CERTIFICATE_BY_TAG_NAME_QUERY,
                GiftCertificate.class);
        return query.setParameter(ConstantQuery.TAG_NAME_PARAMETER_NAME, tagName).getResultList();
    }

    @Override
    public List<GiftCertificate> findByNameAndDescription(GiftCertificate certificate, Integer limit, Integer offset) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery(
                ConstantQuery.FIND_CERTIFICATES_BY_PART_OF_NAME_AND_DESCRIPTION_QUERY, GiftCertificate.class);
        return query.setParameter(ConstantQuery.CERTIFICATE_NAME_PARAMETER_NAME,
                ConstantQuery.PERCENT_VALUE + certificate.getName() + ConstantQuery.PERCENT_VALUE)
                .setParameter(ConstantQuery.CERTIFICATE_DESCRIPTION_PARAMETER_NAME,
                        ConstantQuery.PERCENT_VALUE + certificate.getDescription() + ConstantQuery.PERCENT_VALUE)
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public List<GiftCertificate> findSorted(SortDataDto sortData) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Path<String> path = criteriaQuery.from(GiftCertificate.class).get(sortData.getSortingParameter());
        javax.persistence.criteria.Order order = sortData.getDescending()
                ? criteriaBuilder.desc(path) : criteriaBuilder.asc(path);
        criteriaQuery.orderBy(order);
        TypedQuery<GiftCertificate> limitedCriteriaQuery = entityManager.createQuery(criteriaQuery)
                .setFirstResult(sortData.getOffset()).setMaxResults(sortData.getLimit());
        return limitedCriteriaQuery.getResultList();
    }

    @Override
    public List<GiftCertificate> findCertificatesByTags(Integer limit, Integer offset, String... tagNames) {
        TypedQuery<GiftCertificate> query = entityManager.createQuery(
                ConstantQuery.FIND_CERTIFICATE_BY_TAGS_QUERY, GiftCertificate.class);
        query.setParameter(ConstantQuery.ZERO_INDEX, tagNames[ConstantQuery.ZERO_INDEX])
                .setParameter(ConstantQuery.ONE_INDEX, tagNames[ConstantQuery.ONE_INDEX]);
        return query.setFirstResult(offset).setMaxResults(limit).getResultList();
    }

}
