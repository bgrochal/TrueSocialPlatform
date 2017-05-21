app.controller('newUserController', ['$http', '$scope', '$location', function ($http, $scope, $location) {

    $scope.submit = function () {
        $scope.error = null;

        // Input validation.
        if (typeof $scope.username == 'undefined' || $scope.username.length === 0) {
            $scope.error = 'User name must not be empty.';
            return;
        }

        // Request for creating new user entity.
        $http({
            url     : '/users',
            method  : 'POST',
            params  : {
                'username' : $scope.username
            }
        }).then(
            function (response) {
                // Calling method from parent controller ('loginController') to set loginController's $scope.user
                // variable properly.
                $scope.getUser();
                $location.path('/user');
            },
            function (error) {
                if (error.status == 409) {
                    $scope.error = 'This user name is already in use.';
                } else {
                    $scope.error = 'Unexpected error: ' + error.data.status + ' (' + error.data.error + ').';
                }
            });
    };

}]);
