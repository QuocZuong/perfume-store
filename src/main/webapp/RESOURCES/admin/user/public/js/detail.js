// Dropdown product button for admin
const dropdownProductBtn = document.querySelector("#product-dropdown-btn");
const dropdownUserBtn = document.querySelector("#user-dropdown-btn");

const dropdownProductList = document.querySelector("#product-dropdown-btn + ul");
const dropdownUserList = document.querySelector("#user-dropdown-btn + ul");

dropdownProductBtn.addEventListener('click', function () {
  dropdownProductList.classList.toggle("drop-down-show");
});
dropdownUserBtn.addEventListener('click', function () {
  dropdownUserList.classList.toggle("drop-down-show");
});

// If clicked outside when the dropdown is active, deactivate it.
document.addEventListener('click', function (event) {

  if (dropdownProductList.classList.contains("drop-down-show")) {
    if (isClickedOutside(event, "#product-dropdown-btn") && isClickedOutside(event, "#product-dropdown-btn + ul")) {
      dropdownProductList.classList.toggle("drop-down-show");
    }
  }

  if (dropdownUserList.classList.contains("drop-down-show")) {
    if (isClickedOutside(event, "#user-dropdown-btn") && isClickedOutside(event, "#product-dropdown-btn + ul")) {
      dropdownUserList.classList.toggle("drop-down-show");
    }
  }

});

function isClickedOutside(event, element) {
  if (event.target.closest(element)) {
    console.log("Clicked inside");
    return false;
  }

  console.log("Clicked outside");
  return true;
}