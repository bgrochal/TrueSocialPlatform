app.config(function ($routeProvider, $locationProvider) {

    $locationProvider.html5Mode(true).hashPrefix('');

    $routeProvider
        .when('/', {
            templateUrl : 'templates/news.html',
            controller  : 'newsController'})
        .when('/user', {
            templateUrl : 'templates/user.html',
            controller  : 'userController'})
        .when('/user/new', {
            templateUrl : 'templates/newUser.html',
            controller  : 'newUserController'})
        .otherwise('/');

});
