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