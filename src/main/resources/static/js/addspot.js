const source = "http://localhost:8080/data/addspot";
function sendData()
{
  //getting the form object
  form = document.forms[0];

  //getting the Make and Model and Picture from user input
  let carMake = form.elements["carMake"].value;
  let carModel = form.elements["carModel"].value;
  let carPicFile = form.elements["carPicFile"].files[0];
  //console.log(carMake);
  //console.log(carModel);

  //Preparing the form to be sent
  let XHR = new XMLHttpRequest();
  let FD = new FormData();
  FD.append("carMake", carMake);
  FD.append("carModel",carModel);
  FD.append("carPicFile", carPicFile);


  //Handling response
  XHR.addEventListener("load",function(event){
    if(XHR.status == 200) {
      alert("Spot successfully added");
    } else {
      let resp = JSON.parse(XHR.responseText);
      alert(resp);
    }
    //go to main page
    window.location.href = "http://localhost:8080"

  })

  //Sending the request
  XHR.open("POST",source);
  XHR.setRequestHeader("Authorization", "Basic " + btoa("adrian:adrian")); //testing basic http security
  XHR.send(FD);

}

let submit = document.getElementById("spotSubmit");
submit.addEventListener("click", ()=>{
  sendData();
});





