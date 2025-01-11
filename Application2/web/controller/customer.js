$(document).ready(() => {
    fetchStudentData();
});



document.getElementById("btnCustomer").addEventListener("click", function(){

    document.getElementById("customer").style.display="block";
    document.getElementById("order").style.display="none";
    document.getElementById("item").style.display="none";
    document.getElementById("placeORder").style.display="none";
    fetchStudentData()
});





const fetchStudentData =()=> {
    $.ajax({
        url: "http://localhost:8080/Application2_Web_exploded/customerJson",
        type: "GET",
        dataType: "json",
        success: function (res) {
            $("#tableCustomerBody").empty();
            res.data.forEach((customer) => {
                $("#tableCustomerBody").append(`
                    <tr>
                        <td>${customer.id}</td>
                        <td>${customer.name}</td>
                        <td>${customer.address}</td>
                    </tr>
                `);
            })
        }
    });
}

$("#btnCustSave111").click((e) => {
    e.preventDefault()

    $.ajax({
        type: "POST",
        url:"http://localhost:8080/Application2_Web_exploded/customerJson",
        data: JSON.stringify(
            {
                id: $('#CustID').val(),
                name: $('#CustName').val(),
                address: $('#CustAddress').val()
            }
        ),
        success: (response) => {
            // console.log(response)
            alert("Customer Saved")
            fetchStudentData()
        }

    })
})

$("#btnUpdateCust").click((e) => {
    e.preventDefault()

    $.ajax({
        type: "PUT",
        url:"http://localhost:8080/Application2_Web_exploded/customerJson",
        data: JSON.stringify(
            {
                id: $('#CustID').val(),
                name: $('#CustName').val(),
                address: $('#CustAddress').val()
            }
        ),
        success: (response) => {
            // console.log(response)
            alert("Customer updated")
            fetchStudentData()
        }

    })
})

$("#btnDelteCust").click((e) => {
    e.preventDefault()
    if (confirm("Are you sure you want to delete this customer?")) {
        const id = $('#CustID').val();
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/Application2_Web_exploded/customerJson",
          //  `http://localhost:8080/Application2_Web_exploded/customerJson?id=${id}`
            data: JSON.stringify(
                {
                    id: id
                }
            ),
            success: (response) => {
                console.log(response)
                alert("Customer Deleted")
                fetchStudentData()
            }
        })
    }
})