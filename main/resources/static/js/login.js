const loginForm = document.getElementById("loginForm");
const loginLink = document.getElementById("loginLink");

loginLink.addEventListener("click", function () {
    loginForm.submit();
});