const items = document.querySelectorAll(".item");
const productListInput = document.querySelector("#productList");

items.forEach((item) => {
    const label = item.querySelector("label");
    item.addEventListener("click", () => {
        if (item.classList.contains("enabled")) {
            item.classList.remove("enabled");
            productListInput.value = productListInput.value.replace(label.innerText + ", ", "");
        } else {
            item.classList.add("enabled");
            productListInput.value += label.innerText + ", ";
        }
    });

    label.addEventListener("click", () => {
        if (item.classList.contains("enabled")) {
            item.classList.remove("enabled");
            productListInput.value = productListInput.value.replace(label.innerText + ", ", "");
        } else {
            item.classList.add("enabled");
            productListInput.value += label.innerText + ", ";
        }
    });
});

items.forEach((item, index) => {
    if (item.classList.contains("enabled")) {
        const label = item.querySelector("label");

        if (index === items.length - 1) {
            productListInput.value += productListInput.value + label.innerText;
        } else {
            productListInput.value = productListInput.value + label.innerText + ", ";
        }
    }
});
