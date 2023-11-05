const boxes = document.querySelectorAll(".description");


let ProductQuantInputs = document.querySelectorAll("input[name^=txtQuantity]");
console.log(ProductQuantInputs.length);
for (let i = 0; i < ProductQuantInputs.length; i++) {
    ProductQuantInputs[i].addEventListener("input", function () {
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

boxes.forEach((box) => {
  const content = box.querySelector(".content");
  const button = box.querySelector("button");
  content.classList.add("hide");

  button.addEventListener("click", () => {
    if (content.classList.contains("hide")) {
      content.classList.remove("hide");
      button.textContent = "Thu gọn";
    } else {
      content.classList.add("hide");
      button.textContent = "Xem thêm";
    }
  });
});

function handleKeyDown(event) {
  if (event.key === "Enter") {
    event.preventDefault();
    document.getElementById("Search").click();
  }
}

// Dropdown product button for admin
const dropdownProductBtn = document.querySelector("#product-dropdown-btn");
const dropdownUserBtn = document.querySelector("#user-dropdown-btn");

const dropdownProductList = document.querySelector("#product-dropdown-btn + ul");
const dropdownUserList = document.querySelector("#user-dropdown-btn + ul");



// If clicked outside when the dropdown is active, deactivate it.
document.addEventListener('click', function (event) {

  

 

});

function isClickedOutside(event, element) {
  if (event.target.closest(element)) {
    return false;
  }

  return true;
}
