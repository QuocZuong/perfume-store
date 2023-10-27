const items = document.querySelectorAll(".item");
const productListInput = document.querySelector("#productList");

items.forEach((item) => {
    const label = item.querySelector(".item-name");

    item.addEventListener("click", () => {
        if (item.classList.contains("enabled")) {
            item.classList.remove("enabled");

            console.log(`productListInput.value.indexOf(label.innerText): ${productListInput.value.indexOf(label.innerText)}`);

            const index = productListInput.value.indexOf(label.innerText);
            if (index === 0) {
                if (productListInput.value[index + label.innerText.length] === ",") {
                    productListInput.value = productListInput.value.replace(label.innerText + ", ", "");
                } else {
                    productListInput.value = productListInput.value.replace(label.innerText, "");
                }
            } else {
                productListInput.value = productListInput.value.replace(", " + label.innerText, "");
            }

        } else {
            item.classList.add("enabled");

            if (productListInput.value === "") {
                productListInput.value += label.innerText;
            } else {
                productListInput.value += ", " + label.innerText;
            }
        }
    });

});

items.forEach((item) => {
    if (item.classList.contains("enabled")) {
        const label = item.querySelector(".item-name");

        if (productListInput.value === "")
            productListInput.value += productListInput.value + label.innerText;
        else {
            productListInput.value = productListInput.value + ", " + label.innerText;
        }
    }
});
