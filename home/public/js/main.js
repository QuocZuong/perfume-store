const item = document.querySelector(".item");
const dots = document.querySelector(".dots");
let isDragStart = false;
let dragStartPos = 0;
let dragEndPos = 0;

const dragStart = (e) => {
  isDragStart = true;
  dragStartPos = e.clientX || e.touches[0].clientX;
};

const dragging = (e) => {
  if (!isDragStart) return;
  e.preventDefault();
  dragEndPos = e.clientX || e.touches[0].clientX;
  item.scrollLeft += dragStartPos - dragEndPos;
  dragStartPos = dragEndPos;
  updateDots();
};

const dragEnd = () => {
  isDragStart = false;
};

const updateDots = () => {
  const items = document.querySelectorAll(".item .card");
  const itemWidth = items[0].getBoundingClientRect().width;
  const scrollLeft = item.scrollLeft;
  const currentItem = Math.round(scrollLeft / itemWidth);
  const dotsArray = Array.from(dots.children);
  dotsArray.forEach((dot, index) => {
    dot.classList.remove("active");
  });
  dotsArray[currentItem].classList.add("active");
};

const handleDotClick = (e) => {
  const dotIndex = Array.from(dots.children).indexOf(e.target);
  const items = document.querySelectorAll(".item .card");
  const itemWidth = items[0].getBoundingClientRect().width;
  const targetItem = dotIndex * 5; // Assuming each dot represents 5 items

  item.classList.add("scroll-animation");
  item.scrollLeft = targetItem * itemWidth;
};

dots.querySelectorAll(".dot").forEach((dot) => {
  dot.addEventListener("click", handleDotClick);
});

document.addEventListener("mousemove", dragging);
document.addEventListener("mouseup", dragEnd);

item.addEventListener("mousedown", dragStart);
item.addEventListener("touchstart", dragStart);
item.addEventListener("touchmove", dragging);
item.addEventListener("touchend", dragEnd);

// Add dots
const items = document.querySelectorAll(".item .card");
const dotToGenerate = Math.ceil(items.length / 5);
for (let i = 0; i < dotToGenerate; i++) {
  const dot = document.createElement("span");
  dot.classList.add("dot");
  dots.appendChild(dot);
}

// Update dots on load and resize
window.addEventListener("load", updateDots);
window.addEventListener("resize", updateDots);
