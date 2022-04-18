function functions(apiSpot) {
    //Object destructuring
    let {carMake, carModel, appUserUsername, dateTime, picURL, spotId} = apiSpot;
    //Creating post
    let spot = document.createElement("div");
    spot.classList.add("spots");

    let carMakeModel = document.createElement("p");
    carMakeModel.innerText = carMake + " " + carModel;
    spot.appendChild(carMakeModel);

    dateString = new Date(dateTime).toLocaleString();
    let spottedBy = document.createElement("p");
    spottedBy.innerHTML =`<small>Spotted by: ${appUserUsername} on ${dateString}</small>`;
    spot.appendChild(spottedBy);
    
    let line = document.createElement("hr");
    spot.appendChild(line);

    let carPic = document.createElement("p");
    carPic.innerHTML = `<img src="${picURL}" alt="Failed to load picture">`;
    spot.appendChild(carPic);

    let idMark = document.createElement("p");
    idMark.innerHTML = `<small class="spotId">${spotId}</small>`;
    spot.appendChild(idMark);

    postArea.appendChild(spot);
}

function loadSpots(apiResp) {
    for (let apiSpot of apiResp) {
        functions(apiSpot);
    }
}

function clearSpots() {
    let spots = document.getElementsByClassName("spots");

    while(spots[0]) {
        spots[0].parentNode.removeChild(spots[0]);
    }
}

function addDeleteButtons() {
    let spots = document.getElementsByClassName("spots");

    let i = 0;
    for(spot of spots) {
        let spotId = spot.querySelector(".spotId").innerText;
        let deleteButton = document.createElement("div");
        deleteButton.innerHTML = `<button id="${spotId}" onClick ="deleteSpot(this.id)" >Delete Spot</button>`;
        spot.appendChild(deleteButton);
        i++;
    }
}

function deleteSpot(id) {
    let XHR = new XMLHttpRequest();

    XHR.addEventListener("load",function(event) {
        if (XHR.status == 200) {
            alert("Spot deleted successfully");
        } else {
            let resp = JSON.parse(XHR.responseText);
            alert(resp.message);
        }
    });

    XHR.open("DELETE", source + "/" + id);
    XHR.send();
    //refresh page
    window.location.href = "http://localhost:8080/myspots"
}