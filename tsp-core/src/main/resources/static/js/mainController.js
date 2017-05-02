app.controller('mainController', ['$http', '$scope', function ($http, $scope) {
    $http.get('/user').then(
        function (response) {
            $scope.authenticated = true;
            $scope.user = response.data.userAuthentication.details.name;
        }, function (error) {
            $scope.authenticated = false;
            $scope.user = 'Non-Authenticated';
        });
}]);
