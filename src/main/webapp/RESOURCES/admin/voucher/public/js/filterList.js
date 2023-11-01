$(document).ready(function () {
  console.log("js filterList loaded");

  const list = $('.list');
  const dataField = 'data-product-name';

  const searchBar = list.find('.searchBox');
  const scrollList = list.find('.scroll-list');
  const items = scrollList.find('.item')

  // Sort the list when the page is rendered
  sortAlphabetically(scrollList.find('.item.enabled'), dataField, -1);
  sortAlphabetically(scrollList.find('.item:not(.enabled)'), dataField, 1);

  // Filter the list when the search bar has value
  searchBar.on('input', function () {
    search(scrollList, items, dataField, searchBar)

    // Sort the list after filtering
    sortAlphabetically(scrollList.find('.item.enabled'), dataField, -1);
    sortAlphabetically(scrollList.find('.item:not(.enabled)'), dataField, 1);
  });
});

function search(list, items, dataField, searchBarElement) {
  list.empty();
  list.append(items);

  const currentItems = list.find('.item');
  const searchValue = searchBarElement.prop('value').toLowerCase().trim();

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
}

function sortAlphabetically(list, dataField, priorty) {
  const dictionary = [];
  let count;

  list.each(function () {
    const target = $(this);
    dictionary.push(target.attr(dataField));
  });

  // Resorting the list
  for (let i = 0; i <= dictionary.length - 2; i++) {
    for (let j = 0; j <= dictionary.length - i - 2; j++) {
      const element = dictionary[j];
      const nextElement = dictionary[j + 1];

      if (nextElement.localeCompare(element) == -1) {
        const temp = element;
        dictionary[j] = nextElement;
        dictionary[j + 1] = temp;
      }
    }
  }

  // Setting the correct order for each item
  for (let i = 0; i < dictionary.length; i++) {
    const element = dictionary[i];

    list.each(function () {
      const target = $(this);

      if (target.attr(dataField) === element) {
        if (priorty < 0) {
          count = - ((dictionary.length * -(priorty)) - i);
        } else if (priorty > 0) {
          count = i + (dictionary.length * (priorty - 1));
        } else {
          count = 0;
        }

        target.css("order", count);
      }
    });
  }
}