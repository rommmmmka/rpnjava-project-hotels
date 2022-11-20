const validationConstraints = {
    lastName: {
        presence: {
            allowEmpty: false,
            message: "Увядзіце прозвішча"
        },
        length: {
            maximum: 32,
            tooLong: "Прозвішча павінна быць не больш за 32 сімвалы"
        },
        format: {
            pattern: "[A-zА-яЁёІіЎў'\\- ]+",
            message: "У прозвішчы дазваляюцца сімвалы беларускага, рускага і англійскага алфавітаў, апостраф, працяжнік і прабел"
        }
    },
    firstName: {
        presence: {
            allowEmpty: false,
            message: "Увядзіце імя"
        },
        length: {
            maximum: 32,
            tooLong: "Імя павінна быць не больш за 32 сімвалы"
        },
        format: {
            pattern: "[A-zА-яЁёІіЎў'\\- ]+",
            message: "У імі дазваляюцца сімвалы беларускага, рускага і англійскага алфавітаў, апостраф, працяжнік і прабел"
        }
    },
    patronymic: {
        length: {
            maximum: 32,
            tooLong: "Імя па бацьку павінна быць не больш за 32 сімвалы"
        },
        format: {
            pattern: "[A-zА-яЁёІіЎў'\\- ]+",
            message: "У імі па бацьку дазваляюцца сімвалы беларускага, рускага і англійскага алфавітаў, апостраф, працяжнік і прабел"
        }
    },
    login: {
        presence: {
            allowEmpty: false,
            message: "Увядзіце логін"
        },
        length: {
            minimum: 6,
            maximum: 32,
            tooShort: "Логін павінен быць не менш за 6 сімвалаў",
            tooLong: "Логін павінен быць не больш за 32 сімвалы"
        },
        format: {
            pattern: "[A-z0-9\\-_]+",
            message: "У логіне дазваляюцца сімвалы англійскага алфавіту, лічбы, працяжнік і падкрэсліванне"
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
function filterSortingProperty(property) {
    filtersForm.sortingProperty.value = property;
    filtersForm.submit();
}