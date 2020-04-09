<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="/static/css/reset.css">
    <link rel="stylesheet" href="/static/css/base.css">
    <link rel="stylesheet" href="/static/css/board.css">
    <link rel="stylesheet" href="/static/fontawesome-free-5.13.0-web/css/all.min.css">

</head>
<body>
<div class="wrapper">
    <div class="header-wrapper">
        <div class="header-small">
            <div class="app-name">Task Manager</div>
            <div><a href="/logout" class="link"><i class="fas fa-sign-out-alt"></i> Logout</a></div>
        </div>
        <div class="header-chat">CHAT</div>
    </div>
    <div class="main">
        <div class="board">
            <div class="board-header shadow">
                <div class="board-title">Title</div>
                <div class="members hidden">
                    <div class="member">
                        <img src="../image/2.png" alt="">
                    </div>
                    <div class="member">
                        <img src="../image/3.png" alt="">
                    </div>
                    <div class="member">
                        <img src="../image/4.png" alt="">
                    </div>
                </div>
                <div class="add-user hidden">
                    <i class="fas fa-user-plus"></i>
                    Add user
                </div>
            </div>
            <div class="search-box hidden">
                <div class="search-wrapper shadow">
                    <input type="text" class="search" placeholder="Enter user email.."><i class="fas fa-search"></i>
                </div>
                <div class="result hidden">
                    <div class="users shadow">
                        <div class="user">
                            <div class="avatar-small">
                                <img src="../image/2.png" alt="">
                            </div>
                            <div class="name-small">User</div>
                        </div>
                        <div class="user">
                            <div class="avatar-small">
                                <img src="../image/4.png" alt="">
                            </div>
                            <div class="name-small">User</div>
                        </div>
                        <div class="user">
                            <div class="avatar-small">
                                <img src="../image/4.png" alt="">
                            </div>
                            <div class="name-small">User</div>
                        </div>
                        <div class="user">
                            <div class="avatar-small">
                                <img src="../image/2.png" alt="">
                            </div>
                            <div class="name-small">User</div>
                        </div>
                        <div class="user">
                            <div class="avatar-small">
                                <img src="../image/3.png" alt="">
                            </div>
                            <div class="name-small">User</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="cards">
                <div class="card shadow">
                    <div class="title">New card</div>
                </div>
                <div class="card shadow">
                    <div class="title">New card</div>
                </div>
                <div class="card shadow">
                    <div class="title">New card</div>
                </div>
                <div class="card shadow">
                    <div class="title">New card</div>
                </div>
                <div class="card shadow">
                    <div class="title">New card</div>
                </div>
                <div class="card shadow">
                    <div class="title">New card</div>
                </div>
                <div class="card shadow">
                    <div class="title">New card</div>
                </div>
                <div class="card shadow">
                    <div class="title">New card</div>
                </div>
            </div>
        </div>
        <div class="chat">
            <div class="messages">
                <div class="light-message-wrapper">
                    <div class="light-message">
                        <div class="name">User</div>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                    </div>
                </div>
                <div class="light-message-wrapper">
                    <div class="light-message">
                        <div class="name">User</div>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                    </div>
                </div>
                <div class="blue-message">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div>
                <div class="light-message-wrapper">
                    <div class="light-message">
                        <div class="name">User</div>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                    </div>
                </div>
                <div class="blue-message">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div>
                <div class="blue-message">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div>
                <div class="light-message-wrapper">
                    <div class="light-message">
                        <div class="name">User</div>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                    </div>
                </div>
            </div>
            <div class="message-box">
                <input type="text" class="text" placeholder="Message..">
                <div class="button"><i class="fas fa-paper-plane"></i></div>
            </div>
        </div>
    </div>
</div>
<div class="popup hidden">
    <div class="card-detailed">
        <div class="card-header">
            <div class="title-wrapper">
                <div class="title">
                    Title
                </div>
                <div class="edit">
                    <i class="fas fa-pen"></i>
                </div>
            </div>
            <div class="comments-header">
                Comments
            </div>
        </div>
        <div class="main">
            <div class="card-details-wrapper">
                <div class="card-details">
                    <div class="description-title">Description</div>
                    <div class="description">
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ac velit purus. Morbi et odio dui.
                        Aliquam erat volutpat. Pellentesque vel quam id nisl volutpat tincidunt bibendum nec ipsum.
                        Interdum et malesuada fames ac ante ipsum primis in faucibus. Nullam euismod blandit rhoncus.
                        Duis vehicula, enim vel ullamcorper pretium, neque augue porta magna, a finibus nisl felis in
                        orci. Donec at pulvinar justo.
                    </div>
                </div>
                <div class="tools">
                    <div class="tool"><i class="far fa-clock"></i> Deadline</div>
                    <div class="tool"><i class="fas fa-user"></i> Member</div>
                    <div class="tool"><i class="fas fa-folder-open"></i> Attachments</div>
                    <div class="tool"><i class="fas fa-list"></i> Checklists</div>
                </div>
                <div class="members-wrapper">
                    <div class="members-title">Members</div>
                    <div class="members">
                        <div class="member">
                            <img src="../image/2.png" alt="">
                        </div>
                        <div class="member">
                            <img src="../image/3.png" alt="">
                        </div>
                        <div class="member">
                            <img src="../image/4.png" alt="">
                        </div>
                    </div>
                </div>
            </div>
            <div class="comments-wrapper">
                <div class="comments">
                    <div class="light-comment-wrapper">
                        <div class="avatar">
                            <img src="../image/2.png" alt="">
                        </div>
                        <div class="light-comment">
                            <div class="name">User</div>
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                        </div>
                    </div>
                    <div class="light-comment-wrapper">
                        <div class="avatar">
                            <img src="../image/2.png" alt="">
                        </div>
                        <div class="light-comment">
                            <div class="name">User</div>
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                        </div>
                    </div>
                    <div class="light-comment-wrapper">
                        <div class="avatar">
                            <img src="../image/3.png" alt="">
                        </div>
                        <div class="light-comment">
                            <div class="name">User</div>
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                        </div>
                    </div>
                    <div class="light-comment-wrapper">
                        <div class="avatar">
                            <img src="../image/3.png" alt="">
                        </div>
                        <div class="light-comment">
                            <div class="name">User</div>
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                        </div>
                    </div>
                    <div class="light-comment-wrapper">
                        <div class="avatar">
                            <img src="../image/3.png" alt="">
                        </div>
                        <div class="light-comment">
                            <div class="name">User</div>
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                        </div>
                    </div>
                    <div class="light-comment-wrapper">
                        <div class="avatar">
                            <img src="../image/3.png" alt="">
                        </div>
                        <div class="light-comment">
                            <div class="name">User</div>
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                        </div>
                    </div>
                </div>
                <div class="comment-box">
                    <input type="text" class="text" placeholder="Comment..">
                    <div class="button"><i class="fas fa-paper-plane"></i></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>