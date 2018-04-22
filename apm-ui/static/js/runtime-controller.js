RuntimeController = function(viewController) {
    this.init(viewController);
}


_.extend(RuntimeController.prototype, BaseController.prototype, {
    init: function(viewController) {
        this.viewController = viewController;
    },

    load: function() {
        this.allCharts = [];
        this.allMethodNames = [];

        this.fetchMethodNames();
    },

    unload: function() {
        _.each(this.allCharts, function(chart) {
            chart.destroy();
        });

        this.allCharts = [];
    },

    fetchMethodNames: function() {
        var that = this;

        $.ajax({
            url: window.location.origin + "/get_method_usages"
        }).done(function(data) {
            that.allMethodNames = data;
            that.viewController.setContent(that.buildUI());
            that.loadCharts();

        }).fail(function(data) {
            that.viewController.setContent('Failed to fetch data from server');
        })
    },

    buildUI: function() {
        var ui = $('<div></div>');

        if(this.allMethodNames.length != 0) {
            var that = this;
            _.each(this.allMethodNames, function(method) {
                ui.append(that.viewController.buildChartSection('Method: ' + method, that.containerIdForMethod(method)));
            });

        } else {
            ui.append($('<p></p>').text('No data so far...'))
        }

        return ui;
    },

    containerIdForMethod: function(method) {
        return 'chart_' + method + '_container';
    },

    loadCharts: function() {
        var that = this;
        _.each(this.allMethodNames, function(method) {
            that.loadMethodChart(method);
        });
    },



    loadMethodChart: function(method) {
        var that = this;

        var chart = Highcharts.chart(this.containerIdForMethod(method), {
            title: {
                text: 'Method usages'
            },
            subtitle: {
                text: ''
            },
            yAxis: [{
                    title: {
                        text: 'Execution time'
                    },
                    labels: {
                        formatter: function () {
                            return this.value + ' ms';
                        }
                    },
                    min: 0,
                    opposite: true
                }, {
                    gridLineWidth: 0,
                    title: {
                        text: 'Number of invocations',
                        style: {
                            color: Highcharts.getOptions().colors[0]
                        }
                    },
                    labels: {
                        format: '{value}',
                        style: {
                            color: Highcharts.getOptions().colors[0]
                        }
                    },
                    min: 0,
                    minTickInterval: 1
                }
            ],
            legend: {
                enabled: true
            },
            tooltip: {
                shared: true
            },
            data: {
                csvURL: window.location.origin + '/csv/runtime/' + method,
                enablePolling: true,
                beforeParse: function(data) {
                    return that.filterCsv(data, ['timestamp', 'count', 'minTime', 'maxTime', 'avgTime']);
                }
            },
            series: [{
                name: 'Inv. count',
                type: 'column',
                yAxis: 1
            }, {
                name: 'Min. time',
                type: 'line',
                dashStyle: 'shortdot',
                tooltip: {
                    valueSuffix: ' ms'
                }
            }, {
                name: 'Max. time',
                type: 'line',
                dashStyle: 'shortdot',
                tooltip: {
                    valueSuffix: ' ms'
                }
            }, {
                name: 'Avg. time',
                type: 'line',
                dashStyle: 'shortdot',
                tooltip: {
                    valueSuffix: ' ms'
                }
            }]
        });

        this.allCharts.push(chart);
    }

});
