app.controller('newsController', ['$scope', '$http', function ($scope, $http) {

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

    $scope.getCommentsToPost = function (post) {
        post.commentsToPostError = null;

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
                post.commentsToPostError = 'Unexpected error: ' + error.status + '.';
                console.log(error);
            }
        );
    };

    $scope.getMoreCommentsToPost = function (post) {
        post.commentsToPostError = null;
        
        $http({
            url     : post.nextComments,
            method  : 'GET'
        }).then(
            function (response) {
                post.comments = post.comments.concat(response.data.data);
                post.nextComments = response.data.next;
            },
            function (error) {
                post.commentsToPostError = 'Unexpected error: ' + error.status + '.';
                console.log(error);
            }
        );
    };

    $scope.addCommentSubmit = function (post) {
        post.newCommentError = null;

        $http({
            url     : '/comments',
            method  : 'POST',
            params  : {
                'authorId'  : $scope.$parent.id,
                'postId'    : post.id
            },
            data    : post.comment
        }).then(
            function (response) {
                // TODO: What if a new comment will appear before completion of this request?
                $http({
                    url     : '/comments/latest',
                    method  : 'GET',
                    params  : {
                        'postId' : post.id,
                        'number' : 1
                    }
                }).then(
                    function (get_response) {
                        post.comments = get_response.data.data.concat(post.comments);
                    },
                    function (get_error) {
                        post.newCommentError = 'Unexpected error: ' + get_error.status + '.';
                        console.log(get_error);
                    }
                );
            },
            function (error) {
                post.newCommentError = 'Unexpected error: ' + error.status + '.';
                console.log(error);
            }
        );

        // TODO: Resetting all forms is unnecessary. Refactor this code and try to reset the one, particular form.
        $.each($('form[name="addCommentForm"]'), function (i, form) {
            form.reset();
        });
    };


    $scope.getLatestPosts();

}]);
