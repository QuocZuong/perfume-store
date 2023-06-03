function callSorting(type){
  if(type === "lowToHigh"){
    sortPriceLowToHigh();
  }
  else if(type === "highToLow"){
    sortPriceHighToLow();
  }
}

function sortPriceLowToHigh(){
  const storage = document.getElementById("products");
  const products = Array.from(storage.getElementsByClassName("product"));
  const selection = document.getElementById("top");

  products.sort(function(a,b){
    var priceA = parseFloat(a.querySelector('.product-price').textContent.replace(/\D/g, ''));
    var priceB = parseFloat(b.querySelector('.product-price').textContent.replace(/\D/g, ''));
    return priceA - priceB;
  })

  storage.innerHTML = "";
  storage.appendChild(selection);
  products.forEach(function(product){
    storage.appendChild(product);
  })
}
function sortPriceHighToLow(){
  const storage = document.getElementById("products");
  const products = Array.from(storage.getElementsByClassName("product"));
  const selection = document.getElementById("top");

  products.sort(function(a,b){
    var priceA = parseFloat(a.querySelector('.product-price').textContent.replace(/\D/g, ''));
    var priceB = parseFloat(b.querySelector('.product-price').textContent.replace(/\D/g, ''));
    return priceB - priceA;
  })

  storage.innerHTML = "";
  storage.appendChild(selection);

  products.forEach(function(product){
    storage.appendChild(product);
  })
}
