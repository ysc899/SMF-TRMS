'use strict';

window.colors = [
	'rgb(238, 82, 83)',
	'rgb(255, 99, 132)',
	'rgb(255, 159, 64)',
	'rgb(255, 205, 86)',
	'rgb(75, 192, 192)',
	'rgb(54, 162, 235)',
	'rgb(153, 102, 255)',
	'rgb(255, 159, 243)',
	'rgb(243, 104, 224)',
	'rgb(48, 51, 107)',
	'rgb(201, 203, 207)',
	'rgb(10, 189, 227)',
	'rgb(20, 20, 20)'
];
var chart;
function graph(targerId, type, graphData,labels,min){	
	var ctx = document.getElementById(targerId).getContext('2d');
	ctx.canvas.width = 400;
	ctx.canvas.height = 300;
    if (chart) {
        chart.reset();
        chart.destroy();
    }
	var chartDataSet =  [];
	for (var tmp in graphData){
		var itemtype = graphData[tmp].type;
		itemtype = isNull(itemtype)?type:itemtype;
		
//		console.log(graphData[tmp].type , type,itemtype);
		var set = {
				type: itemtype,
				label:graphData[tmp % 13].label, 
				backgroundColor: window.colors[tmp % 13],
				borderColor: window.colors[tmp % 13],
				borderWidth: 1, 
				fill: false,
				data: graphData[tmp].data 
			};
		chartDataSet.push(set);			
	}
	
	var chartData = {
		labels: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
		datasets: chartDataSet
	};
	if(!isNull(labels)){
		chartData = {
			labels: labels,
			datasets: chartDataSet
		};
	}
	chart = new Chart(ctx, {
			type: type,
			data: chartData,
			options: {
//                maintainAspectRatio: false,
				responsive: true,
				legend: {
					position: 'top',
				}
			, onResize:graphResize
		    , scales: {
		        yAxes: [{
		            ticks: {
		                beginAtZero:true,
		                min: 0   
		            }
		          }]
		       }
			}
		});
	chart.options.onResize( );	
	
	window.chart.update();
	TabResize();
}

function graphResize( ddddc, aaaa){
	TabResize();
}