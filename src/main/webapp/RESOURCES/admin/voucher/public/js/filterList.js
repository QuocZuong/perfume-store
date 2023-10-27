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