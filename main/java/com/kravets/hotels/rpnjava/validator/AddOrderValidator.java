package com.kravets.hotels.rpnjava.validator;

import com.kravets.hotels.rpnjava.data.form.AddOrderForm;
import com.kravets.hotels.rpnjava.misc.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class AddOrderValidator implements Validator {

    @Override
    public boolean supports(Class c) {
        return AddOrderForm.class.equals(c);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AddOrderForm addOrderForm = (AddOrderForm) target;

        try {
            if (addOrderForm.getCheckInDate().compareTo(addOrderForm.getCheckOutDate()) >= 0 ||
                    addOrderForm.getCheckInDate().compareTo(DateUtils.getCurrentDate()) < 0
            ) {
                throw new Exception();
            }
        } catch (Exception e) {
            errors.rejectValue("checkInDate", "1");
            errors.rejectValue("checkOutDate", "1");
        }
    }
}
