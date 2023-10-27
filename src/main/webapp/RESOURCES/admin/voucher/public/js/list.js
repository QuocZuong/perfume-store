const items = document.querySelectorAll(".item");
const productListInput = document.querySelector("#productList");

items.forEach((item, index) => {
    const label = item.querySelector(".item-name");
    
    item.addEventListener("click", () => {
        if (item.classList.contains("enabled")) {
            item.classList.remove("enabled");
            productListInput.value = productListInput.value.replace(", " + label.innerText, "");
        } else {
            item.classList.add("enabled");

            if (index === 0) {
                productListInput.value += label.innerText;
            } else {
                productListInput.value += ", " + label.innerText;
            }
        }
    });

});

items.forEach((item, index) => {
    if (item.classList.contains("enabled")) {
        const label = item.querySelector(".item-name");

        if (index === 0) {
            productListInput.value += productListInput.value + label.innerText;
        } else {
            productListInput.value = productListInput.value + ", " + label.innerText;
        }
    }
});
