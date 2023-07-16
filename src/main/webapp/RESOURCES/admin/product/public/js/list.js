const boxes = document.querySelectorAll(".description");

boxes.forEach((box) => {
  const content = box.querySelector(".content");
  const button = box.querySelector("button");
  content.classList.add("hide");

  button.addEventListener("click", () => {
    if (content.classList.contains("hide")) {
      content.classList.remove("hide");
      button.textContent = "Thu gọn";
    } else {
      content.classList.add("hide");
      button.textContent = "Xem thêm";
    }
  });
});

function handleKeyDown(event) {
  if (event.key === "Enter") {
      event.preventDefault();
      document.getElementById("Search").click();
  }
}