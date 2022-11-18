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
    hotel: {
        presence: {
            allowEmpty: false,
            message: "Выберыце гатэль"
        }
    },
    coverPhotoFile: {
        presence: {
            allowEmpty: false,
            message: "Загрузіце файл"
        }
    }
};
const addRoomForm = document.getElementById("addRoomForm");
const inputFile = document.getElementById("inputFile");
addRoomForm.addEventListener("submit", event => {
    let attributes = validate.collectFormValues(addRoomForm);
    let errors = validate(attributes, validationConstraints, validationOptions);

    if (typeof errors !== 'undefined' && errors.length > 0) {
        event.preventDefault();
        toastr.error(errors[0]);
        return;
    }
    if (inputFile.files[0].size / 1024 / 1024 > 3.0) {
        event.preventDefault();
        toastr.error("Памер файла не можа быць больш чым 3 МБ");
        return;
    }
    if (attributes.guestsLimit <= attributes.childrenLimit) {
        event.preventDefault();
        toastr.error("Агульная колькасць месц павінна быць больш, чым колькасць дзіцячых");
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
            return;
        }
        if (attributes.guestsLimit <= attributes.childrenLimit) {
            event.preventDefault();
            toastr.error("Агульная колькасць месц павінна быць больш, чым колькасць дзіцячых");
        }
    });
});


const inputNumberElements = [
    document.getElementById("inputAdultsCount"),
    document.getElementById("inputСostPerNight"),
    document.getElementById("inputRoomsCount")
];
inputNumberElements.forEach(function (element) {
    element.addEventListener("change", function () {
        element.value = Math.max(1, element.value);
    });
});


function editCard(id) {
    let cardContentTop = document.getElementById("cardContentTop" + id);
    let cardContentBottom = Array.from(document.getElementsByClassName("card-content-bottom-" + id));
    let cardEditor = document.getElementById("cardEditor" + id);

    if (cardEditor.style.display === "none") {
        cardEditor.style.display = "block";
        cardContentTop.style.display = "none";
        cardContentBottom.forEach(el => el.style.display = "none");
    } else {
        cardEditor.style.display = "none";
        cardContentTop.style.display = "block";
        cardContentBottom.forEach(el => el.style.display = "block");
    }
}


const filtersForm = document.getElementById("filtersForm");

function filterHotel(id) {
    filtersForm.filterHotel.value = id;
    filtersForm.submit();
}

function filterCity(id) {
    filtersForm.filterCity.value = id;
    filtersForm.submit();
}

function filterSortingProperty(property) {
    filtersForm.sortingProperty.value = property;
    filtersForm.submit();
}
