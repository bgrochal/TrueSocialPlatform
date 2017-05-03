app.controller('mainController', ['$http', '$scope', '$location', function ($http, $scope, $location) {
    $http.get('/user').then(
        function (response) {
            $scope.authenticated = true;
            $scope.user = response.data.userAuthentication.details.name;
        }, function (error) {
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
