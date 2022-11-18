const filtersForm = document.getElementById("filtersForm");
function filterRoomsCount() {
    filtersForm.sortingProperty.value = "roomsNumber";
    filtersForm.submit();
}
function filterCity(id) {
    filtersForm.filterCity.value = id;
    filtersForm.submit();
}
