
$(function () { 
    $('#container').highcharts({
        chart: {type: 'bar'},
        title: {text: 'Ameba free app'},
        xAxis: {categories: ['先生！バトルタイムですよ。', 'ワンダーランド', 'うち姫']},
        yAxis: {title: {text: 'Fruit eaten'}},
        series: [{name: 'Jane',data: [1, 0, 4]}, {name: 'John', data: [5, 7, 3]}]
    });
});