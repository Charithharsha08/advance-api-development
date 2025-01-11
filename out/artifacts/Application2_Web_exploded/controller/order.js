
$("#btnAddToCart").prop("disabled", true);

document.getElementById("btnOrder").addEventListener("click", function(){
    document.getElementById("customer").style.display="none";
    // document.getElementById("item").style.display="none";
    document.getElementById("item").style.display="none";
    document.getElementById("placeORder").style.display="block";
    document.getElementById("order").style.display="none";

});
