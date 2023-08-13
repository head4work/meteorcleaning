
const carousel_button_elements = document.querySelectorAll('.btn.btn-info.btn-lg');
const main_button_element = document.querySelector('.button-element');
const modal_aside_price_element = document.querySelector('.modal-right-aside');
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

//valid zipcodes
const zipcodes = [90402, 90401, 90406, 90407, 90408, 90409, 90410, 90411,
    90403, 90404, 90405, 90291, 90077, 90073, 90084, 90025, 90095, 90024, 90064, 90210,
    90067, 90212, 90034, 90209, 90213, 90035, 90211, 90069, 90048, 90046, 90036, 90019, 90028, 90038];
//PRICE AND TIME COUNT
let prices = {
    studio: 100,
    apartments: 130,
    house: 180,
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

let userData = {};

let today = new Date;
let count = 0;
let time = 0;
let maximumDate = new Date().setDate(today.getDate() + 30);
let tomorrow = new Date().setDate(today.getDate() + 1);
let weekendClean = false;
let ocuupiedDates = [];
let disableDates = new Set();
let onlinePaymentBolean = false;

let datePickerConfig = {
    altInput: true,
    altFormat: "F j, Y ",
    dateFormat: "Z",
    minDate: tomorrow,
    maxDate: maximumDate,
}
const fp = flatpickr("#estimate-time", datePickerConfig);

let profile = false;
let dataSet = {};
let tableData = {};
//INIT FUNCTIONS
//getOccupiedDateTimes();


//Visual Studio commented

updateDisableDates();
getPrices();
setDataMinToday();
checkOfficeType();
var table = dataTableInit();


// EVENT LISTENERS
select_housing_value.addEventListener('change', checkOfficeType);
document.getElementById('house-form').querySelectorAll('input, select').forEach(e => e.addEventListener('change', estimateCount));
document.querySelector("#square-ft").addEventListener('input', countSqaureft);
$('#registration-password-1, #registration-password-2').change(startValidation1);
$('#edit-password1, #edit-password2').change(startValidation2);

// flatpicker date listener
fp.config.onChange.push(() => {
    if (fp.selectedDates.length > 0) {
        openTimeSelector();
        // check if selected date is sunday
        weekendClean = checkSunday();
    }
});

// Data table event listener for opening and closing details
$('#archive-orders-table tbody').on('click', 'td.dt-control', function () {
    var tr = $(this).closest('tr');
    var row = table.row(tr);

    if (row.child.isShown()) {
        // This row is already open - close it
        row.child.hide();
        tr.removeClass('shown');
    } else {
        // Open this row
        row.child(format(row.data())).show();
        tr.addClass('shown');
    }
});

function startValidation1() {
    passwordValidation($('#registration-password-1'), $('#registration-password-2'), $('#registration-button'));
}

function startValidation2() {
    passwordValidation($('#edit-password1'), $('#edit-password2'), $('#profile-update'));
}

// FUNCTION

function estimateCount() {
    let count = 0;
    let time = 0;
    let house_value = $("#housing-type option:selected").val();
    let bedroom_count = $("#bedroom-count option:selected").val();
    let bathroom_count = $("#bathroom-count option:selected").val();
    let halfBathroom_count = $("#half-bathroom-count option:selected").val();

    let windows_amount = parseInt($("#windows").val());
    let cabinets_amount = parseInt($("#cabinets").val());


    let square_ft_count = countSqaureft();

    count += parseInt(house_value) == 3 ? prices.office * square_ft_count : parseInt(house_value) == 2 ? countHouseBaseByFt() : (Object.values(prices)[house_value]);

    time += parseInt(house_value) !== 3 ? (Object.values(timings)[house_value]) : timings.office * square_ft_count;

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
        // $("#windows").change(function(){
        //     if( parseInt($("#windows").val())  === 0 ){
        //         $("#windows").addClass("is-invalid");
        //     } else{
        //         $("#windows").removeClass("is-invalid");
        //     }
        // });
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
    //  count = count > prices.houseFt * countSqaureft() ? count : prices.houseFt * countSqaureft();

    //check if selected date is weekend
    count += weekendClean ? prices.weekend : 0;

    count = Math.round(count);
    $('#totalPrice').text(count + "$").val(count);


    $('#totalTime').text(time_convert(time.toFixed()) + " min");
    if (onlinePaymentBolean) {
        let payment = getPayment();
        initialize(payment);
    }
    return count;
}


function updateDisableDates() {
    $.when(getOccupiedDateTimes()).done(function () {
        fp.set("disable", [rmySpecificdays]);
        console.log(disableDates);
        console.log(ocuupiedDates);

    });
}

function openTimeSelector() {
    enableTime();
    let date = fp.selectedDates[0].toISOString().substring(0, 10);
    let index = ocuupiedDates.findIndex(e => e === date);

    if (index > -1) {
        let timePeriod = ocuupiedDates[index + 1]
        disableTime(timePeriod);
    }

    $(".select-interval").addClass("active");
}

function disableTime(time) {
    $('#select-time-interval').val(time === 0 ? 1 : 0);
    $('#select-time-interval option[value="' + time + '"]').attr("disabled", true).addClass("disabled");
}

function enableTime() {
    $('#select-time-interval').children().attr("disabled", false).removeClass("disabled");
}

function rmySpecificdays(date) {
    return disableDates.has(date.toISOString().substring(0, 10));
}

function getOccupiedDateTimes() {
    return $.ajax({
        type: "GET",
        url: "/dates",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (response) {
            $.each(response, function (k, v) {
                if (v > 2) {
                    disableDates.add(k);
                }
                ocuupiedDates.push(k, v);
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

function printSelection(n) {
    return n ? '&#9989;' : '&#10006;';
}

function printSelectionCount(n) {
    return n !== 0 ? n : '&#10006;';
}

/* Formatting function for row details - modify as you need */
function format(d) {
    // `d` is the original data object for the row
    let house_type = "Studio";
    switch (d.housingType) {
        case "1":
            house_type = "Apartments"
            break;
        case "2":
            house_type = "House"
            break;
        case "3":
            house_type = "Office"
            break;
    }
    let squareFt = d.squareFt !== null ? d.squareFt : '&#10006;';
    return (
        '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">' +


        '<tr>' +
        '<th>HOUSE</th>' +
        '<th>DATA</th>' +
        '<th>SELECTION</th>' +
        '<th>DATA</th>' +
        '</tr>' +

        '<tr>' +
        '<td>Housing type:</td>' +
        '<td>' + house_type + '</td>' +
        '<td>Green clean:</td>' +
        '<td>' + printSelection(d.greenClean) + '</td>' +
        '</tr>' +

        '<tr>' +
        '<td>Square ft:</td>' +
        '<td>' + squareFt + '</td>' +
        '<td>Deep clean:</td>' +
        '<td>' + printSelection(d.deepClean) + '</td>' +
        '</tr>' +

        '<tr>' +
        '<td>Bedrooms:</td>' +
        '<td>' + d.bedrooms + '</td>' +
        '<td>Microwave clean:</td>' +
        '<td>' + printSelection(d.microwaveClean) + '</td>' +
        '</tr>' +

        '<tr>' +
        '<td>Bathrooms ft:</td>' +
        '<td>' + d.bathrooms + '</td>' +
        '<td>Refrigerator clean:</td>' +
        '<td>' + printSelection(d.refrigeratorClean) + '</td>' +
        '</tr>' +

        '<tr>' +
        '<td>Half bathrooms:</td>' +
        '<td>' + d.halfBathrooms + '</td>' +
        '<td>Oven clean:</td>' +
        '<td>' + printSelection(d.ovenClean) + '</td>' +
        '</tr>' +


        '<tr>' +
        '<td>Payment status</td>' +
        '<td>' + printSelection(d.paid) + '</td>' +
        '<td>Windows clean:</td>' +
        '<td>' + printSelectionCount(d.windowClean) + '</td>' +
        '</tr>' +

        '<tr>' +
        '<td></td>' +
        '<td></td>' +
        '<td>Cabinet clean:</td>' +
        '<td>' + printSelectionCount(d.cabinetClean) + '</td>' +
        '</tr>' +

        '<tr>' +
        '<td></td>' +
        '<td></td>' +
        '<td>Dishes clean:</td>' +
        '<td>' + printSelection(d.dishesClean) + '</td>' +
        '</tr>' +

        '</table>'
    );
}

function dataTableInit() {
    return $('#archive-orders-table').DataTable({
        // destroy: true,
        responsive: {
            details: false,
            breakpoints: [
                {name: 'desktop', width: Infinity},
                {name: 'tablet', width: 1024},
                {name: 'fablet', width: 768},
                {name: 'phone', width: 480}
            ]
        },

        data: userData.orders,
        "createdRow": function (row, data, dataIndex) {
            if (data.paid) {
                $(row).addClass('greenClass');
            }
        },
        columns: [
            {
                className: 'dt-control',
                orderable: false,
                data: null,
                defaultContent: '',
            },
            {data: 'id'},
            {
                data: 'dateTime',
                render: DataTable.render.datetime()
            },
            {data: 'name'},
            {data: 'address'},
            {data: 'estimatedPrice'},
            {
                data: 'paid', render: function (data) {
                    return printSelection(data)
                }
            }
        ],

        "language": {
            "emptyTable": "You have no archive orders yet"
        },
    });

}


function getOrders() {
    return $.ajax({
        type: "GET",
        url: "/rest/profile/orders",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (response) {
            Object.assign(tableData, response);
        },
        error: function (response) {
            console.log("Orders load error");
        }
    });
}

function getPrices() {
    $.ajax({
        type: "GET",
        url: "/prices",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (response) {
            Object.assign(prices, response);
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

function getUserData() {
    $.ajax({
        type: "GET",
        url: "/rest/profile",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (response) {
            Object.assign(userData, response);
            fillUserData();
        },
        error: function (response) {
            console.log("Error getting user details : " + response);
        }
    });
}



//Buttons binds

$("#carouselBook1,#carouselBook2,#carouselBook3,#how-it-works-book,.bookin").on("click", function () {
openEstimate();
});


$("#login-nav").on("click", function () {
    profile = true;
    openModal();
    $('.modal').attr('class', "modal active profile-login");
    openProfile('login');
});


$('#modal-new-order').on("click", function () {
    $('.modal').attr('class', "modal active profile-order");
    openProfile('profile-buttons', 'modal-left-aside', 'modal-right-aside', 'provide-details');
});

$('#modal-edit').on("click", function () {
    $('.modal').attr('class', "modal active profile-edit");
    openProfile('profile-buttons', 'profile-edit');
});

$('#modal-archive-orders').on("click", function () {
    $('.modal').attr('class', "modal active profile-archive");
    openProfile('profile-buttons', 'archive-table');
    table.clear().rows.add(userData.orders).draw();
    // getOrders();
});

//removes the "active" class to .popup and .popup-content when the "Close" button is clicked 
$(".close-button, .close-button-x, .modal-overlay").on("click", function () {
    closeModal();
});

//HELPER FUNCTIONS
function openEstimate() {
    getOccupiedDateTimes();
    estimateCount();
    getUserData();
    profile = false;
    openModal();
    $('.modal').attr('class', "modal active");
    openProfile('modal-left-aside', 'modal-right-aside', 'provide-details');
}

// show elements provided in parameters 
function openProfile(...elements) {

    //default close buttons
    elements.push('close-button-x', 'close-button');

    $.each($('.modal').children(), function (index, value) {
        if (elements.includes(value.className)) {
            $(value).show();
        } else {
            $(value).hide();
        }
    });
}

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

function countHouseBaseByFt() {
    let feets = parseInt(countSqaureft());
    return feets > 1000 ? ((feets - 1000) * prices.houseFt) + prices.house : prices.house;
}

function checkOfficeType() {
    let square_count = "#square-count";
    let house_value = $("#housing-type option:selected").val();
    if (parseInt(house_value) === 3) {
        $("#bedroom-count, #bathroom-count,#half-bathroom-count").val(0);
        //   $("#half-bathroom-count").val(0);
    } else {
        $("#bedroom-count, #bathroom-count").val(1);
        $("#half-bathroom-count").val(0);
    }
    if (parseInt(house_value) > 1) {
        openElementCount(square_count);
    } else {
        closeElementCount(square_count);
        $('#square-ft').val(100);
    }

    if ($('title').text() !== "Edit") {
        parseInt(house_value) === 3 || parseInt(house_value) === 0 ? disableHousingElements() : enableHousingElements();
    }

}

//AJAX
function dateValidRequired() {
    return !isNaN(getDateTime());
}

//submit buttons
$(document).ready(function () {

    if ($('title').text() !== "Edit") {


        $("#login").submit(function (event) {
            event.preventDefault();
            ajaxLogin();
        });
        $("#registration-form").submit(function (event) {
            event.preventDefault();
            ajaxRegistration();
        });

        $("#forgot").submit(function (event) {
            event.preventDefault();
            retrievePassword();
        });

        $("#estimate").submit(function (event) {
            //stop submit the form, we will post it manually.
            event.preventDefault();


            //check zipcode in area of operating
            if (zipValidation($("#zipcode").val())) {
                //check date provided
                dateValidRequired() ? confirm() : dateEmptyPopup();
            } else {
                zipOutOfOpertaionZone();
            }


        });

        $("#profile-edit-form").submit(function (event) {
            event.preventDefault();
            $.confirm({
                title: 'Confirm your selections',
                content: 'Update account information',
                buttons: {

                    confirm: {
                        btnClass: 'btn-green',
                        text: 'Confirm',
                        action: function () {
                            ajaxUpdate();
                        }
                    },
                    cancel: function () {
                    }
                }

            });
        });
    }
});

function forgotPassword() {
    $('#login').hide();
    $('#forgot').show();


    $(document).click(function (e) {
        if ($(e.target).is('.modal-overlay,#close-button, .btn-close, #login-btn')) {
            $('#login').show();
            $('#forgot').hide();
        }
    });


}

function retrievePassword() {
    var search = {}
    search["email"] = $("#forgot-email").val();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/rest/profile/forgot",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        beforeSend: function () {
            $.LoadingOverlay("show");
        },
        success: function (data) {
            $.LoadingOverlay("hide");
            successPopUp("Success!", "You will recieve an email with further instructions on address: " + data.email)
        },
        error: function (e) {
            $.LoadingOverlay("hide");
            showErrorAsDiv(e.responseJSON.detail, $('#forgot-email-div'));
        }
    });
}

function showLogin() {
    $('.modal').toggleClass("login")
    $('#login-section').toggle();
}

function changePasswordToggle() {
    $('#edit-password1, #edit-password2').val('');
    $('#change-password').toggle();
    $('#edit-password1, #edit-password2').prop('disabled', (i, v) => !v);
}

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

function getEstimateData() {
    var estimateData = {};
    estimateData["name"] = $("#firstName").val();
    estimateData["lastName"] = $("#lastName").val();
    estimateData["address"] = $("#address").val();
    estimateData["email"] = $("#email").val();
    estimateData["phone"] = $("#phone").val();
    estimateData["housingType"] = parseInt($("#housing-type").val());
    if (estimateData.housingType > 1) {
        estimateData["squareFt"] = $("#square-ft").val();
    }
    estimateData["bedrooms"] = parseInt($("#bedroom-count").val());
    estimateData["bathrooms"] = parseInt($("#bathroom-count").val());
    estimateData["halfBathrooms"] = parseInt($("#half-bathroom-count").val());
    estimateData["greenClean"] = $("#greenCheck").prop("checked");
    estimateData["deepClean"] = $("#deepCheck").prop("checked");
    estimateData["microwaveClean"] = $("#microwaveCheck").prop("checked");
    estimateData["refrigeratorClean"] = $("#refrigeratorCheck").prop("checked");
    estimateData["ovenClean"] = $("#ovenCheck").prop("checked");
    estimateData["windowClean"] = parseInt($("#windows").val());
    estimateData["cabinetClean"] = parseInt($("#cabinets").val());
    estimateData["dishesClean"] = $("#dishesCheck").prop("checked");
    estimateData["dateTime"] = getDateTime();
    estimateData["estimatedTime"] = $("#totalTime").text();
    estimateData["estimatedPrice"] = parseInt($("#totalPrice").text().slice(0, -1));
    return estimateData;
}


function fire_ajax_submit() {
    var estimateData = getEstimateData();

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
            successPopUp('Confirmed!', 'We got your estimate and will contact you soon !');
            $("#make-estimate").prop("disabled", false);
            $("#estimate").trigger("reset");
            updateDisableDates();
            fp.clear();
        },
        error: function (e) {
            console.log("ERROR : ", e);
            $.LoadingOverlay("hide");
            errorPopUp(e.responseJSON.detail);
            $("#make-estimate").prop("disabled", false);
        }
    });

}

function ajaxRegistration() {
    let userTo = {};
    userTo["name"] = $("#registration-name").val();
    userTo["email"] = $("#registration-email").val();
    userTo["password"] = $("#registration-password-1").val();
    userTo["enabled"] = true;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/rest/profile",
        data: JSON.stringify(userTo),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        beforeSend: function () {
            console.log(userTo);

            $.LoadingOverlay("show");

        },
        success: function (data) {
            console.log("SUCCESS : ", data);
            $("#loading").dialog("close");
            $.LoadingOverlay("hide");
            document.getElementById("registration-form").reset(); //clear form

            $.confirm({
                title: 'Your account been created!',
                content: 'You can now proceed to login page or continue browsing',
                type: 'green',
                backgroundDismiss: true,
                typeAnimated: true,
                buttons: {
                    login: {
                        text: 'Login',
                        btnClass: 'btn-green',
                        action: function () {
                            goToProfileLogin();
                        }
                    },
                    close: function () {
                    }
                }
            });


        },
        error: function (e) {
            showErrorAsDiv(e.responseJSON.detail, $('#password-field-2'));
            $.LoadingOverlay("hide");
        }
    });
}

function goToProfileLogin() {
    openModal();
    $('.modal').attr('class', "modal active profile-login");
    openProfile('login');
}

function ajaxLogin() {

    $.ajax({
        url: "/perform_login",
        type: 'POST',
        beforeSend: function (xhr) {
            xhr.withCredentials = true;
        },
        data: $('#login').serialize(),

        success: function (response) {
            $('.modal').removeClass("login");

            getUserData();


            //refresh element
            $("#navbar-nav").load(location.href + " #navbar-nav");

            if (profile) {
                closeModal();
                openProfileButton();
            }
        },
        error: function (res) {
            console.log(res);
            let info = JSON.parse(res.responseText);
            showErrorAsDiv(info.exception, $('.passfield'));
        },
    });

}

function ajaxUpdate() {
    let userTo = {};
    userTo["name"] = $("#edit-firstName").val();
    userTo["email"] = $("#edit-email").val();
    userTo["password"] = $("#edit-password1").val();
    userTo["address"] = $("#edit-address").val();
    userTo["phone"] = $("#edit-phone").val();
    userTo["enabled"] = true;


    $.ajax({
        url: "/rest/profile",
        type: 'PUT',
        contentType: "application/json",
        data: JSON.stringify(userTo),
        dataType: 'json',
        cache: false,
        timeout: 600000,

        beforeSend: function (xhr) {
            $.LoadingOverlay("show");
        },

        success: function (response) {
            $("#loading").dialog("close");
            $.LoadingOverlay("hide");

            getUserData();
        },
        error: function (res) {
            $("#loading").dialog("close");
            $.LoadingOverlay("hide");
            console.log(res);
            let info = JSON.parse(res.responseText);
            showErrorAsDiv(info.detail, $('.passfield'));
        },
    });

}

function
fillUserData() {
    $('#billingInfo').text("Logged as " + userData.name);
    $('#firstName,#edit-firstName').val(userData.name).addClass("active");
    $('#email, #edit-email').val(userData.email).addClass("active");
    $('#address, #edit-address').val(userData.address).addClass("active");
    $('#phone, #edit-phone').val(userData.phone).addClass("active");

    //clear password fields
    // $('#edit-password1,#edit-password2').val('');
    // startValidation2();

    //replace login button with logout
    $('#login-section, #login-btn, #or ').hide();
    $('#logout-btn').show();
}

function openProfileButton() {
    profile = true;
    getUserData();
    openModal();
    $('.modal').attr('class', "modal active profile-edit");
    openProfile('profile-buttons', 'profile-edit');
}

function showErrorAsDiv(message, element) {
    element.removeClass("mb-4").addClass("mb-1");
    $('<div class="d-flex flex-row align-items-center mb-4 alert-wrap"><i class="fas fa-exclamation-triangle fa-lg me-3 fa-fw text-danger"></i><div class=" alert alert-danger w-100" role="alert"></div></div>').insertAfter(element);
    $('.alert').text(message);
    $(document).click(function () {
        $('.alert-wrap').remove();
        element.removeClass("mb-1").addClass("mb-4");
    });
}

function showError(error) {
    $.confirm({
        title: 'Error',
        content: error,
        type: 'red',
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

// POPUPS FUNCTIONS
function dateEmptyPopup() {
    $.alert({
        type: 'red',
        typeAnimated: true,
        closeIcon: true,
        title: 'Date is missing!',
        content: 'Please pick a date.',
    });
}

function zipOutOfOpertaionZone() {
    $.alert({
        type: 'orange',
        typeAnimated: true,
        closeIcon: true,
        title: 'The address is out of the operational range.',
        content: 'Unfortunately, your address is currently outside of our operational zone. We apologize for the inconvenience and hope that we can serve you in the future! ' +
            '<br> <h3 style="justify-self: center;padding-top:1rem">Operational zone</h3> <br><iframe style="width:100%;" id="map-frame" src="https://www.google.com/maps/d/embed?mid=1N0DL_3qPEBhVvMsA58JpuxC5bNknJVc&ehbc=2E312F" width="640" height="480"></iframe> ',
    });
}

function zipValidation(zip) {
    zip = parseInt(zip);
    return zipcodes.includes(zip);
}

function successPopUp(header, message) {
    $.confirm({
        title: header,
        content: message,
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

function passwordsAreEqual(el1, el2) {
    return $(el1).val() === $(el2).val();
}

function passwordValidation(element1, element2, button) {
    if (passwordsAreEqual(element1, element2)) {
        element2.removeClass("is-invalid mb-0");
        button.prop('disabled', false);
    } else {
        button.prop('disabled', true);
        element2.addClass("is-invalid mb-0");
    }
}

function errorPopUp(message) {
    $.confirm({
        title: 'Encountered an error!',
        content: message,
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