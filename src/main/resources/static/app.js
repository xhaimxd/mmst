const headers = {}

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/mmst',
    connectHeaders: headers
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/user/topic/greetings', (greeting) => {
        showGreeting(JSON.parse(greeting.body).content);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.publish({
        destination: "/mmst/hello",
        body: JSON.stringify({'name': $("#name").val()})
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => {
        // $.ajax({
        //     type: "get",
        //     url: "/csrf",
        //     async: false,
        //     // dataType: 'json',
        //     error: function (data) {
        //         console.log("error")
        //         console.log(data)
        //     },
        //     success: function (data) {
        //         headers[data["headerName"]] = data["token"]
        //     }
        // })
        // $.ajax({
        //     type: "get",
        //     url: "/login",
        //     async: false,
        //     // dataType: 'json',
        //     error: function (data) {
        //         console.log("error")
        //         console.log(data)
        //     },
        //     success: function (data) {
        //         headers["Authorization"] = "Bearer " + data
        //     }
        // })
        connect()
    });
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendName());
});
