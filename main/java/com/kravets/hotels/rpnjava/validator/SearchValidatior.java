package com.kravets.hotels.rpnjava.validator;

import com.kravets.hotels.rpnjava.form.SearchForm;
import com.kravets.hotels.rpnjava.misc.CurrentDate;
import com.kravets.hotels.rpnjava.service.IndexPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.ParseException;

@Service
public class SearchValidatior implements Validator {
    private final IndexPageService indexPageService;

    @Autowired
    public SearchValidatior(IndexPageService indexPageService) {
        this.indexPageService = indexPageService;
    }

    @Override
    public boolean supports(Class c) {
        return SearchForm.class.equals(c);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SearchForm searchForm = (SearchForm) target;

        System.out.println(searchForm.getCheckInDate().toString());
        try {
            System.out.println(CurrentDate.getDate().toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (!indexPageService.getAllEnabledCitiesIds().contains(searchForm.getCity())) {
            errors.rejectValue("city", "1");
        }
        if (searchForm.getAdultsNumber() < 1 || searchForm.getAdultsNumber() > 30) {
            errors.rejectValue("adultsNumber", "1");
        }
        if (searchForm.getChildrenNumber() < 0 || searchForm.getChildrenNumber() > 30) {
            errors.rejectValue("adultsNumber", "1");
        }
        try {
            if (searchForm.getCheckInDate().after(searchForm.getCheckOutDate())
                    || searchForm.getCheckInDate().before(CurrentDate.getDate())
            ) {
                throw new Exception();
            }
        } catch (Exception e) {
            errors.rejectValue("checkInDate", "1");
            errors.rejectValue("checkOutDate", "1");
        }


    }

}
