const signIn = document.getElementById("signIn")
const signUp = document.getElementById("signUp")
const signInForm = document.getElementById("signInForm")
const signUpForm = document.getElementById("signUpForm")


const btnActiveLoginForm = document.querySelector(".login-form  button.active");

// Handle the first active button
if (btnActiveLoginForm == signIn) {
  console.log("sign in");
  showSignInBlock();
} else if (btnActiveLoginForm == signUp) {
  console.log("sign up");
  showSignUpBlock();
}

signIn.addEventListener("click", () => {
  showSignInBlock();
})
signUp.addEventListener("click", () => {
  showSignUpBlock();
})

function showSignInBlock() {
  signInForm.style.display = "block";
  signUpForm.style.display = "none";
  signIn.classList.add("active");
  signUp.classList.remove("active");
}

function showSignUpBlock() {
  signInForm.style.display = "none";
  signUpForm.style.display = "block";
  signIn.classList.remove("active");
  signUp.classList.add("active");
}



function generateRandomPassword() {
  return Math.random().toString(36).slice(-8);
}
