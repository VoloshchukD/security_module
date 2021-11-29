package com.epam.esm.service.util;

import com.epam.esm.service.exception.IllegalPageNumberException;

public class PaginationLogics {

    public static final Integer DEFAULT_LIMIT = 5;

    public static final Integer DEFAULT_PAGE = 1;

    public static Integer convertPage(Integer pageNumber, Integer itemCount) throws IllegalPageNumberException {
        if (pageNumber <= 0) {
            throw new IllegalPageNumberException(ExceptionMessageHandler.INVALID_PAGE_MESSAGE_NAME);
        }
        if (pageNumber == null) {
            pageNumber = DEFAULT_PAGE;
        }
        if (itemCount == null) {
            itemCount = DEFAULT_LIMIT;
        }
        return pageNumber - 1;
    }

}
