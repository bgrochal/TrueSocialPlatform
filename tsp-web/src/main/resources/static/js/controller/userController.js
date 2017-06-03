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

        $scope.deletingAccountError = null;
    };

    $scope.newPostSubmit = function () {
        $scope.clearAlerts();

        $http({
            url     : '/posts',
            method  : 'POST',
            params  : {
                'authorId' : $scope.$parent.id
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

        $http({
            url     : '/posts',
            method  : 'GET',
            params  : {
                'authorId'  : $scope.$parent.id,
                'number'    : 5
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

    $scope.deletePost = function (post) {
        $http({
            url     : '/posts/' + post.id,
            method  : 'DELETE'
        }).then(
            function (response) {
                $scope.allPosts = $scope.allPosts.filter(function (p) {
                    return p.id != post.id;
                });
            },
            function (error) {
                post.postRelatedError = "Unable to delete the post (" + error.status + ").";
                console.log(error);
            }
        );
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

        $http({
            url     : '/comments',
            method  : 'GET',
            params  : {
                'authorId'  : $scope.$parent.id,
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

    $scope.deleteComment = function (comment) {
        $http({
            url     : '/comments/' + comment.id,
            method  : 'DELETE'
        }).then(
            function (response) {
                $scope.allComments = $scope.allComments.filter(function (c) {
                    return c.id != comment.id;
                });
            },
            function (error) {
                comment.commentRelatedError = "Unable to delete the comment (" + error.status + ").";
                console.log(error);
            }
        );
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
        $http({
            url     : '/posts/' + comment.postId,
            method  : 'GET',
            params  : {
                'authorId' : $scope.$parent.id
            }
        }).then(
            function (response) {
                $scope.postToCurrentComment = response.data;
                $('#postToCommentModal').modal();
            },
            function (error) {
                $scope.postToCommentError = 'Unexpected error: ' + error.status + '.';
                console.log(error);
            }
        );
    };

    $scope.deleteAccount = function () {
        $scope.deletingAccountError = null;

        $http({
            url     : '/users/' + $scope.$parent.id,
            method  : 'DELETE'
        }).then(
            function (response) {
                $scope.$parent.logout();
            },
            function (error) {
                $scope.deletingAccountError = 'Unexpected error while deleting the account: ' + error.status + '.';
                console.log(error);
            }
        );
    };

    $scope.init();

}]);
