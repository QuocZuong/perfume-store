const characterLinks = document.getElementsByClassName("character");
const brandContainer = document.getElementsByClassName("brands");


for (let i = 1; i < characterLinks.length; i++) {
  const character = characterLinks[i];

  character.addEventListener("click", function (event) {
    event.preventDefault();
    const characterText = character.innerText.trim();

    for (let j = 1; j < characterLinks.length; j++) {
      characterLinks[j].style.fontWeight = "100";
    }

    character.style.fontWeight = "bold";

    if (characterText === "ALL BRANDS") {
      for (let j = 0; j < brandContainer.length; j++) {
        const brand = brandContainer[j];
        brand.style.opacity = 1;
      }
    }

    else {
      for (let j = 0; j < brandContainer.length; j++) {
        const brand = brandContainer[j];
        const brandTitle = brand.querySelector("h3").innerText;

        if (brandTitle.toLowerCase() !== characterText.toLowerCase()) {
          brand.style.opacity = 0.3;
        }

        else if (brandTitle.toLowerCase() === characterText.toLowerCase()) {
          const scrollPosition = brand.offsetTop - 40;
          brand.style.opacity = 1;
          // brand.scrollIntoView({behavior:"smooth"});
          window.scrollTo({ top: scrollPosition, behavior: "smooth" });

        }
      }
    }

  });
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