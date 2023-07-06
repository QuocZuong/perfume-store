const radioBtns = document.querySelectorAll(".box .left input[type='radio']");

const txtPhone = document.querySelector(".box .right input[name='txtPhone']");
const txtAddress = document.querySelector(".box .right input[name='txtAddress']");
const phone = document.querySelector("#phone");

const header = document.querySelector("#header-address");
const addressInput = document.querySelectorAll("#header-address + div input");



radioBtns[0].addEventListener('click', function () {
  if (this.checked) {
    header.classList.add('hidden');
    addressInput.forEach(e => {
      e.classList.add('hidden');
    });

    txtPhone.setAttribute('readonly', false);
    txtAddress.setAttribute('readonly', false);
    
    txtPhone.classList.remove('disabled-input');
    txtAddress.classList.remove('disabled-input');
  }
});

radioBtns[1].addEventListener('click', function () {
  if (this.checked) {
    header.classList.remove('hidden');
    addressInput.forEach(e => {
      e.classList.remove('hidden');
    });

    txtPhone.setAttribute('readonly', true);
    txtAddress.setAttribute('readonly', true);
    
    txtPhone.classList.add('disabled-input');
    txtAddress.classList.add('disabled-input');
  }
});