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
document.addEventListener('DOMContentLoaded', function() {
  info.forEach((item) => {
    if(item.innerText === "" || item.innerText === null || item.innerText === "null"){
        item.parentElement.style.display = "none";
        count++;
    }
    if(count === 3){
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