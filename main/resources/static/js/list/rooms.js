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
