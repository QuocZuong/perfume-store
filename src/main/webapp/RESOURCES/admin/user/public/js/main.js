/* Modified by Nguyen Le Tai Duc 6:54 P.M 8/7 */
const links = document.querySelectorAll('.list a');
let currentActiveLink = document.querySelector('div.list li a.active');
const pages = document.querySelectorAll('.right > div');
console.log("eeee");

links.forEach((link, index) => {
  if (link == currentActiveLink) {
    hideAllPage();
    currentActiveLink = showPage(index, currentActiveLink);
  }

  link.addEventListener('click', (e) => {
    hideAllPage();
    currentActiveLink = showPage(index, currentActiveLink);
  });
});


function hideAllPage() {
  pages.forEach(page => {
    page.style.display = 'none';
  });
}

function showPage(index, activeLink) {
  pages[index].style.display = 'block';
  activeLink.classList.remove("active");
  links[index].classList.add("active");
  activeLink = links[index];

  return activeLink;
}
