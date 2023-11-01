const items = document.querySelectorAll(".item");
const productListInput = document.querySelector("#productList");

items.forEach((item) => {
    const label = item.querySelector(".item-name");
    const productId = label.getAttribute("data-id");
    item.addEventListener("click", () => {
        if (item.classList.contains("enabled")) {
            item.classList.remove("enabled");

            console.log(`productListInput.value.indexOf(productId): ${productListInput.value.indexOf(productId)}`);

            const index = productListInput.value.indexOf(productId);
            if (index === 0) {
                if (productListInput.value[index + productId.length] === ",") {
                    productListInput.value = productListInput.value.replace(productId + ", ", "");
                } else {
                    productListInput.value = productListInput.value.replace(productId, "");
                }
            } else {
                productListInput.value = productListInput.value.replace(", " + productId, "");
            }
        } else {
            item.classList.add("enabled");

            if (productListInput.value === "") {
                productListInput.value += productId;
            } else {
                productListInput.value += ", " + productId;
            }
        }
    });
});

items.forEach((item) => {
    if (item.classList.contains("enabled")) {
        const label = item.querySelector(".item-name");
        const productId = label.getAttribute("data-id");
        if (productListInput.value === "") productListInput.value += productListInput.value + productId;
        else {
            productListInput.value = productListInput.value + ", " + productId;
        }
    }
});
