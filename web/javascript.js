var xhr = false;
if (window.XMLHttpRequest) {
	xhr = new XMLHttpRequest();
} else if (window.ActiveXObject) {
	xhr = new ActiveXObject("Microsoft.XMLHTTP");
}

function loadUsers() {
    if (xhr.readyState === 4 && xhr.status === 200) { 
        var serverResponse = xhr.responseXML;
        if (serverResponse !== null) {
            var users = serverResponse.getElementsByTagName("user");
            var table = document.getElementById("tblBody");
            table.innerHTML = "";
            for (i = 0; i < users.length; i++) {
                addRow(users[i]);
            }
            var edit = document.getElementById("edit");
            edit.setAttribute("style", "visibility: hidden");
        }
    }
}

function requestUsers() {
    xhr.open("GET", "/Assignment4Server/webresources/users", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader("Content-length", 0);
    xhr.setRequestHeader("Connection", "close");
    xhr.onreadystatechange = loadUsers;
    xhr.send();
}

function createCol(text) {
    var c = document.createElement("td");
    var v = document.createTextNode(text);
    c.appendChild(v);
    return c;
}

function addRow(xml) {
    var table = document.getElementById("tblBody");
    var newRow = document.createElement("tr");
    for (var node = xml.firstChild; node !== null; node = node.nextSibling) {
        if (window.ActiveXObject) {
            newRow.appendChild(createCol(node.text));
        } else {
            newRow.appendChild(createCol(node.textContent));	
        }
    };
    newRow.onclick = select;
    table.appendChild(newRow);
}

function select() {
    unselectAll(this.parentNode);
    this.setAttribute("class", "success");
    var points = document.getElementById("points");
    points.value = this.firstChild.nextSibling.nextSibling.innerHTML;
    var edit = document.getElementById("edit");
    edit.setAttribute("style", "visibility: visible");
}

function unselectAll(parent) {
    for (var node = parent.firstChild; node !== null; node = node.nextSibling){
        node.className = "";
    }
}

function updatePoints() {
    var points = document.getElementById("points").value;
    if (points === "" || isNaN(points)) {
        alert("Please enter a number into the field");
    } else if (points < 0){
        alert("Please enter a non negative number into the field");
    } else {
        var tBody = document.getElementById("tblBody");
	for (var node = tBody.firstChild; node !== null; node = node.nextSibling) {
            if (node.className === "success") {
                var fname = encodeURIComponent(node.firstChild.innerHTML).toLowerCase();
                var lname = encodeURIComponent(node.firstChild.nextSibling.innerHTML).toLowerCase();
                var url = "/Assignment4Server/webresources/users/" + fname + "/" + lname + "/points/" + points;
                xhr.open("POST", url, false);
                xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhr.setRequestHeader("Content-length", 0);
                xhr.setRequestHeader("Connection", "close");
                xhr.send();
                requestUsers();
                document.getElementById("points").value = "";
            }
	}
    }
}

function addUser() {
    var fname = document.getElementById("fname").value;
    var lname = document.getElementById("lname").value;
    if (fname.length < 3 || lname.length < 3) {
        alert("Please fill in both name fields");
    } else {
        fname = encodeURI(fname);
        lname = encodeURI(lname);
        xhr.open("POST", "/Assignment4Server/webresources/users/" + fname + "/" + lname, true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.setRequestHeader("Content-length", 0);
        xhr.setRequestHeader("Connection", "close");
        xhr.onreadystatechange = addUserResponse;
        xhr.send();
    }
}

function addUserResponse() {
    if (xhr.readyState === 4 && xhr.status === 200) { 
        document.getElementById("fname").value = "";
        document.getElementById("lname").value = "";
        document.getElementById("response").innerHTML = xhr.responseText;
    }
}