const signIn = document.getElementById("signIn")
const signUp = document.getElementById("signUp")
const signInForm = document.getElementById("signInForm")
const signUpForm = document.getElementById("signUpForm")


signIn.addEventListener("click", ()=>{
  signInForm.style.display = "block";
  signUpForm.style.display = "none";
  signIn.classList.add("active");
  signUp.classList.remove("active");
  
})
signUp.addEventListener("click", ()=>{
  signInForm.style.display = "none";
  signUpForm.style.display = "block";
  signIn.classList.remove("active");
  signUp.classList.add("active");
})