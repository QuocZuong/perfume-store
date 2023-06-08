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

  // Check if reached the end and scroll back to the first item
  const items = document.querySelectorAll(".item .card");
  const itemWidth = items[0].getBoundingClientRect().width;
  const lastItemIndex = items.length - 1;
  const lastItemOffset = lastItemIndex * itemWidth;
  const scrollEndOffset = item.scrollWidth - item.offsetWidth;http://127.0.0.1:5500/public/images/products/Kilian-Angels-Share-600x600.png

  if (item.scrollLeft >= scrollEndOffset && dragStartPos < dragEndPos) {
    item.scrollLeft = 0;
  } else if (item.scrollLeft === 0 && dragStartPos > dragEndPos) {
    item.scrollLeft = lastItemOffset - item.offsetWidth;
  }
};

const dragEnd = () => {
  isDragStart = false;
};

const updateDots = () => { // function to add effect for dots
  const items = document.querySelectorAll(".item .card");
  const itemWidth = items[0].getBoundingClientRect().width;
  const scrollLeft = item.scrollLeft; //return the value when scroll this item
  const currentItem = Math.round(scrollLeft / itemWidth);
  const dotsArray = Array.from(dots.children); // create new array that contain all the child of dots element
  dotsArray.forEach((dot, index) => {
    if (index === currentItem) {
      dot.classList.add("active");
    } else {
      dot.classList.remove("active");$
    }
  });
};

const handleDotClick = (e) => {
  const dotsArray = Array.from(dots.children);
  const dotIndex = dotsArray.indexOf(e.target); // get the index of the clicked dot element
  const items = document.querySelectorAll(".item .card");
  const itemWidth = items[0].getBoundingClientRect().width; // get the width of the first item
  const targetItem = dotIndex;

  item.classList.add("scroll-animation");
  item.scrollLeft = targetItem * itemWidth;

  dotsArray.forEach((dot) => {
    dot.classList.remove("active");
  });

  dotsArray[dotIndex].classList.add("active");

  const currentItem = Math.round(item.scrollLeft / itemWidth);
  
  if (currentItem < targetItem) {
    item.scrollLeft = targetItem * itemWidth;
  } else if (currentItem > targetItem + 1) {
    item.scrollLeft = (targetItem + 1) * itemWidth;
  }
  
};

dots.querySelectorAll(".dot").forEach((dot) => {
  dot.addEventListener("click", handleDotClick);
});

item.addEventListener("transitionend", () => {
  item.classList.remove("scroll-animation");
});

item.addEventListener("mousedown", dragStart);
item.addEventListener("mousemove", dragging);
item.addEventListener("mouseup", dragEnd);

item.addEventListener("touchstart", dragStart);
item.addEventListener("touchmove", dragging);
item.addEventListener("touchend", dragEnd);



// add dots
const items = document.querySelectorAll(".item .card");
const dotToGenerate = Math.ceil(items.length / 5); // one dot for 5 items
for (let i = 0; i < dotToGenerate; i++) {
  const dot = document.createElement("span"); // create a new span element for each card
  dot.classList.add("dot"); // add the dot class to the span element
  dots.appendChild(dot); // append the span element to the dots container element
  dot.addEventListener("click", handleDotClick);
}

// update dots on load
updateDots();
