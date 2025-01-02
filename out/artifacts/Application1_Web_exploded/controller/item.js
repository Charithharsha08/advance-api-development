
let selectedRowItem;
let itemIndex;
let table;


$(document).ready(() =>{
   // fetchItemData()
});


document.getElementById("btnItems").addEventListener("click", function (){

    document.getElementById("customer").style.display="none";
    document.getElementById("placeORder").style.display="none";
    document.getElementById("item").style.display="block"
    document.getElementById("order").style.display="none";
    fetchItemData()
});

function fetchItemData() {
    $.ajax({
        type: "GET",
        url:"http://localhost:8080/Application1_Web_exploded/item",
        success: (response) => {
            console.log("response")
            let itemData = ""
            response.forEach((item) => {
                itemData += `<tr>
                        <td>${item.code}</td>
                        <td>${item.description}</td>
                        <td>${item.unit}</td>
                        <td>${item.qty}</td>
                    </tr>`
            })
            $('#itemBody').html(itemData)
        }
    })
}


