(function() {

	'use strict';

	angular
		.module('app')
		.factory('DataFactory', DataFactory);

	function DataFactory($http) {
		var factory = {
			findLastDay: findLastDay
		}

		return factory;

		function findLastDay() {
			return $http.get('/car/speed/lastDay')
		}
	}

})();