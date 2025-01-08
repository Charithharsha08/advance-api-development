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





const fetchStudentData =()=>{
    $.ajax({
        type: "GET",
        url:"http://localhost:8080/Application1_Web_exploded/customer",
        success: (response) => {
            console.log("response")
            let customerData = ""
            response.forEach((customer) => {
                customerData += `<tr>
                        <td>${customer.id}</td>
                        <td>${customer.name}</td>
                        <td>${customer.address}</td>
                    </tr>`
            })
            $('#tableCustomerBody').html(customerData)
        }
    })
}

$("#btnCustSave111").click((e) => {
    e.preventDefault()
    console.log("clicked save function called")
    const id = $('#CustID').val();
    const name = $('#CustName').val();
    const address = $('#CustAddress').val();
    const contact = $('#CustContact').val();

    $.ajax({
        type: "POST",
        url:"http://localhost:8080/Application1_Web_exploded/customer",
        data: {
            id: id,
            name: name,
            address: address
        },
        success: (response) => {
            // console.log(response)
            alert("Customer Saved")
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
            url: `http://localhost:8080/Application1_Web_exploded/customer?id=${id}`,
            success: (response) => {
                console.log(response)
                alert("Customer Deleted")
                fetchStudentData()
            }
        })
    }else{
        return;
    }
})

$("#btnUpdateCust").click((e) => {
    e.preventDefault()
    const id = $('#CustID').val();
    const name = $('#CustName').val();
    const address = $('#CustAddress').val();
    const contact = $('#CustContact').val();

    $.ajax({
        type: "PUT",
        url:`http://localhost:8080/Application1_Web_exploded/customer?id=${id}&name=${name}&address=${address}`,
        success: (response) => {
            console.log(response)
            alert("Customer Updated")
            fetchStudentData()
        }
    })
})



