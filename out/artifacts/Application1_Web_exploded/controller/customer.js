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



