const radioBtns = document.querySelectorAll(".box .left input[type='radio']");
const divArea = document.querySelector(".box .left div:has(textarea)");

const header = document.querySelector("#header-address");
const addressInput = document.querySelectorAll("#header-address + div input");

radioBtns[0].addEventListener('click', function () {
  if (this.checked) {
    divArea.classList.add('hidden');
  }
});

radioBtns[1].addEventListener('click', function () {
  if (this.checked) {
    divArea.classList.remove('hidden');
  }
});


radioBtns[2].addEventListener('click', function () {
  if (this.checked) {
    header.classList.add('hidden');
    addressInput.forEach(e => {
      e.classList.add('hidden');
    });
  }
});

radioBtns[3].addEventListener('click', function () {
  if (this.checked) {
    header.classList.remove('hidden');
    addressInput.forEach(e => {
      e.classList.remove('hidden');
    });
  }
});


