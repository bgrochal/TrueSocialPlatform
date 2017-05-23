app.controller('userController', ['$scope', '$http', function ($scope, $http) {

    $scope.init = function () {
        $scope.welcomeMessage = $scope.$parent.usernameWarning ? $scope.$parent.usernameWarning :
            'You are signed in successfully. Choose an action from the menu.';

        if ($scope.$parent.usernameWarning) {
            $('.preventable').children().addClass('disabled');
        }
    };

    $scope.clearAlerts = function () {
        $scope.newPostError = null;
        $scope.newPostSuccess = null;

        $scope.latestPostsError = null;
        $scope.commentsToPostError = null;
        $scope.editedPostError = null;

        $scope.latestCommentsError = null;
        $scope.editedCommentError = null;
    };

    $scope.newPostSubmit = function () {
        $scope.clearAlerts();

        $http({
            url     : '/posts',
            method  : 'POST',
            params  : {
                'authorId' : $scope.$parent._id
            },
            data : $scope.newPost
        }).then(
            function (response) {
                $scope.newPostSuccess = 'The post has been added successfully.';
            },
            function (error) {
                $scope.newPostError = 'Unexpected error: ' + error.status + '.';
                console.log(error);
            }
        );

        $('form[name="newPostForm"]')[0].reset();
    };

    $scope.getLatestPosts = function () {
        $scope.latestPostsError = null;
        $scope.hasMorePosts = true;

        // TODO: Change to getting posts of current user when the server API will be completed.
        $http({
            url     : '/posts/latest',
            method  : 'GET',
            params  : {
                'number' : 5
            }
        }).then(
            function (response) {
                $scope.allPosts = response.data.data;
                $scope.nextPageUrl = response.data.next;
                $scope.hasMorePosts = $scope.nextPageUrl != null;
            },
            function (error) {
                $scope.latestPostsError = 'Unexpected error: ' + error.status + '.';
                console.log(error);
            }
        );
    };

    $scope.getMorePosts = function () {
        $http({
            url     : $scope.nextPageUrl,
            method  : 'GET'
        }).then(
            function (response) {
                $scope.allPosts = $scope.allPosts.concat(response.data.data);
                $scope.nextPageUrl = response.data.next;
                $scope.hasMorePosts = $scope.nextPageUrl != null;
            },
            function (error) {
                $scope.latestPostsError = 'Unexpected error: ' + error.status + '.';
                console.log(error);
            }
        );
    };

    $scope.deletePost = function (postId) {
        // TODO: make a request to the server and put the code below into the successful callback.
        $scope.allPosts = $scope.allPosts.filter(function (post) {
            return post.id != postId;
        });
    };

    $scope.editPost = function (post) {
        $scope.editedPost = $.extend(true, {}, post);
        $('#editPostModal').modal();
    };

    $scope.editPostSubmit = function () {
        $http({
            url     : '/posts/' + $scope.editedPost.id,
            method  : 'PUT',
            params  : {
                'authorId' : $scope.editedPost.authorId
            },
            data    : $scope.editedPost
        }).then(
            function (response) {
                $scope.allPosts.forEach(function (post, i) {
                    if (post.id == $scope.editedPost.id) {
                        $scope.allPosts[i] = $scope.editedPost;
                    }
                });

                $('#editPostModal').modal('hide');
            },
            function (error) {
                $scope.editedPostError = 'Unexpected error: ' + error.status + '.';
                console.log(error);
            }
        );
    };

    $scope.getCommentsToPost = function (post) {
        $http({
            url     : '/comments/latest',
            method  : 'GET',
            params  : {
                'postId' : post.id,
                'number' : 5
            }
        }).then(
            function (response) {
                post.comments = response.data.data;
                post.nextComments = response.data.next;
            },
            function (error) {
                $scope.commentsToPostError = 'Unexpected error: ' + error.status + '.';
                console.log(error);
            }
        );
    };

    $scope.getMoreCommentsToPost = function (post) {
        $http({
            url     : post.nextComments,
            method  : 'GET'
        }).then(
            function (response) {
                post.comments = post.comments.concat(response.data.data);
                post.nextComments = response.data.next;
            },
            function (error) {
                $scope.commentsToPostError = 'Unexpected error: ' + error.status + '.';
                console.log(error);
            }
        );
    };

    $scope.getLatestComments = function () {
        $scope.latestCommentsError = null;
        $scope.hasMoreComments = true;

        // TODO: Change to getting comments of current user when the server API will be completed.
        $http({
            url     : '/comments/latest',
            method  : 'GET',
            params  : {
                'postId'    : '5922d5cd9a2ffe1c74acf31b',
                'number'    : 5
            }
        }).then(
            function (response) {
                $scope.allComments = response.data.data;
                $scope.nextCommentsUrl = response.data.next;
                $scope.hasMoreComments = $scope.nextCommentsUrl != null;
            },
            function (error) {
                $scope.latestCommentsError = 'Unexpected error: ' + error.status + '.';
                console.log(error);
            }
        );
    };

    $scope.getMoreComments = function () {
        $http({
            url     : $scope.nextCommentsUrl,
            method  : 'GET'
        }).then(
            function (response) {
                $scope.allComments = $scope.allComments.concat(response.data.data);
                $scope.nextCommentsUrl = response.data.next;
                $scope.hasMoreComments = $scope.nextCommentsUrl != null;
            },
            function (error) {
                $scope.latestCommentsError = 'Unexpected error: ' + error.status + '.';
                console.log(error);
            }
        );
    };

    $scope.deleteComment = function (commentId) {
        // TODO: make a request to the server and put the code below into the successful callback.
        $scope.allComments = $scope.allComments.filter(function (comment) {
            return comment.id != commentId;
        });
    };

    $scope.editComment = function (comment) {
        $scope.editedComment = $.extend(true, {}, comment);
        $('#editCommentModal').modal();
    };

    $scope.editCommentSubmit = function () {
        $http({
            url     : '/comments/' + $scope.editedComment.id,
            method  : 'PUT',
            params  : {
                'authorId'  : $scope.editedComment.authorId,
                'postId'    : $scope.editedComment.postId
            },
            data    : $scope.editedComment
        }).then(
            function (response) {
                $scope.allComments.forEach(function (comment, i) {
                    if (comment.id == $scope.editedComment.id) {
                        $scope.allComments[i] = $scope.editedComment;
                    }
                });

                $('#editCommentModal').modal('hide');
            },
            function (error) {
                $scope.editedCommentError = 'Unexpected error: ' + error.status + '.';
                console.log(error);
            }
        );
    };

    $scope.getPostToComment = function (comment) {
        // TODO: Change to getting the post with given comment.postId when the server API will be completed.
        $http({
            url     : '/posts/latest',
            method  : 'GET',
            params  : {
                'number' : 1
            }
        }).then(
            function (response) {
                $scope.postToCurrentComment = response.data.data[0];
                $('#postToCommentModal').modal();
            },
            function (error) {
                $scope.postToCommentError = 'Unexpected error: ' + error.status + '.';
                console.log(error);
            }
        );
    };

    $scope.init();

}]);
