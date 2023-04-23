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
let weekendClean = false;

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


document.addEventListener("DOMContentLoaded", function (event) {

    const showNavbar = (toggleId, navId, bodyId, headerId) => {
        const toggle = document.getElementById(toggleId),
            nav = document.getElementById(navId),
            bodypd = document.getElementById(bodyId),
            headerpd = document.getElementById(headerId)

        // Validate that all variables exist
        if(toggle && nav && bodypd && headerpd){
            toggle.addEventListener('click', ()=>{
                // show navbar
                nav.classList.toggle('show')
                // change icon
                toggle.classList.toggle('bx-x')
                // add padding to body
                bodypd.classList.toggle('body-pd')
                // add padding to header
                headerpd.classList.toggle('body-pd')
            })
        }
    }

    showNavbar('header-toggle','nav-bar','body-pd','header')

    /*===== LINK ACTIVE =====*/
    const linkColor = document.querySelectorAll('.nav_link')

    function colorLink(){
        if(linkColor){
            linkColor.forEach(l=> l.classList.remove('active'))
            this.classList.add('active')
        }
    }

    linkColor.forEach(l => l.addEventListener('click', colorLink))

    // Your code to run since DOM is loaded and ready


});

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

function countSqaureft() {
    return ($("#square-ft").val());
}

function estimateCount() {
    let count = 0;

    let house_value = $("#housing-type option:selected").val();
    let bedroom_count = $("#bedroom-count option:selected").val();
    let bathroom_count = $("#bathroom-count option:selected").val();
    let halfBathroom_count = $("#half-bathroom-count option:selected").val();

    let windows_amount = parseInt($("#windowCheck").val());
    let cabinets_amount = parseInt($("#cabinetCheck").val());


    let square_ft_count = countSqaureft();

    count += parseInt(house_value) !== 3 ? (Object.values(prices)[house_value]) : prices.office * square_ft_count;


    count += prices.bedroom * bedroom_count;

    count += prices.bathroom * bathroom_count;

    count += (prices.bathroom / 2) * halfBathroom_count;


    if (select_checkbox_green.checked) {
        count += prices.greenClean;
    }
    if (select_checkbox_deep.checked) {
        count = Math.round(count * prices.deepClean);
    }

    if (select_checkbox_microwave.checked) {
        count += prices.microwaveClean;
    }
    if (select_checkbox_refrigerator.checked) {
        count += prices.refrigeratorClean;
    }
    if (select_checkbox_oven.checked) {
        count += prices.ovenClean;
    }

    count += prices.window * windows_amount;

    count += prices.cabinet * cabinets_amount;


    if (select_checkbox_dishes.checked) {
        count += prices.dishesWash;
    }

    // price for house declare by choosing higher option
    count = count > prices.houseFt * $("#square-ft").val() ? count : prices.houseFt * $("#square-ft").val();

    //check if selected date is weekend
    count += weekendClean ? prices.weekend : 0;

    $('#totalPrice').text(count + "$").val(count);

    return count;
}
      