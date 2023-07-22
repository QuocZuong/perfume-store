const links = document.querySelectorAll('.list a');
let currentActiveLink = document.querySelector('div.list li a.active');
const pages = document.querySelectorAll('.right > div');


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


const accountLinks = document.querySelectorAll('.account-link');

accountLinks.forEach((link) => {
  link.addEventListener('click', (e) => {
    console.log("clicked");
    e.preventDefault();
    const targetPage = link.getAttribute('data-page');
    if (targetPage === "order-page") {
      hideAllPage();
      currentActiveLink = showPage(1, currentActiveLink);
    }
    if (targetPage === "address-page") {
      hideAllPage();
      currentActiveLink = showPage(2, currentActiveLink);
    }
    if (targetPage === "info-page") {
      hideAllPage();
      currentActiveLink = showPage(3, currentActiveLink);
    }
  });
});

