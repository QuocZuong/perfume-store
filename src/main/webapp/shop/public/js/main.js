const storage = document.getElementById("products");
let products = Array.from(storage.getElementsByClassName("product"));
const searchBox = document.getElementById("searchBox");
const selection = document.getElementById("top");
const radioButtons = document.querySelectorAll('input[name="priceRange"]');


searchBox.addEventListener("keypress", function(event){
  if(event.key == "Enter"){
    findProduct(searchBox.value);
  }
})

radioButtons.forEach(function (radioButton) {
  radioButton.addEventListener('click', findByPrice);
});


function findProduct(value){   // find by search box 
  storage.innerHTML = "";
  storage.appendChild(selection);
  products.forEach(function(product){
  const itemName = product.querySelector(".product-name").textContent.toString().toLowerCase();
  const itemBrand = product.querySelector(".product-brand").textContent.toString().toLowerCase();
  if(itemName.includes(value.toLowerCase()) || itemBrand.includes(value.toLowerCase())){
    // product.style.display = "flex";
    storage.appendChild(product);
  }
  else{
    product.style.display = "none";
  }
  });
}

function findByPrice(){
    
    const selectedOption = document.querySelector("input[name = priceRange]:checked").value;
    products.forEach(function(product){
      const productPrice = parseFloat(product.querySelector(".product-price").textContent.replace(/\D/g, ''));

      if((selectedOption === "low" && productPrice >= 1500000 && productPrice <= 3000000)
        ||(selectedOption === "medium" && productPrice >= 3000000 && productPrice <= 5000000)
        ||(selectedOption === "high" && productPrice >= 5000000 )
      ){
        product.style.display = "flex";
      }
      else{
        product.style.display = "none";
      }
    })
}



function callSorting(type){
  if(type === "lowToHigh"){
    sortPriceLowToHigh();
  }
  else if(type === "highToLow"){
    sortPriceHighToLow();
  }
  else {
    products.forEach(function (product) {
      product.style.display = "flex";
      storage.appendChild(product);
    });
    
    searchBox.value = "";
    radioButtons.forEach(function (item) {
      item.checked = false;
    });
    sortPriceLowToHigh();
  }
}

function sortPriceLowToHigh(){
  
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

