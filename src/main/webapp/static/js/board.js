var query = $("#query");
var search = $("#search");
var users = $("#users");

search.on("click", function () {
    if (query.val().length !== 0) {
        $.ajax({
            url: '/api/users?name=' + query.val(),
            headers: {"X-CSRF-TOKEN": token},
            type: 'GET',
            success: function (data) {
                users.empty();
                users.attr("class", "users shadow");
                for (var i = 0; i < data.length; i++) {
                    users.append(
                        "<div class=\"user\">" +
                        "<div class=\"name-small\" data-id=\"" + data[i].id + "\">" + data[i].name + "</div>" +
                        "</div>"
                    );
                }
            }
        });
    } else {
        query.val("");
        users.attr("class", "users shadow hidden");
        users.empty();
    }
});

var body = $("#body");
var token = body.data("csrf");
var boardId = body.data("id");
users.on("click", function (ev) {
    var element = ev.target;
    if (element.dataset.id !== undefined) {
        var userId = element.dataset.id;
        users.empty();
        query.val("");
        users.attr("class", "users shadow hidden");
        $.ajax({
            url: '/api/boards/' + boardId + "/users",
            headers: {"X-CSRF-TOKEN": token},
            type: 'POST',
            data: {
                user_id: userId
            }
        });
    }
});

var searchBox = $("#search-box");
var addUser = $("#add-member");
var toggle = false;
addUser.on("click", function () {
    if (!toggle) {
        searchBox.show();
    } else {
        searchBox.hide();
    }
    toggle = !toggle;
});

var send = $("#send");
var message = $("#message");
var messages = $("#messages");
send.on("click", function () {
    var text = message.val();
    $.ajax({
        url: '/api/messages',
        headers: {"X-CSRF-TOKEN": token},
        type: 'POST',
        data: {
            text: text,
            board_id: boardId
        },
        success: function () {
            message.val("");
            messages.append("<div class=\"blue-message\">" + text + "</div>");
        }
    });
});

var userId = parseInt(body.data("user"));
(function loadMessage() {
    $.ajax({
        url: '/api/messages?board_id=' + boardId,
        headers: {"X-CSRF-TOKEN": token},
        type: 'GET',
        success: function (data) {
            for (var i = data.length - 1; i >= 0; i--) {
                var html = "<div class=\"light-message\">" +
                    "<div class=\"name\">" + data[i].userDto.name + "</div>" + data[i].text +
                    "</div>";
                if (data[i].userDto.id === userId) {
                    html = "<div class=\"blue-message\">" + data[i].text + "</div>"
                }
                messages.append(html);
            }
            loadMessage();
        }
    });
})();