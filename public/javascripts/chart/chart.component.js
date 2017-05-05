(function() {
	'use strict';
	angular
		.module('app.chart')
		.component('appChart', {
			templateUrl: '/assets/javascripts/chart/chart.component.html',
			bindings: {
				title: '@',
				timeZone: '@'
			},
			controller: AppChartController
		})

	function AppChartController(DataFactory) {
		var ctrl = this;
		ctrl.$onInit = init;

		function init() {
			DataFactory.findLastDay().then(function(response) {
				Plotly.newPlot('chart', fillChart(response.data));
			}, function(error) {
				console.log(error);
			});
		}

		function fillChart(data) {
			var result = [{
				x: [],
				y: [],
				type: 'scatter'
			}];
			for (var index = 0; index < data.length; index++) {
				result[0].x.push(data[index].date)
				result[0].y.push(data[index].value)
			}
			return result;
		}
	}
})();