const item = document.querySelector(".item");

console.log(item)
let isDragStart = false;
let dragStartX = 0;
let scrollLeftStart = 0;

const dragStart = (e) => {
  isDragStart = true;
  dragStartX = e.pageX - item.offsetLeft;
  scrollLeftStart = item.scrollLeft;
};

const dragging = (e) => {
  if (!isDragStart) return;
  e.preventDefault();
  const dragX = e.pageX - item.offsetLeft;
  const dragDistance = dragX - dragStartX;
  item.scrollLeft = scrollLeftStart - dragDistance;
};

const dragEnd = (e) => {
  isDragStart = false;
};

item.addEventListener("mousedown", dragStart);
item.addEventListener("mousemove", dragging);
item.addEventListener("mouseup", dragEnd);
item.addEventListener("mouseleave", dragEnd);
