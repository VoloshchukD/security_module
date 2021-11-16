package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.dto.SortDataDto;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;

import java.util.List;

/**
 * Base interface with logics specific for {@link GiftCertificate}.
 *
 * @author Daniil Valashchuk
 */
public interface GiftCertificateService extends BaseService<GiftCertificate> {

    /**
     * Method that binds certificate and user.
     *
     * @param certificateId - certificate identifier for binding
     * @param userId        - user identifier for binding
     * @return boolean result of binding
     * @throws {@link ParameterNotPresentException}
     * @throws {@link DataNotFoundException} when some of entities do not exist
     */
    boolean addCertificateToUser(Long certificateId, Long userId)
            throws ParameterNotPresentException, DataNotFoundException;

    /**
     * Method for seeking {@link GiftCertificate}s by part of {@link Tag} name.
     *
     * @param tagName - target certificate identifier
     * @return list of {@link GiftCertificate} matching search condition
     */
    List<GiftCertificate> findByTagName(String tagName);

    /**
     * Method for seeking {@link GiftCertificate}s by part of its name and description.
     *
     * @param certificate - certificate data
     * @param page        - required page number with data
     * @param itemCount   - required items amount for the page
     * @return list of {@link GiftCertificate} matching search condition
     * @throws {@link IllegalPageNumberException} when page number is invalid
     */
    List<GiftCertificate> findByNameAndDescription(GiftCertificate certificate, Integer page, Integer itemCount)
            throws IllegalPageNumberException;

    /**
     * Method for receiving sorted {@link GiftCertificate}s.
     *
     * @param sortData - data required for ordering
     * @param page     - required page number with data
     * @return ordered list of {@link GiftCertificate}
     * @throws {@link IllegalPageNumberException} when page number is invalid
     */
    List<GiftCertificate> findSorted(SortDataDto sortData, Integer page)
            throws IllegalPageNumberException;

    /**
     * Method for receiving {@link GiftCertificate}s by several tag names.
     *
     * @param page      - required page number with data
     * @param itemCount - required items amount at the page
     * @param tagNames  - tag names for search
     * @return list of {@link GiftCertificate}
     * @throws {@link IllegalPageNumberException} when page number is invalid
     */
    List<GiftCertificate> findCertificatesByTags(Integer page, Integer itemCount, String... tagNames)
            throws IllegalPageNumberException;

}
