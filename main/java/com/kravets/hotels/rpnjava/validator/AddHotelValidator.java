package com.kravets.hotels.rpnjava.validator;

import com.kravets.hotels.rpnjava.form.AddHotelForm;
import com.kravets.hotels.rpnjava.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;


@Service
public class AddHotelValidator implements Validator {
    private final AdminService adminService;

    @Autowired
    public AddHotelValidator(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public boolean supports(Class c) {
        return AddHotelForm.class.equals(c);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AddHotelForm addHotelForm = (AddHotelForm) target;

        if (addHotelForm.getName() == null
                || addHotelForm.getName().length() < 6
                || addHotelForm.getName().length() > 45
        ) {
            errors.rejectValue("name", "1");
        }
        if (addHotelForm.getDescription() == null
                || addHotelForm.getDescription().length() < 6
                || addHotelForm.getDescription().length() > 300
        ) {
            errors.rejectValue("description", "1");
        }
        if (!adminService.getAllEnabledCitiesIds().contains(addHotelForm.getCity())) {
            errors.rejectValue("city", "1");
        }
        try {
            String fileName = addHotelForm.getCoverPhotoFile().getOriginalFilename();
            String fileExtension = fileName.substring(fileName.length() - 4);
            if (!fileExtension.equals(".jpg")) {
                throw new Exception();
            }
        } catch (Exception e) {
            errors.rejectValue("coverPhotoFile", "1");
        }
    }
}
