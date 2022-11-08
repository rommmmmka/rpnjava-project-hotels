const registerForm = document.getElementById("registerForm");
const inputPassword = document.getElementById("inputPassword");
const inputPasswordConfirm = document.getElementById("inputPasswordConfirm");
const createAccountLink = document.getElementById("createAccountLink");

createAccountLink.addEventListener("click", function () {
    if (inputPassword.value !== inputPasswordConfirm.value) {
        toastr.error("Паролі не супадаюць");
    } else {
        registerForm.submit();
    }
});