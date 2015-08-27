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
    $scope.endpoint = "192.168.25.68";
    $scope.turnLight = function(){
      $scope.color = $('#selectedColor').val();
      console.log($scope.color);
      var request = "http://"+$scope.endpoint+"/LED-"+$scope.color.substr(1);
      
      $http.get(request).success(function(response){
        
      }).error(function(){
        console.log("Erro na requisição.");
      })
    };
  });