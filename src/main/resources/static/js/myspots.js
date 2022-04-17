//Source URL where API is hosted
const source = "http://localhost:8080/data/myspots";

postArea = document.getElementById("postArea");
siteEnd = document.getElementById("siteEnd");

fetch(source).then(data => data.json())
  .then(data => loadSpots(data));









