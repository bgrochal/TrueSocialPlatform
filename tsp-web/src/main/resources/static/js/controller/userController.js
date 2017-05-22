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

    $scope.init();

}]);
