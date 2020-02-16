function createLottery(e) {
    const title = document.getElementById('title').value;
    const limit = document.getElementById('limit').value;
    e.preventDefault();
    fetch('/start-registration', {
        method: 'POST',
        body: JSON.stringify({
            title: title,
            limit: limit
        }),
        headers: {'Content-Type': 'application/json'}
    }).then((responseValue) => responseValue.json()
    ).then(response => {
        if (response.status === 'OK') {
            alert("Lottery successfully created.");
            location.reload();
        } else {
            alert(response.reason);
        }
    });

}

function stopLottery(e, id) {
    e.preventDefault();
    fetch('/stop-registration', {
        method: 'POST',
        body: JSON.stringify({
            id: id
        }),
        headers: {'Content-Type': 'application/json'}
    })
        .then((responseValue) => responseValue.json()
        ).then(response => {
        console.log(response.status);
        if (response.status === "OK") {
            alert("Lottery with id " + id + " stopped.");
            location.reload();
        } else {
            alert(response.reason);
        }
    });
}

function chooseWinner(e, id) {
    e.preventDefault();
    fetch('/choose-winner', {
        method: 'POST',
        body: JSON.stringify({
            id: id
        }),
        headers: {'Content-Type': 'application/json'}
    }).then((responseValue) => responseValue.json()
    ).then(response => {
        if (response.status === 'OK') {
            alert("Winner code is " + response.winnerCode);
            location.reload();
        } else {
            alert(response.reason);
        }
    });
}

function checkStatus(e) {
    const lotteryId = document.getElementById('lotteryId').value;
    const email = document.getElementById('email').value;
    const uniqueCode = document.getElementById('uniqueCode').value;
    e.preventDefault();
    fetch('/status?id=' + lotteryId + '&email=' + email + '&code=' + uniqueCode, {
        method: 'GET'
    }).then((responseValue) => responseValue.json()
    ).then(response => {
        if (response.status === "FAIL") {
            alert("Wrong request data passed, please check your code/email/lottery id")
            location.reload();
        } else {
            alert(response.status);
            location.reload();
        }
    });
}

function registerParticipant(e) {
    const email = document.getElementById('email').value;
    const age = document.getElementById('age').value;
    const uniqueCode = document.getElementById('uniqueCode').value;
    const lotteryId = document.getElementById('lotteryId').value;
    e.preventDefault();
    fetch('/register', {
        method: 'POST',
        body: JSON.stringify({
            id: lotteryId,
            email: email,
            age: age,
            code: uniqueCode
        }),
        headers: {"Content-Type": "application/json"}
    }).then((responseValue) => responseValue.json()
    ).then(response => {
        if (response.status === 'OK') {
            alert("Participant successfully registered.");
            location.reload();
        } else {
            alert(response.reason);
        }
    });
}

$.ajax({
    url: 'http://localhost:8080/stats',
    type: "get",
    dataType: "json",

    success: function (data) {
        drawTable(data);
    }
});

function drawTable(data) {
    for (var element in data) {
        drawRow(data[element]);
    }
}

function drawRow(rowData) {
    let lotteryId = rowData.id;
    let endDateReplacement = "Running...";
    if (!rowData.endDate) {
        rowData.endDate = endDateReplacement;
    }
    let row = $("<tr>");
    $("#statisticsData").append(row);
    row.append($("<td>" + lotteryId + "</td>"));
    row.append($("<td>" + rowData.status + "</td>"));
    row.append($("<td>" + rowData.title + "</td>"));
    row.append($("<td>" + rowData.startDate + "</td>"));
    row.append($("<td>" + rowData.endDate + "</td>"));
    row.append($("<td>" + rowData.participants + "</td>"));
    row.append($("<td><button id='lotteryStopButton" + lotteryId + "' type='button' onclick='stopLottery(event, " + lotteryId + ")'>Stop</button></td>"));
    row.append($("<td><button id='chooseWinnerButton" + lotteryId + "' type='button' onclick='chooseWinner(event, " + lotteryId + ")'>Select winner</button></td>"));
    row.append($("</tr>"));
    let stopButton = document.getElementById("lotteryStopButton" + lotteryId);
    if (rowData.status === "CLOSED") {
        stopButton.disabled = true;
    }
    let winButton = document.getElementById("chooseWinnerButton" + lotteryId);
    if (rowData.endDate !== endDateReplacement) {
        winButton.disabled = true;
    }

}
