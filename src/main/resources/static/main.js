const carousel_button_elements = document.querySelectorAll('.btn.btn-info.btn-lg');
const main_button_element = document.querySelector('.button-element');
const modal_aside_price_element = document.querySelector('.modal-right-aside');
const select_element = document.querySelectorAll('select, input');
const select_housing_value = document.querySelector("#housing-type");
const select_badroom_value = document.getElementById("badroom-count");
const select_bathroom_value = document.getElementById("bathroom-count");
const select_checkbox_green = document.querySelector('#greenCheck');
const select_checkbox_deep = document.querySelector('#deepCheck');
const select_checkbox_steam = document.querySelector('#steamCheck');
const select_checkbox_microwave = document.querySelector('#microwaveCheck');





//PRICE AND TIME COUNT
let prices = {
  studio : 150,
  apartments: 170,
  house: 220,
  office: 0.5, //per sq ft
  bedroom: 70,
  bathroom: 50,
  greenClean: 50,
  deepClean: 50,
  steamClean: 50,
  microwaveClean: 30,
  weekend: 50
};


let timings = {
  studio : 90,
  apartments: 120,
  house: 160,
  office: 0.1, //per sq ft
  bedroom: 30,
  bathroom: 20,
  greenClean: 0,
  deepClean: 30,
  steamClean: 30,
  microwaveClean: 20,
  weekend: 30
}


let today = new Date;
let count = 0;
let time = 0;
let maximumDate = new Date().setDate(today.getDate() + 30);
datePickerConfig = {


  altInput: true,
  altFormat: "F j, Y ",
  dateFormat: "Y-m-d",
  minDate: "today",
  maxDate: maximumDate

}
//INIT FUNCTIONS
estimateCount();
setDataMinToday();
const fp = flatpickr("#estimate-time", datePickerConfig);

// EVENT LISTENERS
select_housing_value.addEventListener('change', checkOfficeType);
select_element.forEach(e => e.addEventListener('change', estimateCount));
document.querySelector("#square-ft").addEventListener('input', countSqaureft);

fp.config.onChange.push(() => {
  openTimeSelector();
});

function openTimeSelector() {
  $(".select-interval").addClass("active");
}
// FUNCTION
function estimateCount() {
  count = 0;
  time = 0;
  let house_value = $("#housing-type option:selected").val();
  let bedroom_count = $("#bedroom-count option:selected").val();
  let bathroom_count = $("#bathroom-count option:selected").val();
  let halfBathroom_count = $("#half-bathroom-count option:selected").val();

  let square_ft_count = countSqaureft();

  count += parseInt(house_value) != 2 ? (Object.values(prices)[house_value]) : prices.office * square_ft_count;

  time += parseInt(house_value) != 2 ? (Object.values(timings)[house_value]) : timings.office * square_ft_count;

  count += prices.bedroom * bedroom_count;
  time += timings.bedroom * bedroom_count;

  count += prices.bathroom * bathroom_count;
  time += timings.bathroom * bathroom_count;


  if (select_checkbox_green.checked) {
    count += prices.greenClean;
    time += timings.greenClean;
  }
  if (select_checkbox_deep.checked) {
    count += prices.deepClean;
    time += timings.deepClean;
  }
  if (select_checkbox_steam.checked) {
    count += prices.steamClean;
    time += timings.steamClean;
  }
  if (select_checkbox_microwave.checked) {
    count += prices.microwaveClean;
    time += timings.microwaveClean;
  }

  $('#totalPrice').text(count + "$");
  $('#totalTime').text(time_convert(time.toFixed()) + " min");

  return count;
}

$(".btn").on("click", function () {
  openModal();
});

//removes the "active" class to .popup and .popup-content when the "Close" button is clicked 
$("#close-button, .close-button-x, .modal-overlay ").on("click", function () {
  closeModal();
});

//HELPER FUNCTIONS
function time_convert(num) {
  const hours = Math.floor(num / 60);
  const minutes = num % 60;
  return `${hours + "hours"}: ${minutes > 9 ? minutes : '0' + minutes}`;
}

