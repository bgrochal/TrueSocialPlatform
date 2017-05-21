app.controller('userController', ['$scope', function ($scope) {

    $scope.init = function () {
        $scope.welcomeMessage = $scope.$parent.usernameWarning ? $scope.$parent.usernameWarning :
            "You are signed in successfully. Choose an action from the menu.";

        if ($scope.$parent.usernameWarning) {
            $('.preventable').children().addClass('disabled');
        }
    };

    $scope.init();

}]);
