let ProductQuantInputs = document.querySelectorAll("input[name^=ProductQuan]");
let ProductCostInputs = document.querySelectorAll("input[name^=ProductCost]");
let ProductIDInputs = document.querySelectorAll("input[name^=ProductID]");
let ProductMaxQuantityInputs = document.querySelectorAll("span.ProductMaxQuantity");

for (let i = 0; i < ProductQuantInputs.length; i++) {
    ProductQuantInputs[i].addEventListener("input", function () {
//        if (ProductQuantInputs[i].value > parseInt(ProductMaxQuantityInputs[i].value)) {
//            ProductQuantInputs[i].value = parseInt(ProductMaxQuantityInputs[i].value);
//        }
        if (ProductQuantInputs[i].value < 1) {
            ProductQuantInputs[i].value = 1;
        }
    });
    ProductQuantInputs[i].addEventListener("blur", function () {
        if (ProductQuantInputs[i].value === "") {
            ProductQuantInputs[i].value = 1;
        }
    });
}

for (let i = 0; i < ProductCostInputs.length; i++) {
    ProductCostInputs[i].addEventListener("input", function () {
        if (ProductCostInputs[i].value < 0) {
            ProductCostInputs[i].value = 0;
        }
    });
    ProductCostInputs[i].addEventListener("blur", function () {
        if (ProductCostInputs[i].value === "") {
            ProductCostInputs[i].value = 0;
        }
    });
}

// Dropdown product button for admin
const dropdownProductBtn = document.querySelector("#product-dropdown-btn");

const dropdownProductList = document.querySelector("#product-dropdown-btn + ul");


dropdownProductBtn.addEventListener('click', function () {
  dropdownProductList.classList.toggle("drop-down-show");
});


// If clicked outside when the dropdown is active, deactivate it.
document.addEventListener('click', function (event) {

  if (dropdownProductList.classList.contains("drop-down-show")) {
    if (isClickedOutside(event, "#product-dropdown-btn") && isClickedOutside(event, "#product-dropdown-btn + ul")) {
      dropdownProductList.classList.toggle("drop-down-show");
    }
  }

});

function isClickedOutside(event, element) {
  if (event.target.closest(element)) {
    return false;
  }

  return true;
}

// Hide/Show div depending on button clicked

const accountLinks = document.querySelectorAll('.account-link');

accountLinks.forEach((link) => {
  link.addEventListener('click', (e) => {
    console.log("clicked");

    const targetPage = link.getAttribute('data-page');
    if (targetPage === "manager-page") {
      hideAllPage();
      currentActiveLink = showPage(1, currentActiveLink);
    }
    if (targetPage === "info-page") {
      hideAllPage();
      currentActiveLink = showPage(2, currentActiveLink);
    }
  });
});



const buttons = document.querySelectorAll(".btnDropDown");
const opens = document.querySelectorAll(".open");

buttons.forEach((button) => {
    button.addEventListener("click", function (e) {
        const currentButton = e.currentTarget;
        const dropdownContent = currentButton.nextElementSibling;
        const span = currentButton.querySelector("span");

        opens.forEach((element) => {
            if (element !== dropdownContent) {
                element.classList.remove("active");
            }
        });

        if (span.innerText === "-") {
            span.innerText = "+";
        } else {
            span.innerText = "-";
        }

        dropdownContent.classList.toggle("active");
    });
});


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



