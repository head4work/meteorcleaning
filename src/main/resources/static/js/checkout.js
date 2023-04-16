// This is your test publishable API key.
const stripe = Stripe("pk_test_51MuNeYCxo90t77m1bobhYnDuJBAYbSbLcCoUwGVWkWuLvvMDwX49m3LCGAdgEu5M19RRYDSKA5XOuvYdWsU2N1iu00enztfEX5");

// The items the customer wants to buy
const items = [{id: "xl-tshirt"}];

let elements;


document
    .querySelector("#payment-form")
    .addEventListener("submit", handleSubmit);

let emailAddress = '';

function getPayment() {
  let payment = {
    email: $("#email").val(),
    amount: parseInt($("#totalPrice").text().slice(0, -1))
  };
  return payment;
}

function initPayment() {
  $(".payment-body, #book-cleaning-button, #payment-init").toggleClass('hidden');
  $("#payment-cancel-button").toggle();
  let payment = getPayment();
  emailAddress = payment.email;
  initialize(payment);
  checkStatus();

  onlinePaymentBolean = true;
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
  elements = stripe.elements({appearance, clientSecret});

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
    $.ajax({
      type: "POST",
      contentType: "application/json",
      url: "/estimate",
      data: JSON.stringify(estimateData),
      dataType: 'json',
      cache: false,
      timeout: 600000,

      success: async function (data) {

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
            showMessage(error.message);
          } else {
            showMessage("An unexpected error occurred.");
          }
        } else {
          showMessage("Payment succeeded!");
        }

        // clear form and date field
        $("#estimate").trigger("reset");
        updateDisableDates();
        fp.clear();
        setLoading(false);
      },
      error: function (e) {
        setLoading(false);

        showMessage(e.responseJSON.detail);
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
      showMessage("Payment succeeded!");
      break;
    case "processing":
      showMessage("Your payment is processing.");
      break;
    case "requires_payment_method":
      showMessage("Your payment was not successful, please try again.");
      break;
    default:
      showMessage("Something went wrong.");
      break;
  }
}

// ------- UI helpers -------

function showMessage(messageText) {
  const messageContainer = document.querySelector("#payment-message");

  messageContainer.classList.remove("hidden");
  messageContainer.textContent = messageText;

  setTimeout(function () {
    messageContainer.classList.add("hidden");
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