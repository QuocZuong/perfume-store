const characterLinks = document.getElementsByClassName("character");
const brandContainer = document.getElementsByClassName("brands");


for(let i = 1; i < characterLinks.length; i++){
  const character = characterLinks[i];
  
  character.addEventListener("click", function(event){
    event.preventDefault();
    const characterText = character.innerText.trim();

    for (let j = 1; j < characterLinks.length; j++) {
      characterLinks[j].style.fontWeight = "100";
    }
    
    character.style.fontWeight = "bold";

    if(characterText === "ALL BRANDS"){
      for(let j = 0; j < brandContainer.length; j++){
        const brand = brandContainer[j];
        brand.style.opacity = 1;
      }
    }

    else{
      for(let j = 0; j < brandContainer.length; j++){
        const brand = brandContainer[j];
        const brandTitle = brand.querySelector("h3").innerText;
        
        if(brandTitle.toLowerCase() !== characterText.toLowerCase()){
          brand.style.opacity = 0.3;
        }
  
        else if(brandTitle.toLowerCase() === characterText.toLowerCase()){
          const scrollPosition = brand.offsetTop - 40;
          brand.style.opacity = 1;
          // brand.scrollIntoView({behavior:"smooth"});
          window.scrollTo({ top: scrollPosition, behavior: "smooth" });

        }
      }
    }
  
  });
}