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