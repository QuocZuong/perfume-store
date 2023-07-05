let ProductQuantInputs = document.querySelectorAll("input[name^=ProductQuan]");
let ProductIDInputs = document.querySelectorAll("input[name^=ProductID]");
let ProductMaxQuantityInputs = document.querySelectorAll("span.ProductMaxQuantity");

for (let i = 0; i < ProductQuantInputs.length; i++) {
    ProductQuantInputs[i].addEventListener("input", function () {
        console.log(ProductMaxQuantityInputs[i].innerText);
        if (ProductQuantInputs[i].value > parseInt(ProductMaxQuantityInputs[i].innerText)) {
            ProductQuantInputs[i].value = parseInt(ProductMaxQuantityInputs[i].innerText);
        }
        if (ProductQuantInputs[i].value < 1 && ProductQuantInputs[i].value !== "") {
            ProductQuantInputs[i].value = 1;
        }
    });
    ProductQuantInputs[i].addEventListener("blur", function () {
        if (ProductQuantInputs[i].value === "") {
            ProductQuantInputs[i].value = 1;
        }
    });
}

// Only allow form to submit if there is any changes made into the cart.

const inputs = document.querySelectorAll("form input");
const disabledBtn = document.querySelector("button.btn-disabled");

let dataChanged = false;

disabledBtn.addEventListener('click', function (e) {
    if (!dataChanged) {
        e.preventDefault();
    }
});

inputs.forEach((input) => {
    input.addEventListener('change', function () {
        if (dataChanged === false) {
            dataChanged = true;
            disabledBtn.classList.remove('btn-disabled');
        }
    });
    input.addEventListener('input', function () {
        if (dataChanged === false) {
            dataChanged = true;
            disabledBtn.classList.remove('btn-disabled');
        }
    });
});

