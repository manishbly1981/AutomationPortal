function loginMethod() {
  var param = {
    email: document.getElementById("username").value,
    password: document.getElementById("password").value
    };

  axios.post("/api/login", param, {
    headers: {
      "Content-Type": "application/json"
    }
  })
  .then(function (response) {
    console.log(email + " " +password);
    console.log(response);
    alert("Data submitted successfully!");
  })
  .catch(function (error) {
    console.log(error);
    alert("Error submitting data!");
  });
}
