const links = document.querySelectorAll('.list a');

const pages = document.querySelectorAll('.right > div');
links.forEach((link, index) => {
  link.addEventListener('click', (e) => {
    e.preventDefault();
    console.log("click");
    pages.forEach(page => {
      page.style.display = 'none';
    });

    pages[index].style.display = 'block';
  });
});