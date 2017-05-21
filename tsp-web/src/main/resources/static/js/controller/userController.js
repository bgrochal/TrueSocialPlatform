app.controller('userController', ['$scope', '$http', function ($scope, $http) {

    $scope.init = function () {
        $scope.welcomeMessage = $scope.$parent.usernameWarning ? $scope.$parent.usernameWarning :
            'You are signed in successfully. Choose an action from the menu.';

        if ($scope.$parent.usernameWarning) {
            $('.preventable').children().addClass('disabled');
        }
    };

    $scope.newPostSubmit = function () {
        $scope.newPostError = null;
        $scope.newPostSuccess = null;

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

        $('form[name="newPostForm"]').reset();
    };

    $scope.init();

}]);
