$(document).ready(function () {
  console.log("js filterList loaded");

  const list = $('.list');
  const scrollList = list.find('.scroll-list');
  const dataField = 'data-product-name';

  // Filter the list when the search bar has value
  const searchBar = list.find('.searchBox');
  const items = scrollList.find('.item')

  console.log(searchBar);

  searchBar.on('input', function () {
    scrollList.empty();
    scrollList.append(items);

    const currentItems = scrollList.find('.item');
    const searchValue = searchBar.prop('value').toLowerCase().trim();

    if (searchValue !== "") {
      console.log(`Searching value: "${searchValue}"`);

      currentItems.each(function () {
        const target = $(this);
        const rawData = target.attr(dataField);
        const data = rawData.toLowerCase().trim();

        if (!data.includes(searchValue)) {
          currentItems.remove(`[${dataField}='${rawData}']`);
        }
      });
    }

  });

});

// let brandStorage = document.getElementById("box-brand");
// const brandNameForSearch = document.querySelectorAll(".brandNameForSearch");
// const initialBrandStorageHTML = brandStorage.innerHTML;

// searchBox.addEventListener("input", function (event) {
//   const value = event.target.value.toLowerCase().trim();

//   if (value === "") {
//     brandStorage.innerHTML = initialBrandStorageHTML;
//   } else {
//     brandStorage.innerHTML = "";

//     brandNameForSearch.forEach(function (brand) {
//       const brandText = brand.textContent.toLowerCase();
//       if (brandText.includes(value)) {
//         const listItem = brand.closest("li");
//         brandStorage.appendChild(listItem);
//       }
//     });
//   }
// });