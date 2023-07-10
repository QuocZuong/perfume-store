const storage = document.getElementById("products");
let brandStorage = document.getElementById("box-brand");
let products = Array.from(storage.getElementsByClassName("product"));
const searchBox = document.getElementById("searchBox");
const selection = document.getElementById("top");
const radioButtons = document.querySelectorAll('input[name="priceRange"]');
const brandNameForSearch = document.querySelectorAll(".brandNameForSearch");
const form = document.getElementById("searchForm");
const url = new URLSearchParams(window.location.search);
const gender = url.get("Gender");
const price = url.get("priceRange")
const initialBrandStorageHTML = brandStorage.innerHTML;


const AllgenderRadio = document.querySelectorAll(`input[name="Gender"]`);
const AllPriceRangeRadio = document.querySelectorAll(`input[name="priceRange"]`);
const searchForm = document.querySelector(`form#searchForm`);
console.log({ AllgenderRadio });

// Make checkbox function like radio button
AllgenderRadio.forEach(function (item) {
    item.addEventListener("change", function () {
        handleCheckboxChange(item, AllgenderRadio);
        searchForm.submit();
    })
});

AllPriceRangeRadio.forEach(function (item) {
    console.log(item);
    item.addEventListener("change", function () {
        handleCheckboxChange(item, AllPriceRangeRadio);
        searchForm.submit();
    })
});


function handleCheckboxChange(checkbox, eleName) {
    if (checkbox.checked) {
        eleName.forEach(function (item) {
            if (item !== checkbox) {
                item.checked = false;
            }
        });
    }
}
// Make checkbox function like radio button

if (gender) {
    const genderRadio = document.querySelector(`input[name="Gender"][value="${gender}"]`);
    if (genderRadio) {
        genderRadio.checked = true;
    }
}

if (price) {
    const priceRadio = document.querySelector(`input[name="priceRange"][value="${price}"]`);
    if (priceRadio) {
        priceRadio.checked = true;
    }
}
function resetGenderCheckBox() {
    if (AllgenderRadio) {
        for (let i = 0; i < AllgenderRadio.length; i++) {
            AllgenderRadio[i].checked = false;
        }
    }
}

function enableScroll() {
    window.onscroll = function () { };
}

radioButtons.forEach(function (radioButton) {
    radioButton.addEventListener('click', findByPrice);
});

searchBox.addEventListener("input", function (event) {
    const value = event.target.value.toLowerCase().trim();

    if (value === "") {
        brandStorage.innerHTML = initialBrandStorageHTML;
    } else {
        brandStorage.innerHTML = "";

        brandNameForSearch.forEach(function (brand) {
            const brandText = brand.textContent.toLowerCase();
            if (brandText.includes(value)) {
                const listItem = brand.closest("li");
                brandStorage.appendChild(listItem);
            }
        });
    }
});

function findByPrice() {

    const selectedOption = document.querySelector("input[name = priceRange]:checked").value;
    products.forEach(function (product) {
        const productPrice = parseFloat(product.querySelector(".product-price").textContent.replace(/\D/g, ''));
        if ((selectedOption === "low" && productPrice >= 1500000 && productPrice <= 3000000)
            || (selectedOption === "medium" && productPrice >= 3000000 && productPrice <= 5000000)
            || (selectedOption === "high" && productPrice >= 5000000)
        ) {
            product.style.display = "flex";
        } else {
            product.style.display = "none";
        }
    })
}



function callSorting(type) {
    if (type === "lowToHigh") {
        sortPriceLowToHigh();
    } else if (type === "highToLow") {
        sortPriceHighToLow();
    } else {
        products.forEach(function (product) {
            product.style.display = "flex";
            storage.appendChild(product);
        });
        searchBox.value = "";
        radioButtons.forEach(function (item) {
            item.checked = false;
        });
        sortPriceLowToHigh();
    }
}

function sortPriceLowToHigh() {

    products.sort(function (a, b) {
        var priceA = parseFloat(a.querySelector('.product-price').textContent.replace(/\D/g, ''));
        var priceB = parseFloat(b.querySelector('.product-price').textContent.replace(/\D/g, ''));
        return priceA - priceB;
    })

    storage.innerHTML = "";
    storage.appendChild(selection);
    products.forEach(function (product) {
        storage.appendChild(product);
    })
}
function sortPriceHighToLow() {

    products.sort(function (a, b) {
        var priceA = parseFloat(a.querySelector('.product-price').textContent.replace(/\D/g, ''));
        var priceB = parseFloat(b.querySelector('.product-price').textContent.replace(/\D/g, ''));
        return priceB - priceA;
    })

    storage.innerHTML = "";
    storage.appendChild(selection);
    products.forEach(function (product) {
        storage.appendChild(product);
    })
}

// keep underline when click on tag a search bar
const anchorTags = document.querySelectorAll(".list-brand a");
const expirationTime = 30 * 1000; // 30 seconds

anchorTags.forEach((anchorTag) => {
    anchorTag.addEventListener("click", function (event) {
        anchorTags.forEach((tag) => {
            tag.classList.remove("clicked");
        });
        this.classList.add("clicked");
        const expiration = Date.now() + expirationTime;
        const item = {
            value: this.getAttribute("href"),
            expiration: expiration
        };
        localStorage.setItem("clickedValue", JSON.stringify(item));
        setTimeout(() => {
            localStorage.removeItem("clickedValue");
        }, expirationTime);
    });
});

const storedClickedValue = localStorage.getItem("clickedValue");
if (storedClickedValue) {
    const item = JSON.parse(storedClickedValue);
    const currentTime = Date.now();
    if (currentTime <= item.expiration) {
        anchorTags.forEach((tag) => {
            if (tag.getAttribute("href") === item.value) {
                tag.classList.add("clicked");
                tag.scrollIntoView({ behavior: "smooth", block: "center", inline: "start" });
            }
        });
    } else {
        localStorage.removeItem("clickedValue");
    }
}

window.addEventListener("load", function () {
    const spinnerContainer = document.querySelector(".spinner-container");
    const container = document.querySelector(".container-fluid");

    const hideDelay = 1500;

    setTimeout(function () {
        spinnerContainer.style.display = "none";
        container.style.display = "block";
    }, hideDelay);
});

//Search box function
const searchBoxTop = document.querySelector(".searchIcon");
const searchDiv = document.querySelector(".search-box");
const closeBtn = document.querySelector(".close-search-box");
const inputSearch = document.getElementById("inputSearch");

searchBoxTop.addEventListener("click", () => {
  searchDiv.classList.add("show");
  setTimeout(() => {
    inputSearch.focus();
  }, 100);
});

closeBtn.addEventListener("click", () => {
  searchDiv.classList.remove("show");
});

function handleKeyDown(event) {
  if (event.key === "Enter") {
    event.preventDefault();
    document.getElementById("SearchProduct").click();
  }
}

enableScroll();

