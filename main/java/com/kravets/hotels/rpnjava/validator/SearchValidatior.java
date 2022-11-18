package com.kravets.hotels.rpnjava.validator;

import com.kravets.hotels.rpnjava.data.form.SearchForm;
import com.kravets.hotels.rpnjava.misc.DateUtils;
import com.kravets.hotels.rpnjava.misc.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class SearchValidatior implements Validator {
    private final Services services;

    @Autowired
    public SearchValidatior(Services services) {
        this.services = services;
    }

    @Override
    public boolean supports(Class c) {
        return SearchForm.class.equals(c);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SearchForm searchForm = (SearchForm) target;

        if (!services.cities.getEnabledCitiesIds().contains(searchForm.getCity())) {
            errors.rejectValue("city", "1");
        }
        if (searchForm.getAdultsCount() < 1 || searchForm.getAdultsCount() > 30) {
            errors.rejectValue("adultsCount", "1");
        }
        if (searchForm.getChildrenCount() < 0 || searchForm.getChildrenCount() > 30) {
            errors.rejectValue("adultsCount", "1");
        }
        try {
            if (searchForm.getCheckInDate().compareTo(searchForm.getCheckOutDate()) >= 0 ||
                    searchForm.getCheckInDate().compareTo(DateUtils.getCurrentDate()) > 0
            ) {
                throw new Exception();
            }
        } catch (Exception e) {
            errors.rejectValue("checkInDate", "1");
            errors.rejectValue("checkOutDate", "1");
        }


    }

}
