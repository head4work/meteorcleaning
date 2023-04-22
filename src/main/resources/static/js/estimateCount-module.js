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

    count += parseInt(house_value) !== 3 ? (Object.values(prices)[house_value]) : prices.office * square_ft_count;

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
    count = count > prices.houseFt * $("#square-ft").val() ? count : prices.houseFt * $("#square-ft").val();

    //check if selected date is weekend
    count += weekendClean ? prices.weekend : 0;

    $('#totalPrice').text(count + "$").val(count);


    $('#totalTime').text(time_convert(time.toFixed()) + " min");
    if (onlinePaymentBolean) {
        let payment = getPayment();
        initialize(payment);
    }
    return count;
}
