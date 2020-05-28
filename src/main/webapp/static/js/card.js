let popup = $("#popup");
let currentCardId;
let currentCard;
let membersArr = [];

function openCard(card) {
    membersHint.empty();
    comments.empty();
    commentText.val("");
    files.empty();
    membersArr = [];
    findMembers();
    currentCard = card;
    currentCardId = card.dataset.cardId;
    ajax("/api/cards/" + currentCardId, "GET", {}, function (data) {
        setData(data);
    });
}

function closeCard() {
    popup.hide();
}

let title = $("#title");
let comments = $("#comments");

function setData(data) {
    console.log(data);
    title.text(() => data.title);
    description.val(data.description);
    if (data.deadline !== null) {
        deadline.val(data.deadline.substring(0, 10));
    }

    for (let i = 0; i < data.files.length; i++) {
        files
            .append("<div><a href='http://localhost:8080/api/files?file=" + data.files[i].file + "'>File: " + data.files[i].file + "</a></div>");
    }

    for (let i = 0; i < membersArr.length; i++) {
        membersHint.append("<div class='member-id'><div>ID: " + membersArr[i].id + ", " + membersArr[i].name + "</div></div>");
    }

    for (let i = 0; i < data.comments.length; i++) {
        let comment = resolveMentions(data.comments[i]);
        let html =
            "<div class='light-comment-wrapper'>" +
            "<div class='light-comment'>" +
            "    <div class='name'>" + comment.name + "</div>" + comment.message + "</div>" +
            "</div>";
        comments.append(html);
    }
    popup.show();
}

let deadline = $("#deadline");
let description = $("#description");
description.bind('input propertychange', function () {
    ajax("/api/cards/" + currentCardId + "/description", "PUT", {
        description: description.val()
    });
});

deadline.bind('input propertychange', function () {
    ajax("/api/cards/" + currentCardId + "/deadline", "PUT", {
        deadline: deadline.val()
    });
});

let commentText = $("#comment-text");
let sendComment = $("#send-comment");
let membersHint = $("#members-hint");
commentText.bind('input propertychange', function () {
    if (commentText.val().slice(-1) == "@") {
        membersHint.show();
    } else {
        membersHint.hide();
    }
});

sendComment.on("click", function () {
    console.log(commentText.val().trim().length);
    if (commentText.val().trim().length !== 0) {
        ajax("/api/cards/" + currentCardId + "/comment", "POST", {
            comment: commentText.val().trim()
        }, function success() {
            let comment = resolveMentions({
                userId: document.getElementById("body").dataset.userId,
                message: commentText.val().trim()
            });
            let html =
                "<div class='light-comment-wrapper'>" +
                "<div class='light-comment'>" +
                "    <div class='name'>" + comment.name + "</div>" + comment.message + "</div>" +
                "</div>";
            comments.append(html);
            commentText.val("");
        });
    }
});

function findMembers() {
    let list = document.getElementById("members-list");
    let memberNames = list.getElementsByClassName("name");
    let ids = list.getElementsByClassName("fa-times");
    for (let i = 0; i < memberNames.length; i++) {
        membersArr.push({
            name: memberNames[i].innerHTML,
            id: ids[i].dataset.memberId
        })
    }
}

function resolveMentions(text) {
    for (let i = 0; i < membersArr.length; i++) {
        if (text.userId == membersArr[i].id) {
            text.name = membersArr[i].name;
        }
        text.message = text.message.replace("@id" + membersArr[i].id, "<span class='mention'>" + membersArr[i].name + "</span>");
    }
    return text;
}

let file = $("#file");
let upload = $("#upload");
upload.on("click", function () {
    file.trigger("click");
});

let files = $("#files-list");
file.on("change", function () {
    let formdata = new FormData();
    formdata.append("file", file.prop("files")[0]);
    formdata.append("card_id", currentCardId);
    $.ajax({
        url: '/api/files',
        type: 'POST',
        headers: {"X-CSRF-TOKEN": token},
        data: formdata,
        contentType: false,
        cache: false,
        processData: false,
        success: function (response) {
            files.append("<div><a href='http://localhost:8080/api/files?file=" + response + "'>File: " + response + "</a></div>");
        }
    });
});