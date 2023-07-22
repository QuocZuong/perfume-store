/* Modified by Nguyen Le Tai Duc 6:54 P.M 8/7 */
const links = document.querySelectorAll('.list a');
let currentActiveLink = document.querySelector('div.list li a.active');
const pages = document.querySelectorAll('.right > div');
console.log("eeee");

links.forEach((link, index) => {
  if (link == currentActiveLink) {
    hideAllPage();
    currentActiveLink = showPage(index, currentActiveLink);
  }

  link.addEventListener('click', (e) => {
    hideAllPage();
    currentActiveLink = showPage(index, currentActiveLink);
  });
});


function hideAllPage() {
  pages.forEach(page => {
    page.style.display = 'none';
  });
}

function showPage(index, activeLink) {
  pages[index].style.display = 'block';
  activeLink.classList.remove("active");
  links[index].classList.add("active");
  activeLink = links[index];

  return activeLink;
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