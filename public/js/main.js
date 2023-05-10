const item = document.querySelector(".item");
const dots = document.querySelector(".dots");
let isDragStart = false;
let dragStartPos = 0;
let dragEndPos = 0;

const dragStart = (e) => {
  isDragStart = true;
  dragStartPos = e.clientX || e.touches[0].clientX; // set dragStartPos to the client X position of the mouse or touch
};

const dragging = (e) => {
  if (!isDragStart) return;
  e.preventDefault();
  dragEndPos = e.clientX || e.touches[0].clientX; // set dragEndPos to the client X position of the mouse or touch
  item.scrollLeft += dragStartPos - dragEndPos; // scroll the item element horizontally based on the difference between the start and end drag positions
  dragStartPos = dragEndPos; // update dragStartPos to the current dragEndPos for the next dragging event
  updateDots(); // update the dots based on the current scroll position
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
    if (index === currentItem) {
      dot.classList.add("active");
    } else {
      dot.classList.remove("active");
    }
  });
};

const handleDotClick = (e) => {
  const dotIndex = Array.from(dots.children).indexOf(e.target); // get the index of the clicked dot element
  item.scrollLeft = item.offsetWidth * dotIndex; // set the scroll position of the item element to show the card corresponding to the clicked dot
};

item.addEventListener("mousedown", dragStart);
item.addEventListener("mousemove", dragging);
item.addEventListener("mouseup", dragEnd);

item.addEventListener("touchstart", dragStart);
item.addEventListener("touchmove", dragging);
item.addEventListener("touchend", dragEnd);

dots.querySelectorAll(".dot").forEach((dot) => {
  dot.addEventListener("click", handleDotClick);
});

// add dots
const items = document.querySelectorAll(".item .card");
for (let i = 0; i < items.length; i++) {
  const dot = document.createElement("span"); // create a new span element for each card
  dot.classList.add("dot"); // add the dot class to the span element
  dots.appendChild(dot); // append the span element to the dots container element
}

// update dots on load
updateDots();
