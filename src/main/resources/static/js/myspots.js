//Source URL where API is hosted
const source = "http://localhost:8080/data/myspots";

//Function bundles loadSpots and addDeleteButtons so both of these are run where
//data is returned from fetch request
function prepare(data) {
    loadSpots(data);
    addDeleteButtons();
}

postArea = document.getElementById("postArea");
siteEnd = document.getElementById("siteEnd");

fetch(source).then(data => data.json())
  .then(data => prepare(data));










