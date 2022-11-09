const loginForm = document.getElementById("loginForm");

const validationConstraints = {
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
    },
    passwordHash: {
        presence: {
            allowEmpty: false,
            message: "Увядзіце пароль"
        },
        length: {
            minimum: 6,
            maximum: 32,
            tooShort: "Пароль павінен быць не менш за 6 сімвалаў",
            tooLong: "Пароль павінен быць не больш за 32 сімвалы"
        },
        format: {
            pattern: "[A-z0-9\\-_]+",
            message: "У паролі дазваляюцца сімвалы англійскага алфавіту, лічбы, працяжнік і падкрэсліванне"
        }
    }
};

loginForm.addEventListener("submit", event => {
    let attributes = validate.collectFormValues(loginForm);
    let errors = validate(attributes, validationConstraints, validationOptions);
    if (typeof errors !== 'undefined' && errors.length > 0) {
        event.preventDefault();
        toastr.error(errors[0]);
    }
});