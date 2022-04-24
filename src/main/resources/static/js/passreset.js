sourcePassReset = "http://localhost:8080/users/resetpassword";
sourceResendToken = "http://localhost:8080/users/resendtoken";
function sendRequest(source)
{
    //getting the email from for
    let email = document.forms[0].elements["email"].value;

    //Preparing the form to be sent
    let XHR = new XMLHttpRequest();
    let FD = new FormData();
    FD.append("email", email);

    //Handling response
    XHR.addEventListener("load",function(event){
        if(XHR.status == 200) {
            alert("Request sent successfully");
        } else {
            let resp = JSON.parse(XHR.responseText);
            alert(resp.message);
        }
    });
    //Sending the request
    XHR.open("POST",source);
    XHR.send(FD);
    //go to main page
    //window.location.href = "http://localhost:8080"
}

//Adding listener to Reset Password button
let resetPassword = document.getElementById("resetPassword");
resetPassword.addEventListener("click", ()=>{
    sendRequest(sourcePassReset);
});
//Adding listener to Resend Token button
let resendToken = document.getElementById("resendToken");
resendToken.addEventListener("click", ()=>{
    sendRequest(sourceResendToken);
});
