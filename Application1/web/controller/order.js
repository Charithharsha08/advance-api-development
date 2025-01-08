
$("#btnAddToCart").prop("disabled", true);

document.getElementById("btnOrder").addEventListener("click", function(){
    document.getElementById("customer").style.display="none";
    // document.getElementById("item").style.display="none";
    document.getElementById("item").style.display="none";
    document.getElementById("placeORder").style.display="block";
    document.getElementById("order").style.display="none";

});

$(document).ready(() => {
        $("#txtdate").val(new Date().toISOString().substring(0, 10));
        loadAllCustomers();
        loadAllItems();
    }
);

function getSelectedCustID() {

    // Get the selected option element
    const selectedOption = document.getElementById("custIds").selectedOptions[0];

    // Retrieve the value (Customer ID) and text (Customer Name) of the selected option
    const customerID = selectedOption.value;
    const customerName = selectedOption.getAttribute("data-name");

    // Set the customer name in the input field
    document.getElementById("custNameRst").value = customerName;

    console.log(`Selected Customer ID: ${customerID}, Name: ${customerName}`);
}

function loadAllCustomers() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/Application1_Web_exploded/customer",
        dataType: "json",
        success: function (data) {
            const custoers = $('#custIds');
            custoers.empty(); // Clear the existing options

            // Add a default option
            custoers.append('<option selected disabled hidden>Customer ID</option>');

            // Append customer options dynamically
            data.forEach((customer) => {
                // Use customer.id as the display text and include customer.name as a data attribute
                custoers.append(`<option value="${customer.id}" data-name="${customer.name}">${customer.id}</option>`);
            });
        }
    });
}
function loadAllItems() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/Application1_Web_exploded/item",
        dataType: "json",
        success: function (data) {

            const items = $('#itemIds');
            items.empty(); // Clear the existing options

            // Add a default option
            items.append('<option selected disabled hidden>Item ID</option>');

            // Append customer options dynamically
            data.forEach((item) => {
               console.log(item.unit) ;
                // Use customer.id as the display text and include customer.name as a data attribute
                items.append(`<option value="${item.code}" item-description="${item.description}" item-qty="${item.qty}" item-price="${item.unit}">${item.code}</option>`);
                console.log()
            });
        }
    });
}
function getSelectedItemID() {

    // Get the selected option element
    const selectedOption = document.getElementById("itemIds").selectedOptions[0];

    // Retrieve the value (Customer ID) and text (Customer Name) of the selected option
    const itemID = selectedOption.value;
    const itemDescription = selectedOption.getAttribute("item-description");
    const itemPrice = selectedOption.getAttribute("item-price");
    const itemQty = selectedOption.getAttribute("item-qty");

    // Set the customer name in the input field
    document.getElementById("custItemRst").value = itemDescription;
    document.getElementById("txtPriceRst").value = itemPrice;
    document.getElementById("txtQytRst").value = itemQty;
    console.log(`Selected Item ID: ${itemID}, Name: ${itemDescription}, Price: ${itemPrice}, Qty: ${itemQty}`);

    $("#btnAddToCart").prop("disabled", false);
    }

$("#btnAddToCart").click(function () {
    if (document.getElementById("custIds").value == ""  ) {
        alert("Please select a customer");
        return;
    }
    if (document.getElementById("txtQtyOd").value == ""  ) {
        alert("Qty is Empty Please enter again");
        return;
    }/*if (document.getElementById("txtQtyOd").value > document.getElementById("txtQytRst").value  ) {
        alert("Qty exceeds the stock Please enter again");
        return;
    }*/
    else {
        const customerName = document.getElementById("custNameRst").value;
        const code = document.getElementById("itemIds").value;
        const itemDescription = document.getElementById("custItemRst").value;
        const itemPrice = document.getElementById("txtPriceRst").value;
        const itemQty = document.getElementById("txtQtyOd").value;
        const itemTotal = itemPrice * itemQty;



        const item = {
            "code": code,
            "description": itemDescription,
            "price": itemPrice,
            "qty": itemQty,
            "total": itemTotal
        };
        // Add the item to the cart
        // cart.push(item);

        // Update the cart table
        const cartTable = document.getElementById("tblPlaceOrderBody");
        const newRow = document.createElement("tr");
        newRow.innerHTML = `
    <td>${item.code}</td>
    <td>${itemDescription}</td>
    <td>${itemQty}</td>
    <td>${itemPrice}</td>
    <td>${itemTotal}</td>   
    <td><button class="btn btn-danger" onclick="removeItem(this)">Remove</button></td>
    `;
        cartTable.appendChild(newRow);
        // Clear the input fields
        clearFields();
        calculateTotal();
    }
});

function removeItem(button) {
    const row = button.parentNode.parentNode;
    row.remove();
    calculateTotal();
}

function clearFields() {

    loadAllItems();
    document.getElementById("custItemRst").value = "";
    document.getElementById("txtPriceRst").value = "";
    document.getElementById("txtQytRst").value = "";
    document.getElementById("txtQtyOd").value = "";
    $("#btnAddToCart").prop("disabled", true);
}

function calculateTotal() {

    let tot = 0;
    const cartTable = document.getElementById("tblPlaceOrderBody");
    const rows = cartTable.getElementsByTagName("tr");
    for (let i = 0; i < rows.length; i++) {
        const qty = rows[i].getElementsByTagName("td")[2].textContent;
        const price = rows[i].getElementsByTagName("td")[3].textContent;
        console.log("qty" + qty + "price" + price);
        const total = qty * price;
        console.log("total" + total);
        tot += total;
        console.log("tot" + tot);
    }
    console.log(tot);
    document.getElementById("txtTot").value = tot;
}

$("#txtDiscount").on('input', function () {
    const discount = $("#txtDiscount").val();
    const total = $("#txtTot").val();
    const subTotal = total - discount;
    if (discount > 0) {
        $("#txtSubTot").val(subTotal);
    }
    else {
        $("#txtSubTot").val(total);
    }
});

$("#btnPurchase").click(() => {
    const orderId = $("#txtOrderID").val();
    const customerId = $("#custIds").val();
    const orderDate = $("#txtdate").val();
    const discount = $("#txtDiscount").val();
    const subTotal = $("#txtSubTot").val();
    const total = $("#txtTot").val();

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/Application1_Web_exploded/order",
        data: {
            orderId: orderId,
            customerId: customerId,
            orderDate: orderDate,
            discount: discount,
            subTotal: subTotal,
            total: total
        },
        success: function (response) {
            alert("Order placed successfully!");
            clearFields();
        },
        error: function (xhr, status, error) {
            console.log("Error: " + error);
        }
    });
    
   /* $.ajax({
        type: "PUT",
        url: "http://localhost:8080/Application1_Web_exploded/item",
        success: function (response) {
            alert("Stock updated successfully!");
            clearFields();
        },
        error: function (xhr, status, error) {
            console.log("Error: " + error);
        }
    })*/

});