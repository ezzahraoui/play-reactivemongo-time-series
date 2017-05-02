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
		ctrl.chartConfig = {
			chart: {
				type: 'line'
			},
			series: [{
				data: []
			}],
			title: {
				text: ctrl.title
			},
			xAxis: [{
				type: 'datetime'
			}],
			yAxis: [{
				title: {
					text: 'Speed'
				}
			}]
		}
		ctrl.$onInit = init;

		function init() {
			DataFactory.findLastDay().then(function(response) {
				ctrl.chartConfig.series[0].data = fillChart(response.data);
				console.log(ctrl.chartConfig.series[0].data)
			}, function(error) {
				console.log(error);
			});
		}

		function fillChart(data) {
			var result = []
			for (var index = 0; index < data.length; index++) {
				result.push({
					x: new Date(data[index].date),
					y: data[index].value
				})
			}
			return result;
		}
	}
})();