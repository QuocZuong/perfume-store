// Variable declaration
const DEBUG = false;
const API_ENDPOINT = "https://dev-online-gateway.ghn.vn/shiip/public-api/master-data";
const HEADERS = {
  token: "57c1c50f-fca4-11ee-8bfa-8a2dda8ec551"
};

const currentScript = document.currentScript
const City = currentScript.getAttribute("data-province") 
const District = currentScript.getAttribute("data-district");
const Ward = currentScript.getAttribute("data-ward");

const DEFAULT_CITY = 'Chọn tỉnh thành';
const DEFAULT_DISTRICT = 'Chọn quận huyện';
const DEFAULT_WARD = 'Chọn phường xã';

// Function declaration

const callAPI = async (API) => {
  if (DEBUG) {
    console.log("Making the initial call to the API...");
    console.log("API PATH: " + API);

    console.log("Initial values:");
    console.log("City: " + City);
    console.log("District: " + District);
    console.log("Ward: " + Ward);
  }

  await renderCity(API);
};

const callApiProvince = async (API) => {
  const API_PATH = API + "/province";
  const RES = await axios.get(API_PATH, { headers: HEADERS });

  if (DEBUG) {
    console.log("Calling Province API...");
    console.log("API path: " + API_PATH);

    console.log("Response:");
    console.log(RES);

    console.log("res.data:");
    console.log(RES.data);
  }

  return RES.data.data;
};

const callApiDistrict = async (API, provinceId) => {
  const API_PATH = API + "/district?province_id=" + provinceId;
  const RES = await axios.get(API_PATH, { headers: HEADERS });

  if (DEBUG) {
    console.log("Calling District API...");
    console.log("API PATH: " + API_PATH);
  }

  return RES.data.data;
};

const callApiWard = async (API, districtId) => {
  const API_PATH = API + "/ward?district_id=" + districtId;
  const RES = await axios.get(API_PATH, { headers: HEADERS });

  if (DEBUG) {
    console.log("Calling Ward API...");
    console.log("API path: " + API_PATH);
  }

  return RES.data.data;
};

const renderCity = async (API) => {
  const data = await callApiProvince(API);
  renderData(data, "city", DEFAULT_CITY);

  if (City !== "") {
    $(`select option[value='` + City + `']`).prop("selected", true);

    const id = $("#city").find(':selected').data('id');

    if (id) {
      await renderDistrict(API, id);
    }
  }
};

const renderDistrict = async (API, provinceId) => {
  const data = await callApiDistrict(API, provinceId);

  if (data) {
    renderData(data, "district", DEFAULT_DISTRICT);

    if (District !== "") {
      $(`select option[value='` + District + `']`).prop("selected", true);

      const id = $("#district").find(':selected').data('id');

      if (id) {
        await renderWard(API, id);
      }
    }
  }
};

const renderWard = async (API, districtId) => {
  const data = await callApiWard(API, districtId);

  if (data) {
    renderData(data, "ward", DEFAULT_WARD);

    if (Ward !== "") {
      $(`select option[value='` + Ward + `']`).prop("selected", true);
    }
  }
};

const renderData = (array, select, msg = DEFAULT_CITY) => {
  let row = ' <option disable value="">' + msg + '</option>';
  let idPropName;
  let locationPropName;

  switch (select) {
    case "city":
      idPropName = "ProvinceID";
      locationPropName = "ProvinceName";
      break;

    case "district":
      idPropName = "DistrictID";
      locationPropName = "DistrictName";
      break;

    case "ward":
      idPropName = "WardCode";
      locationPropName = "WardName";
      break;

    default:
      idPropName = "code";
      locationPropName = "name";
      break;
  }

  array.forEach((e) => {
    const code = e[idPropName];
    const name = e[locationPropName];

    row += `<option data-id="` + code + `" value="` + name + `">` + name + `</option>`;
  });

  document.querySelector("#" + select).innerHTML = row;
};

function resetData(select, msg = DEFAULT_CITY) {
  const row = '<option disable value="">' + msg + '</option>';
  document.querySelector("#" + select).innerHTML = row;
}

// Code execution
if (DEBUG) {
  console.log("Base API endpoints: " + API_ENDPOINT);
}

callAPI(API_ENDPOINT);

$("#city").change(() => {
  resetData("district", DEFAULT_DISTRICT);
  resetData("ward", DEFAULT_WARD);

  const id = $("#city").find(':selected').data('id');

  if (id) {
    renderDistrict(API_ENDPOINT, id);
  }

  printResult();
});

$("#district").change(() => {
  resetData("ward", DEFAULT_WARD);

  const id = $("#district").find(':selected').data('id');

  if (id) {
    renderWard(API_ENDPOINT, id);
  }

  printResult();
});

$("#ward").change(() => {
  printResult();
});

const printResult = () => {
  const isCityEmpty = $("#city").find(':selected').data('id') === "";
  const isDistrictEmpty = $("#district").find(':selected').data('id') === "";
  const isWardEmpty = $("#ward").find(':selected').data('id') === "";

  if (!isCityEmpty && !isDistrictEmpty && !isWardEmpty) {

    const city = $("#city option:selected").text();
    const district = $("#district option:selected").text();
    const ward = $("#ward option:selected").text();
    const sp = " - ";
    let result = (city === DEFAULT_CITY ? "" : city);

    result += (district === DEFAULT_DISTRICT ? "" : sp + district);
    result += (ward === DEFAULT_WARD ? "" : sp + ward);

    const addressInputSelector = currentScript.getAttribute("is-checking-out") ? "input#txtNewAddress" : "input#txtAddress";

    if (city !== DEFAULT_CITY && district !== DEFAULT_DISTRICT && ward !== DEFAULT_WARD) {
      $(addressInputSelector).val(result);

      if (DEBUG) {
        console.log("update value success");
      }
    } else {
     $(addressInputSelector).val("");

      if (DEBUG) {
        console.log("update value null");
      }
    }

  }
};