const carousel_button_elements = document.querySelectorAll('.btn.btn-info.btn-lg');
const main_button_element = document.querySelector('.button-element');
const modal_aside_price_element = document.querySelector('.modal-right-aside');
const select_element = document.querySelectorAll('select, input');
const select_housing_value = document.querySelector("#housing-type");
const select_badroom_value = document.getElementById("badroom-count");
const select_bathroom_value = document.getElementById("bathroom-count");
const select_checkbox_green = document.querySelector('#greenCheck');
const select_checkbox_deep = document.querySelector('#deepCheck');

const select_checkbox_window = document.querySelector('#windowCheck');
const select_checkbox_cabinet = document.querySelector('#cabinetCheck');

// const select_checkbox_steam = document.querySelector('#steamCheck');
const select_checkbox_microwave = document.querySelector('#microwaveCheck');

const select_checkbox_refrigerator = document.querySelector('#refrigeratorCheck');
const select_checkbox_oven = document.querySelector('#ovenCheck');


const select_checkbox_dishes = document.querySelector('#dishesCheck');





//PRICE AND TIME COUNT
let prices = {
  studio: 140,
  apartments: 170,
  house: 220,
  houseFt: 0.5, //per sq ft
  office: 0.3, //per sq ft
  bedroom: 20,
  bathroom: 24, // half bathroom is 1/2 
  greenClean: 40,
  deepClean: 1.3, // coaf * multiply base + rooms
  // steamClean: 30,
  microwaveClean: 20,
  refrigeratorClean: 30,
  ovenClean: 30,
  window: 8,
  cabinet: 10,
  dishesWash: 10,
  weekend: 50
};


let timings = {
  studio: 90,
  apartments: 120,
  house: 160,
  office: 0.1, //per sq ft
  bedroom: 30,
  bathroom: 20,
  greenClean: 0,
  deepClean: 30,
  // steamClean: 30,
  microwaveClean: 20,
  refrigeratorClean: 10,
  ovenClean: 10,
  dishesWash: 10,
  weekend: 30
}


let today = new Date;
let count = 0;
let time = 0;
let maximumDate = new Date().setDate(today.getDate() + 30);
let tomorrow = new Date().setDate(today.getDate() + 1);
let weekendClean = false;
let ocuupiedDates = [];
let disableDates = new Set();

let datePickerConfig = {
  altInput: true,
  altFormat: "F j, Y ",
  dateFormat: "Z",
  minDate: tomorrow,
  maxDate: maximumDate,
}
const fp = flatpickr("#estimate-time",datePickerConfig);


//INIT FUNCTIONS
//getOccupiedDateTimes();
updateDisableDates();
getPrices();
setDataMinToday();
checkOfficeType();



// EVENT LISTENERS
select_housing_value.addEventListener('change', checkOfficeType);
select_element.forEach(e => e.addEventListener('change', estimateCount));
document.querySelector("#square-ft").addEventListener('input', countSqaureft);

fp.config.onChange.push(() => {
  if (fp.selectedDates.length > 0){
    openTimeSelector();
    // check if selected date is sunday
    weekendClean = checkSunday();
  }
});



// FUNCTION
function updateDisableDates() {
  $.when(getOccupiedDateTimes()).done(function (){
    fp.set("disable",[rmySpecificdays]);
    console.log(disableDates);
    console.log(ocuupiedDates);

  });
}

function openTimeSelector() {
  enableTime();
  let date = fp.selectedDates[0].toISOString().substring(0, 10);
  let index = ocuupiedDates.findIndex(e => e === date);

 if(index > -1 ){
  let timePeriod = ocuupiedDates[index + 1]
   disableTime(timePeriod);
 }

  $(".select-interval").addClass("active");
}
function disableTime(time){
  $('#select-time-interval').val(time === 0 ? 1 : 0);
  $('#select-time-interval option[value="'+time+'"]').attr("disabled", true).addClass("disabled");
}

function enableTime(){
  $('#select-time-interval').children().attr("disabled", false).removeClass("disabled");
}

function rmySpecificdays(date) {
  return disableDates.has(date.toISOString().substring(0, 10));
}

function getOccupiedDateTimes(){
 return  $.ajax({
    type: "GET",
    url: "/dates",
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    success: function (response) {
     $.each(response, function(k,v){
      if(v > 2){
        disableDates.add(k);
      } ocuupiedDates.push(k,v);
     })
    },
    failure: function (response) {
      alert("Failed to load dates");
    },
    error: function (response) {
      alert("Failed to load dates");
    }
  });
}

