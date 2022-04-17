function loadSpot(apiSpot) {
    //Object destructuring
    let {carMake, carModel, appUserUsername, dateTime, picURL, spotId} = apiSpot;
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

    let idMark = document.createElement("p");
    idMark.innerHTML = `<small>SpotId: ${spotId}</small>`;
    spot.appendChild(idMark);

    postArea.appendChild(spot);
}

function loadSpots(apiResp) {
    for (let apiSpot of apiResp) {
        loadSpot(apiSpot);
    }
}

function clearSpots() {
    let spots = document.getElementsByClassName("spots");

    while(spots[0]) {
        spots[0].parentNode.removeChild(spots[0]);
    }
}