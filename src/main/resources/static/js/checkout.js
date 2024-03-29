const stripe = getPublicKey() ? Stripe(getPublicKey()) : null;

function getPublicKey() {
  let result;
  $.ajax({
    type: "GET",
    url: "/public_key",
    dataType: "text",
    contentType: "application/json; charset=utf-8",
    async: false,
    success: function (response) {
      result = response;
    },
    error: function (response) {
      alert("Error getting stripe api key : ");
    }
  });
  return result;
}


const hello = "hellow world";
// This is your test publishable API key.
// The items the customer wants to buy
const items = [{id: "xl-tshirt"}];
let elements;
let payment_secret;
$(function () {
  document
      .querySelector("#payment-form")
      .addEventListener("submit", handleSubmit);

  $("#email").change(reInitializePayment);

});


let emailAddress = '';

function reInitializePayment() {
  let payment = getPayment();
  initialize(payment);
}

function getPayment() {
  let payment = {
    email: $("#email").val(),
    amount: estimatedPrice
  };
  return payment;
}

function initPayment() {
  if (stripe !== null) {
    $(".payment-body, #book-cleaning-button, #payment-init").toggleClass('hidden');
    $("#payment-cancel-button").toggle();
    let payment = getPayment();
    emailAddress = payment.email;
    initialize(payment);
    checkStatus();
    onlinePaymentBolean = true;
  } else {
    errorPopUp("Stripe public key is not defined");
  }

}

function cancelPayment() {
  $(".payment-body, #book-cleaning-button, #payment-init").toggleClass('hidden');
  $("#payment-cancel-button").toggle();
  onlinePaymentBolean = false;

}

// Fetches a payment intent and captures the client secret
async function initialize(payment) {
  const response = await fetch("/create-payment-intent", {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify(payment),
  });
  const {clientSecret} = await response.json();

  const appearance = {
    theme: 'stripe',
  };
  console.log(stripe);

  elements = stripe.elements({appearance, clientSecret});
  payment_secret = clientSecret.split('_secret_')[0];
  const paymentElementOptions = {
    layout: "tabs",
  };

  const paymentElement = elements.create("payment", paymentElementOptions);
  paymentElement.mount("#payment-element");
}

async function handleSubmit(e) {
  e.preventDefault();


  if (document.querySelector('#estimate').checkValidity()) {

    if (!dateValidRequired()) {
      dateEmptyPopup();
      return;
    }

    setLoading(true);

    var estimateData = getEstimateData();
    estimateData.paymentSecret = payment_secret;
    $.ajax({
      type: "POST",
      contentType: "application/json",
      url: "/estimate",
      data: JSON.stringify(estimateData),
      dataType: 'json',
      cache: false,
      timeout: 600000,

      success: async function (order) {

        const {error} = await stripe.confirmPayment({
          elements,
          redirect: "if_required",
          confirmParams: {
            // Make sure to change this to your payment completion page
            return_url: "http://stripe.com",
            receipt_email: emailAddress,
          },

        });
        // This point will only be reached if there is an immediate error when
        // confirming the payment. Otherwise, your customer will be redirected to
        // your `return_url`. For some payment methods like iDEAL, your customer will
        // be redirected to an intermediate site first to authorize the payment, then
        // redirected to the `return_url`.
        if (error !== undefined) {

          if (error.type === "card_error" || error.type === "validation_error") {
            showMessage(error.message, "alert alert-danger");
          } else {
            showMessage("An unexpected error occurred.", "alert alert-danger");
          }
          cancelOrder(order.paymentSecret);


        } else {
          showMessage("Payment succeeded!", "alert alert-success");
          successPopUp('Succeeded!', 'Thank you for submitting your order. We will review it and be in touch with you shortly.'
              + 'Once the processing is complete, we will send you a receipt via email. Have a great day!!');
          // close modal and clear form and date field
          closeModal();
          $("#estimate").trigger("reset");
          updateDisableDates();
          fp.clear();
        }


        setLoading(false);
      },
      error: function (e) {
        setLoading(false);

        showMessage(e.responseJSON.detail, "alert alert-danger");
      }
    });

  } else {
    document.querySelector('#estimate').reportValidity();
  }

}

// Fetches the payment intent status after payment submission
async function checkStatus() {
  const clientSecret = new URLSearchParams(window.location.search).get(
      "payment_intent_client_secret"
  );

  if (!clientSecret) {
    return;
  }

  const {paymentIntent} = await stripe.retrievePaymentIntent(clientSecret);

  switch (paymentIntent.status) {
    case "succeeded":
      showMessage("Payment succeeded!", "alert alert-success");
      break;
    case "processing":
      showMessage("Your payment is processing.", "alert alert-warning");
      break;
    case "requires_payment_method":
      showMessage("Your payment was not successful, please try again.", "alert alert-danger");
      break;
    default:
      showMessage("Something went wrong.", "alert alert-danger");
      break;
  }
}

// ------- UI helpers -------

function showMessage(messageText, alertClass) {
  const messageContainer = document.querySelector("#payment-message");
  $('#payment-message').addClass(alertClass);
  messageContainer.classList.remove("hidden");
  messageContainer.textContent = messageText;

  setTimeout(function () {
    messageContainer.setAttribute("class", "hidden");
    messageText.textContent = "";
  }, 40000);
}

// Show a spinner on payment submission
function setLoading(isLoading) {
  if (isLoading) {
    // Disable the button and show a spinner
    document.querySelector("#submit-payment").disabled = true;
    document.querySelector("#spinner").classList.remove("hidden");
    document.querySelector("#button-text").classList.add("hidden");
  } else {
    document.querySelector("#submit-payment").disabled = false;
    document.querySelector("#spinner").classList.add("hidden");
    document.querySelector("#button-text").classList.remove("hidden");
  }
}

function cancelOrder(id) {
  $.ajax({
    type: "DELETE",
    url: "/estimate/cancel",
    data: {
      id: id
    },

    success: function (result) {
      console.log("order canceled");
    },
    error: function (e) {
      console.log(e);
    }
  })
}