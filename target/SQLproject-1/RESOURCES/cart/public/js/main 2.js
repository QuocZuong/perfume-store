const buttons = document.querySelectorAll(".btnDropDown");
const opens = document.querySelectorAll(".open");

buttons.forEach((button) => {
  button.addEventListener("click", function (e) {
    const currentButton = e.currentTarget;
    const dropdownContent = currentButton.nextElementSibling;
    const span = currentButton.querySelector("span");

    opens.forEach((element) => {
      if (element !== dropdownContent) {
        element.classList.remove("active");
      }
    });

    if (span.innerText === "-") {
      span.innerText = "+";
    } else {
      span.innerText = "-";
    }

    dropdownContent.classList.toggle("active");
  });
});
