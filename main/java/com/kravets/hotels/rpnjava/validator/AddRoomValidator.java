package com.kravets.hotels.rpnjava.validator;

import com.kravets.hotels.rpnjava.data.form.AddHotelForm;
import com.kravets.hotels.rpnjava.data.form.AddRoomForm;
import com.kravets.hotels.rpnjava.misc.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class AddRoomValidator implements Validator {
    private final Services services;

    @Autowired
    public AddRoomValidator(Services services) {
        this.services = services;
    }

    @Override
    public boolean supports(Class c) {
        return AddHotelForm.class.equals(c);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AddRoomForm addRoomForm = (AddRoomForm) target;

        if (addRoomForm.getName() == null || addRoomForm.getName().length() < 6 || addRoomForm.getName().length() > 45) {
            errors.rejectValue("name", "1");
        }
        if (addRoomForm.getDescription().length() > 300) {
            errors.rejectValue("description", "1");
        }
        if (services.hotel.getHotelById(addRoomForm.getHotel()) == null) {
            errors.rejectValue("hotel", "1");
        }
        try {
            String fileName = addRoomForm.getCoverPhotoFile().getOriginalFilename();
            String fileExtension = fileName.substring(fileName.length() - 4);
            if (!fileExtension.equals(".jpg")) {
                throw new Exception();
            }
        } catch (Exception e) {
            errors.rejectValue("coverPhotoFile", "1");
        }
    }
}
