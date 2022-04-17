const source = "http://localhost:8080/data/search";
function searchSpots()
{
  //clearing the previously loaded spots
  clearSpots();
  //getting the form object
  let form = document.forms[0];

  //getting the Make and Model and Picture from user input
  let carMake = form.elements["carMake"].value;
  let carModel = form.elements["carModel"].value;

  let params = new URLSearchParams({
    "carMake": carMake,
    "carModel": carModel
  });

  fetch(source + "?" + params).then(data => data.json())
      .then(data =>loadSpots(data));

}

searchPostArea = document.getElementById("postArea");

let search = document.getElementById("spotSearch");
search.addEventListener("click", ()=>{
  searchSpots()});





