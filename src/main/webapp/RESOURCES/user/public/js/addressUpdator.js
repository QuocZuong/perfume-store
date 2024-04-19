// This script is used to handle changing address in address list
$(document).ready(function () {

  let city = "";
  let district = "";
  let ward = "";
  let phoneNumber = "";
  let addressId = "";
  let status = "";
  let receiver = "";
  const DEBUG = true;

  $('.delivery-address-item').click(function (e) {

    const target = $(this);

    city = target.attr('data-address-city');
    district = target.attr('data-address-district');
    ward = target.attr('data-address-ward');
    phoneNumber = target.attr('data-phone-number');
    addressId = target.attr('data-address-id');
    status = target.attr('data-status');
    receiver = target.attr('data-receiver-name');

    // debugging
    if (DEBUG) {
      console.log({ city, district, ward, phoneNumber, addressId, status, receiver });
    }

    $('.delivery-address-item').removeClass('active');
    target.addClass('active');

    changeAddress(city, district, ward, phoneNumber, status, receiver);
  });

  async function changeAddress(city, district, ward, phoneNumber, status, receiver) {
    $("select[id='city'] > option[selected]").prop("selected", false);
    $("select[id='district'] > option[selected]").prop("selected", false);
    $("select[id='ward'] > option[selected]").prop("selected", false);

    $("select[id='city'] > option[value*='" + city + "']").prop("selected", true);

    const provinceId = $("#city").find(':selected').data('id');

    if (DEBUG) {
      console.log("Province changed: " + provinceId);
    }

    if (provinceId) {
      if (DEBUG) {
        console.log("Calling districtAPI...");
      }

      await renderDistrict(API_ENDPOINT, provinceId);
      printResult();
    }


    $("select[id='district'] > option[value*='" + district + "']").prop("selected", true);

    const districtId = $("#district").find(':selected').data('id');

    if (DEBUG) {
      console.log("District changed: " + districtId);
    }

    if (districtId) {
      if (DEBUG) {
        console.log("Calling wardAPI...");
      }

      await renderWard(API_ENDPOINT, districtId);
      printResult();
    }

    $("select[id='ward'] > option[value*='" + ward + "']").prop("selected", true);
    printResult();

    $('#txtPhoneNumber').val(phoneNumber);
    $('#txtAddressId').val(addressId);
    $('#txtStatus').val(status);
    $('#txtStatus').prop('checked', status === "Default");
    $('#txtReceiverName').val(receiver);
  }

});