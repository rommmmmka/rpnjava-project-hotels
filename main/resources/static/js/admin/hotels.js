const validationConstraints = {
    name: {
        presence: {
            allowEmpty: false,
            message: "Увядзіце назву"
        },
        length: {
            minimum: 6,
            maximum: 45,
            tooShort: "Назва павінна быць не менш за 6 сімвалаў",
            tooLong: "Назва павінна быць не больш за 45 сімвалаў"
        }
    },
    description: {
        length: {
            maximum: 300,
            tooLong: "Апісанне павінна быць не больш за 300 сімвалаў"
        }
    },
    coverPhotoFile: {
        presence: {
            allowEmpty: false,
            message: "Загрузіце файл"
        }
    }
};
const addHotelForm = document.getElementById("addHotelForm");
const inputFile = document.getElementById("inputFile");
addHotelForm.addEventListener("submit", event => {
    let attributes = validate.collectFormValues(addHotelForm);
    let errors = validate(attributes, validationConstraints, validationOptions);

    if (typeof errors !== 'undefined' && errors.length > 0) {
        event.preventDefault();
        toastr.error(errors[0]);
    } else {
        if (inputFile.files[0].size / 1024 / 1024 > 3.0) {
            event.preventDefault();
            toastr.error("Памер файла не можа быць больш чым 3 МБ");
        }
    }
});


const validationConstraints2 = {
    name: {
        presence: {
            allowEmpty: false,
            message: "Увядзіце назву"
        },
        length: {
            minimum: 6,
            maximum: 45,
            tooShort: "Назва павінна быць не менш за 6 сімвалаў",
            tooLong: "Назва павінна быць не больш за 45 сімвалаў"
        }
    },
    description: {
        length: {
            maximum: 300,
            tooLong: "Апісанне павінна быць не больш за 300 сімвалаў"
        }
    }
};
const editCardFormsArray = Array.from(document.getElementsByClassName("editCardForm"));
editCardFormsArray.forEach(function (form) {
    form.addEventListener("submit", event => {
        let attributes = validate.collectFormValues(form);
        let errors = validate(attributes, validationConstraints2, validationOptions);

        if (typeof errors !== 'undefined' && errors.length > 0) {
            event.preventDefault();
            toastr.error(errors[0]);
        }
    });
});


const textareasArray = Array.from(document.getElementsByTagName("textarea"));
textareasArray.forEach(function (textarea) {
    textarea.addEventListener("scroll", function () {
        let labelId = textarea.id + "Label";
        console.log(labelId);
        if (textarea.scrollTop !== 0) {
            $('#' + labelId).hide(0);
            console.log("hide");
        } else {
            $('#' + labelId).show(0);
            console.log("show");
        }
    });
});


function editCard(id) {
    let cardContent = document.getElementById("cardContent" + id);
    let cardEditor = document.getElementById("cardEditor" + id);

    if (cardEditor.style.display === "none") {
        cardEditor.style.display = "block";
        cardContent.style.display = "none";
    } else {
        cardEditor.style.display = "none";
        cardContent.style.display = "block";
    }
}


const filtersForm = document.getElementById("filtersForm");
function filterRoomsCount() {
    filtersForm.sortingProperty.value = "roomsNumber";
    filtersForm.submit();
}
function filterCity(id) {
    filtersForm.filterCity.value = id;
    filtersForm.submit();
}
