
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

$("#btnItemAdd").click(() => {
    console.log("clicked item add function called")
    const code = $('#txtItemID').val();
    const description = $('#txtItemName').val();
    const qtyOnHand = $('#txtQty').val();
    const unitPrice = $('#txtPrice').val();

    $.ajax({
        type: "POST",
        url:"http://localhost:8080/Application1_Web_exploded/item",
        data: {
            code: code,
            description: description,
            qtyOnHand: qtyOnHand,
            unitPrice: unitPrice
        },
        success: (response) => {
            console.log(response)
            alert("Item Added")
            //fetchItemData()
        }
    })
})

$("#btnItemDelete").click(() => {
    const code = $('#txtItemID').val();
    $.ajax({
        type: "DELETE",
        url:`http://localhost:8080/Application1_Web_exploded/item?code=${code}`,
        success: (response) => {
            console.log(response)
            alert("Item Deleted")
            //fetchItemData()
        }
    })
})
$("#btnItemUpdate").click(() => {
    const code = $('#txtItemID').val();
    const description = $('#txtItemName').val();``
    const qtyOnHand = $('#txtQty').val();
    const unitPrice = $('#txtPrice').val();

    $.ajax({
        type: "PUT",
        url:`http://localhost:8080/Application1_Web_exploded/item?code=${code}&description=${description}&qtyOnHand=${qtyOnHand}&unitPrice=${unitPrice}`,
        success: (response) => {
            console.log(response)
            alert("Item Updated")
            //fetchItemData()
        }
    })
})

