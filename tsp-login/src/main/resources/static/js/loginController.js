app.controller('loginController', ['$http', '$scope', '$location', function ($http, $scope, $location) {

    $http.get('/me').then(
        function (response) {
            $scope.loginError = '';
            $scope.authenticated = true;
            $scope.user = response.data.name.concat(' (', response.data.email, ')');
        }, function (error) {
            if (error.status == 401) {
                $scope.logout();
                $scope.loginError = error.data.errorMessage
            }
            if (error.status == 403) {
                $scope.loginError = '';
            }
            $scope.authenticated = false;
            $scope.user = 'Non-Authenticated';
        });

    $scope.logout = function () {
        $http.post('/logout', {}).then(
            function (response) {
                $scope.authenticated = false;
                $location.path('/');
            }, function (error) {
                $scope.authenticated = false;
                console.log(error);
            });
    };

}]);
