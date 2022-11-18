package com.kravets.hotels.rpnjava.validator;

import com.kravets.hotels.rpnjava.data.form.EditRoomForm;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class EditRoomValidator implements Validator {

    @Override
    public boolean supports(Class c) {
        return EditRoomForm.class.equals(c);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EditRoomForm editRoomForm = (EditRoomForm) target;

        if (editRoomForm.getName() == null || editRoomForm.getName().length() < 6 || editRoomForm.getName().length() > 45) {
            errors.rejectValue("name", "1");
        }
        if (editRoomForm.getDescription().length() > 300) {
            errors.rejectValue("description", "1");
        }
    }
}