function setDataMinToday() {
  var today = new Date().toISOString().slice(0, 16);
  document.getElementById("estimate-time").setAttribute('min', today);
}
function openModal() {
  $(".modal-overlay, .modal").addClass("active");
}
function closeModal() {
  $(".modal-overlay, .modal").removeClass("active");
}

function openSquareCount() {
  $("#square-count").addClass("active");
}

function closeSquareCount() {
  $("#square-count").removeClass("active");
}

function countSqaureft() {
  return ($("#square-ft").val());
}

function checkOfficeType() {
  let house_value = $("#housing-type option:selected").val();
  parseInt(house_value) > 1 ? openSquareCount() : closeSquareCount();
}

//AJAX


$(document).ready(function () {

  $("#estimate").submit(function (event) {


    //stop submit the form, we will post it manually.
    event.preventDefault();
    confirm();


  });

});

function confirm() {
  $.confirm({


    title: 'Confirm your selections',
    content: 'Estimate will be processed if confirmed',
    buttons: {

      confirm: {
        btnClass: 'btn-green',
        text: 'Confirm',
        action: function () {
          // $.alert('We received your estimate and will contact you soon !');
          fire_ajax_submit();
        }
      },
      cancel: function () {
      }
    }
  });
}

function fire_ajax_submit() {

  var estimateData = {}
  estimateData["name"] = $("#firstName").val();
  estimateData["lastName"] = $("#lastName").val();
  estimateData["address"] = $("#address").val();
  estimateData["email"] = $("#email").val();
  estimateData["phone"] = $("#phone").val();
  estimateData["housingType"] = $("#housing-type option:selected").text();
  if (parseInt($("#housing-type").val()) === 2) {
    estimateData["squareFt"] = $("#square-ft").val();
  }
  estimateData["bedrooms"] = parseInt($("#bedroom-count").val()) + 1;
  estimateData["bathrooms"] = parseInt($("#bathroom-count").val()) + 1;
  estimateData["greenClean"] = $("#greenCheck").val();
  estimateData["deepClean"] = $("#deepCheck").val();
  estimateData["steamClean"] = $("#steamCheck").val();
  estimateData["microwaveClean"] = $("#microwaveCheck").val();
  estimateData["date"] = $("#estimate-time").val();
  estimateData["time"] = $("#select-time-interval option:selected").text();

  estimateData["estimatedTime"] = $("#totalTime").text();
  estimateData["estimatedPrice"] = $("#totalPrice").text();

  console.log(estimateData);

  $("#make-estimate").prop("disabled", true);

  $.ajax({
    type: "POST",
    contentType: "application/json",
    url: "/estimate",
    data: JSON.stringify(estimateData),
    dataType: 'json',
    cache: false,
    timeout: 600000,
    beforeSend: function () {
      $.LoadingOverlay("show");
    },
    success: function (data) {
      console.log("SUCCESS : ", data);
      $("#loading").dialog("close");
      $.LoadingOverlay("hide");
      successPopUp();
      $("#make-estimate").prop("disabled", false);
    },
    error: function (e) {
      console.log("ERROR : ", e);
      $.LoadingOverlay("hide");
      errorPopUp();
      $("#make-estimate").prop("disabled", false);
    }
  });

}

// POPUPS FUNCTIONS


function successPopUp() {

  $.confirm({
    title: 'Confirmed!',
    content: 'We got your estimate and will contact you soon !',
    type: 'green',
    backgroundDismiss: true,
    typeAnimated: true,
    buttons: {
      success: {
        text: 'OK',
        btnClass: 'btn-green',
        action: function () {
          closeModal();
        }
      }
    }
  });
}

function errorPopUp() {
  $.confirm({
    title: 'Encountered an error!',
    content: 'Something went wrong, give it anothe try',
    type: 'red',
    backgroundDismiss: true,
    typeAnimated: true,
    buttons: {
      tryAgain: {
        text: 'Try again',
        btnClass: 'btn-red',
        action: function () {
        }
      },
      close: function () {
      }
    }
  });

}