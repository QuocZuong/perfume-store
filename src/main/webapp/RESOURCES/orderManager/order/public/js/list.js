$(document).ready(function () {
  const disabledBtn = document.querySelectorAll(".btn-disabled");

  for (let i = 0; i < disabledBtn.length; i++) {
    const btn = disabledBtn[i];

    btn.addEventListener('click', (e) => {
      e.preventDefault();
      return false;
    });

  }
});