var tot;

$("#btnAddToCart").prop("disabled", true);

document.getElementById("btnOrder").addEventListener("click", function(){
    document.getElementById("customer").style.display="none";
    // document.getElementById("item").style.display="none";
    document.getElementById("item").style.display="none";
    document.getElementById("placeORder").style.display="block";
    document.getElementById("order").style.display="none";
    loadAllCustomers()
});

function loadAllCustomers () {
    $.ajax({
        url: "http://localhost:8080/customer",
        type: "GET",
        success: function (response) {
            const custoers = $('#custIds');


            response.forEach((customer) => {
                <option value="${customer.id}"></option>
            });
        }
    });
}