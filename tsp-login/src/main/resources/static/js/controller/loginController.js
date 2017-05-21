app.controller('loginController', ['$http', '$scope', '$location', function ($http, $scope, $location) {

    $scope.getUser = function () {
        $http.get('/me').then(
            function (response) {
                $scope.loginError = null;
                $scope.usernameWarning = null;

                // User logged in successfully, but he/she may not have username provided (e.g. because it's his/her
                // first login attempt).
                if (response.data.username == null) {
                    $scope.usernameWarning = 'User ' + response.data.name + ' does not have any user name provided.'
                }

                $scope.authenticated = true;
                $scope.user = response.data.name.concat(' (', response.data.username, ')');
            },
            function (error) {
                if (error.status == 403) {
                    // Client is not authenticated - that's not any error.
                    $scope.loginError = null;
                } else {
                    // Unexpected error occurred while authenticating.
                    $scope.logout();

                    $scope.loginError = 'Unexpected error: ' + error.status + '.';
                    console.log(error);
                }

                $scope.authenticated = false;
                $scope.user = 'Non-Authenticated';
            });
    };


    $scope.logout = function () {
        $http.post('/logout', {}).then(
            function (response) {
                $scope.authenticated = false;
                $location.path('/');
            }, function (error) {
                $scope.authenticated = false;
                console.log(error);
                $location.path('/');
            });
    };


    $scope.getUser();

}]);
