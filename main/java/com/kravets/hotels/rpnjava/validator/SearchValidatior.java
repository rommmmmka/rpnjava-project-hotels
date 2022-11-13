package com.kravets.hotels.rpnjava.validator;

import com.kravets.hotels.rpnjava.form.SearchForm;
import com.kravets.hotels.rpnjava.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class SearchValidatior implements Validator {
    private final CityService cityService;

    @Autowired
    public SearchValidatior(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public boolean supports(Class c) {
        return SearchForm.class.equals(c);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SearchForm searchForm = (SearchForm) target;

        if (!cityService.getAllEnabledCitiesIds().contains(searchForm.getCity())) {
            errors.rejectValue("city", "1");
        }
        if (searchForm.getAdultsNumber() < 1 || searchForm.getAdultsNumber() > 30) {
            errors.rejectValue("adultsNumber", "1");
        }
        if (searchForm.getChildrenNumber() < 0 || searchForm.getChildrenNumber() > 30) {
            errors.rejectValue("adultsNumber", "1");
        }
        if (searchForm.getCheckInDate().after(searchForm.getCheckOutDate())) {
            errors.rejectValue("checkInDate", "1");
            errors.rejectValue("checkOutDate", "1");
        }

    }

}