function getPrices(){
  $.ajax({
    type: "GET",
    url: "/prices",
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    success: function (response) {
      let newPrices = Object.assign(prices,response);
      estimateCount();
    },
    failure: function (response) {
      alert("Failed to load prices");
    },
    error: function (response) {
      alert("Failed to load prices");
    }
  });
}
function estimateCount() {
  count = 0;
  time = 0;
  let house_value = $("#housing-type option:selected").val();
  let bedroom_count = $("#bedroom-count option:selected").val();
  let bathroom_count = $("#bathroom-count option:selected").val();
  let halfBathroom_count = $("#half-bathroom-count option:selected").val();
 
  let windows_amount = parseInt($("#windows").val());
  let cabinets_amount = parseInt($("#cabinets").val());


  let square_ft_count = countSqaureft();

  count += parseInt(house_value) != 3 ? (Object.values(prices)[house_value]) : prices.office * square_ft_count;

  time += parseInt(house_value) != 3 ? (Object.values(timings)[house_value]) : timings.office * square_ft_count;

  count += prices.bedroom * bedroom_count;
  time += timings.bedroom * bedroom_count;

  count += prices.bathroom * bathroom_count;

  count += (prices.bathroom / 2) * halfBathroom_count;

  time += timings.bathroom * bathroom_count;


  if (select_checkbox_green.checked) {
    count += prices.greenClean;
    time += timings.greenClean;
  }
  if (select_checkbox_deep.checked) {
    count = Math.round(count * prices.deepClean);
    time += timings.deepClean;
  }
  // if (select_checkbox_steam.checked) {
  //   count += prices.steamClean;
  //   time += timings.steamClean;
  // }
  if (select_checkbox_microwave.checked) {
    count += prices.microwaveClean;
    time += timings.microwaveClean;
  }
  if (select_checkbox_refrigerator.checked) {
    count += prices.refrigeratorClean;
    time += timings.refrigeratorClean;
  }
  if (select_checkbox_oven.checked) {
    count += prices.ovenClean;
    time += timings.ovenClean;
  }

  if (select_checkbox_window.checked) {
    openElementCount("#window-count");
    count += prices.window * windows_amount;
    time += timings.dishesWash;
  } else {
    closeElementCount("#window-count");
    $("#windows").val(0);
  }

  if (select_checkbox_cabinet.checked) {
    openElementCount("#cabinet-count");
    count += prices.cabinet * cabinets_amount;
    time += timings.dishesWash;
  } else {
    closeElementCount("#cabinet-count");
    $("#cabinets").val(0);
  }

  if (select_checkbox_dishes.checked) {
    count += prices.dishesWash;
    time += timings.dishesWash;
  }

  // price for house declare by choosing higher option
  count = count > prices.houseFt * $("#square-ft").val() ? count : prices.houseFt * $("#square-ft").val();

  //check if selected date is weekend
  count += weekendClean ? prices.weekend : 0;

  $('#totalPrice').text(count + "$");
  $('#totalPrice').val(count);

  $('#totalTime').text(time_convert(time.toFixed()) + " min");

  return count;
}

$("#carouselBook1,#carouselBook2,#carouselBook3").on("click", function () {
  getOccupiedDateTimes();
  estimateCount();
  openModal();
});

//removes the "active" class to .popup and .popup-content when the "Close" button is clicked 
$("#close-button, .close-button-x, .modal-overlay ").on("click", function () {
  closeModal();
});

//HELPER FUNCTIONS
function checkSunday() {
  let date = fp.selectedDates[0];
  return date.getDay() === 0;
}

function getDateTime() {
  let dateTime = new Date($("#estimate-time").val());
  let hours = parseInt($("#select-time-interval").val()) === 0 ? 11 : 15;
  dateTime.setUTCHours(hours);
  return dateTime;
}

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

function openElementCount(element) {
  $(element).addClass("active");

}

function closeElementCount(element) {
  $(element).removeClass("active");

}

function disableHousingElements() {
  $("#bedroom-count, #bathroom-count, #half-bathroom-count").prop("disabled", true);
}

function enableHousingElements() {
  $("#bedroom-count, #bathroom-count, #half-bathroom-count").prop("disabled", false);
}

function countSqaureft() {
  return ($("#square-ft").val());
}

function checkOfficeType() {
  let square_count = "#square-count";
  let house_value = $("#housing-type option:selected").val();
  if (parseInt(house_value) === 0){
    $("#bedroom-count, #bathroom-count, #half-bathroom-count").val(0);
  }
  parseInt(house_value) > 1 ? openElementCount(square_count) : closeElementCount(square_count);
  if ($('title').text() != "Edit"){
    parseInt(house_value) === 3 || parseInt(house_value) === 0 ? disableHousingElements() : enableHousingElements();
  }

}

//AJAX
function dateValidRequired() {
  return !isNaN(getDateTime());
}

$(document).ready(function () {
  if ($('title').text() != "Edit"){
    $("#estimate").submit(function (event) {
      //stop submit the form, we will post it manually.
      event.preventDefault();

      //check email provided
      dateValidRequired() ? confirm() : emailEmptyPopup();
    });
  }


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
  //estimateData["housingType"] = $("#housing-type option:selected").text();
  estimateData["housingType"] = parseInt($("#housing-type").val());

  if (estimateData.housingType > 1) {
    estimateData["squareFt"] = $("#square-ft").val();
  }
  estimateData["bedrooms"] = parseInt($("#bedroom-count").val()) + 1;
  estimateData["bathrooms"] = parseInt($("#bathroom-count").val()) + 1;
  estimateData["halfBathrooms"] = parseInt($("#half-bathroom-count").val());

  estimateData["greenClean"] = $("#greenCheck").prop("checked");
  estimateData["deepClean"] = $("#deepCheck").prop("checked");
  // estimateData["steamClean"] = $("#steamCheck").val();
  estimateData["microwaveClean"] = $("#microwaveCheck").prop("checked");

  estimateData["refrigeratorClean"] = $("#refrigeratorCheck").prop("checked");

  estimateData["ovenClean"] = $("#ovenCheck").prop("checked");

  estimateData["windowClean"] = parseInt($("#windows").val());
  estimateData["cabinetClean"] = parseInt($("#cabinets").val());

  estimateData["dishesClean"] = $("#dishesCheck").prop("checked");

  estimateData["dateTime"] = getDateTime();

  // estimateData["time"] = $("#select-time-interval option:selected").text();

  estimateData["estimatedTime"] = $("#totalTime").text();
  estimateData["estimatedPrice"] = parseInt($("#totalPrice").text().slice(0,-1));


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
      $("#estimate").trigger("reset");
      updateDisableDates();
      fp.clear();
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
function emailEmptyPopup() {
  $.alert({
    type: 'red',
    typeAnimated: true,
    closeIcon: true,
    title: 'Date is missing!',
    content: 'Please pick a date.',
  });
}

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