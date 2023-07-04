document.addEventListener("DOMContentLoaded", function () {
  let addToCartButton = document.getElementById("addToCartButton");
  let imgProduct = document.getElementById("productImg");
  let destination = document.getElementById("destination");

  addToCartButton.addEventListener("click", function (event) {
    event.preventDefault();
    let position = imgProduct.getBoundingClientRect();
    console.log(position.top);
    console.log(position.left);
    console.log(position.right);
    console.log(position.bottom);
    let clone = imgProduct.cloneNode(true);
    clone.classList.add("circle");
    
    clone.style.position = "absolute";
    clone.style.right = imgProduct.offsetRight + "px";
    clone.style.top = imgProduct.offsetTop + "px";

    imgProduct.parentNode.appendChild(clone);

    setTimeout(function () {
      clone.classList.remove("circle");
      clone.parentNode.removeChild(clone);
    }, 500);
  });
});
