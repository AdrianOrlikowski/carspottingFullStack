//Source URL where API is hosted
const source = "http://localhost:8080/data/spots";


function loadSpot(apiSpot) {
  //Object destructuring
  let {carMake, carModel, appUserUsername, dateTime, picURL} = apiSpot;
  //Creating post
  let spot = document.createElement("div");
  spot.classList.add("spots");

    let carMakeModel = document.createElement("p");
    carMakeModel.innerText = carMake + " " + carModel;
    spot.appendChild(carMakeModel);

    dateString = new Date(dateTime).toLocaleString();
    let postedBy = document.createElement("p");
    postedBy.innerHTML =`<small>Posted by: ${appUserUsername} on ${dateString}</small>`;
    spot.appendChild(postedBy);

    let line = document.createElement("hr");
    spot.appendChild(line);

    let carPic = document.createElement("p");
    carPic.innerHTML = `<img src="${picURL}" alt="Failed to load picture">`;
    spot.appendChild(carPic);

  postArea.appendChild(spot);
}

function loadSpots(apiResp) {
  for (let apiSpot of apiResp) {
    loadSpot(apiSpot);
  }
}

postArea = document.getElementById("postArea");
siteEnd = document.getElementById("siteEnd");

fetch(source).then(data => data.json())
  .then(data => loadSpots(data));









