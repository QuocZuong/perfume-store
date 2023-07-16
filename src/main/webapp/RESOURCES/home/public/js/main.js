const item1 = document.querySelector(".item.man");
const item2 = document.querySelector(".item.woman");
const item3 = document.querySelector(".item.unisex");
const dots = document.querySelector(".dots");
let isDragStart = false;
let dragStartPos = 0;
let dragEndPos = 0;
let dotsArray = Array.from(dots.children);

const man = document.querySelector(".btnMan");
const woman = document.querySelector(".btnWoman");
const unisex = document.querySelector(".btnUnisex");

const boxMan = document.querySelector(".item.man");
const boxWoman = document.querySelector(".item.woman");
const boxUnisex = document.querySelector(".item.unisex");

man.addEventListener("click", () => {
  man.classList.add("active");
  woman.classList.remove("active");
  unisex.classList.remove("active");

  boxMan.classList.add("active");
  boxWoman.classList.remove("active");
  boxUnisex.classList.remove("active");
});

woman.addEventListener("click", () => {
  man.classList.remove("active");
  woman.classList.add("active");
  unisex.classList.remove("active");

  boxMan.classList.remove("active");
  boxWoman.classList.add("active");
  boxUnisex.classList.remove("active");
});

unisex.addEventListener("click", () => {
  man.classList.remove("active");
  woman.classList.remove("active");
  unisex.classList.add("active");

  boxMan.classList.remove("active");
  boxWoman.classList.remove("active");
  boxUnisex.classList.add("active");
});

const dragStart = (e) => {
  isDragStart = true;
  dragStartPos = e.clientX || e.touches[0].clientX;
};

const dragging = (e) => {
  if (!isDragStart) return;
  e.preventDefault();
  dragEndPos = e.clientX || e.touches[0].clientX;
  item1.scrollLeft += dragStartPos - dragEndPos;
  item2.scrollLeft += dragStartPos - dragEndPos;
  item3.scrollLeft += dragStartPos - dragEndPos;
  dragStartPos = dragEndPos;
  updateDots();
};

const dragEnd = () => {
  isDragStart = false;
};

const updateDots = () => {
  const items = document.querySelectorAll(".item .card");
  const itemWidth = items[0].getBoundingClientRect().width;
  const scrollLeft = item1.scrollLeft;
  const currentItem = Math.round(scrollLeft / itemWidth);
  dotsArray.forEach((dot, index) => {
    dot.classList.remove("active");
  });
  dotsArray[currentItem].classList.add("active");
};

const handleDotClick = (e) => {
  const dotIndex = Array.from(dots.children).indexOf(e.target);
  const items = document.querySelectorAll(".item .card");
  const itemWidth = items[0].getBoundingClientRect().width;
  const targetItem = dotIndex;

  item1.classList.add("scroll-animation");
  item2.classList.add("scroll-animation");
  item3.classList.add("scroll-animation");
  item1.scrollLeft = targetItem * itemWidth;
  item2.scrollLeft = targetItem * itemWidth;
  item3.scrollLeft = targetItem * itemWidth;
  updateDots();
};

dots.querySelectorAll(".dot").forEach((dot) => {
  dot.addEventListener("click", handleDotClick);
});

document.addEventListener("mousemove", dragging);
document.addEventListener("mouseup", dragEnd);

item1.addEventListener("mousedown", dragStart);
item2.addEventListener("mousedown", dragStart);
item3.addEventListener("mousedown", dragStart);
item1.addEventListener("touchstart", dragStart);
item2.addEventListener("touchstart", dragStart);
item3.addEventListener("touchstart", dragStart);
item1.addEventListener("touchmove", dragging);
item2.addEventListener("touchmove", dragging);
item3.addEventListener("touchmove", dragging);
item1.addEventListener("touchend", dragEnd);
item2.addEventListener("touchend", dragEnd);
item3.addEventListener("touchend", dragEnd);

// Clear existing dots
dots.innerHTML = "";

// Add dots
const items = document.querySelectorAll(".item .card");
const dotToGenerate = Math.ceil(items.length / 5);
for (let i = 0; i < dotToGenerate; i++) {
  const dot = document.createElement("span");
  dot.classList.add("dot");
  dot.addEventListener("click", handleDotClick);
  dots.appendChild(dot);
  dotsArray.push(dot);
}

// Update dots on load and resize
updateDots();
window.addEventListener("resize", updateDots);

// Dropdown product button for admin
const dropdownProductBtn = document.querySelector("#product-dropdown-btn");
const dropdownUserBtn = document.querySelector("#user-dropdown-btn");

const dropdownProductList =  document.querySelector("#product-dropdown-btn + ul");
const dropdownUserList =  document.querySelector("#user-dropdown-btn + ul");

dropdownProductBtn.addEventListener('click', function() {
  dropdownProductList.classList.toggle("drop-down-show");
});
dropdownUserBtn.addEventListener('click', function() {
  dropdownUserList.classList.toggle("drop-down-show");
});

// If clicked outside when the dropdown is active, deactivate it.
document.addEventListener('click', function(event) {
  
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