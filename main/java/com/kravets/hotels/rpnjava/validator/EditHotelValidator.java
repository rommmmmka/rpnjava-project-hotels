package com.kravets.hotels.rpnjava.validator;

import com.kravets.hotels.rpnjava.data.form.AddHotelForm;
import com.kravets.hotels.rpnjava.data.form.EditHotelForm;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class EditHotelValidator implements Validator {

    @Override
    public boolean supports(Class c) {
        return AddHotelForm.class.equals(c);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EditHotelForm addHotelForm = (EditHotelForm) target;

        if (addHotelForm.getName() == null
                || addHotelForm.getName().length() < 6
                || addHotelForm.getName().length() > 45
        ) {
            errors.rejectValue("name", "1");
        }
        if (addHotelForm.getDescription().length() > 300) {
            errors.rejectValue("description", "1");
        }
    }
}
