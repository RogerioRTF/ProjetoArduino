// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
angular.module('starter', ['ionic'])

.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if(window.cordova && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
    }
    if(window.StatusBar) {
      StatusBar.styleDefault();
    }

  });
}).controller('luminariaCtrl', function($scope,$http) {


    $scope.color = "FF0000";
    var turnLight = function(){
      $http.get("http://192.168.25.68/LED-"+$scope.color).success(function(response){
        $scope.color = response;
      }).error(function(){
        $scope.color = "DEU RUIM";
      })
      //HTTP REQUEST WITH COLORS
    };

    $scope.blueLight = function(){
     // $scope.color = "blue";
      turnLight();
    }

    $scope.greenLight = function(){
      //$scope.color = "green";
      turnLight();
    }

    $scope.redLight = function(){
      //$scope.color = "red";
      turnLight();
    }


  });
