angular.module('starter', ['ionic'])

.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
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