const validationConstraints = {
    adultsNumber: {
        numericality: {
            lessThanOrEqualTo: 30,
            message: "Колькасць дарослых не можа быць больш за 30"
        }
    },
    childrenNumber: {
        numericality: {
            lessThanOrEqualTo: 10,
            message: "Колькасць дзяцей не можа быць больш за 10"
        }
    }
};
const searchForm = document.getElementById("searchForm");
searchForm.addEventListener("submit", event => {
    let attributes = validate.collectFormValues(searchForm);
    let errors = validate(attributes, validationConstraints, validationOptions);
    console.log(attributes)
    console.log(errors)
    if (typeof errors !== 'undefined' && errors.length > 0) {
        event.preventDefault();
        toastr.error(errors[0]);
    }
});

const inputAdultsNumber = document.getElementById("inputAdultsNumber");
inputAdultsNumber.addEventListener("change", function () {
    inputAdultsNumber.value = Math.max(1, inputAdultsNumber.value);
});

const inputChildrenNumber = document.getElementById("inputChildrenNumber");
inputChildrenNumber.addEventListener("change", function () {
    inputChildrenNumber.value = Math.max(0, inputChildrenNumber.value);
});

const inputCheckInDate = document.getElementById("inputCheckInDate");
const inputCheckOutDate = document.getElementById("inputCheckOutDate");
inputCheckInDate.addEventListener("change", function () {
    let checkInDate = new Date(inputCheckInDate.value);
    let checkOutMinDate = new Date();
    checkOutMinDate.setDate(checkInDate.getDate() + 1);
    let checkOutMinDateString = `${checkOutMinDate.getFullYear()}-${checkOutMinDate.getMonth() + 1}-${checkOutMinDate.getDate()}`;
    inputCheckOutDate.setAttribute("min", checkOutMinDateString);

    if (checkOutMinDate > inputCheckOutDate.valueAsDate) {
        inputCheckOutDate.value = checkOutMinDateString;
    }
});

