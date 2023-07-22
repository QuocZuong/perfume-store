// const boxes = document.querySelectorAll(".description");

// boxes.forEach((box) => {
//   const content = box.querySelector(".content");
//   const button = box.querySelector("button");
//   content.classList.add("hide");

//   button.addEventListener("click", () => {
//     if (content.classList.contains("hide")) {
//       content.classList.remove("hide");
//       button.textContent = "Thu gọn";
//     } else {
//       content.classList.add("hide");
//       button.textContent = "Xem thêm";
//     }
//   });
// });

const info = document.querySelectorAll(".info");
let count = 0;
document.addEventListener('DOMContentLoaded', function () {
  info.forEach((item) => {
    if (item.innerText === "" || item.innerText === null || item.innerText === "null") {
      item.parentElement.style.display = "none";
      count++;
    }
    if (count === 3) {
      item.parentElement.parentElement.style.display = "none";
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
    return false;
  }

  return true;
}