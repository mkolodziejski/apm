JVMController = function(viewController) {
    this.init(viewController);
}


_.extend(JVMController.prototype, BaseController.prototype, {
    init: function(viewController) {
        this.viewController = viewController;
    },

    load: function() {
        this.allCharts = [];

        this.viewController.setContent(this.buildUI());

        this.loadJvmMemoryChart();
        this.loadThreadsChart();
        this.loadGcChart('PS Scavenge', '/csv/jmx/gc-ps_scavenge', 'chart_gc1_container');
        this.loadGcChart('PS MarkSweep', '/csv/jmx/gc-ps_marksweep', 'chart_gc2_container');
    },

    unload: function() {
        _.each(this.allCharts, function(chart) {
            chart.destroy();
        });

        this.allCharts = [];
    },

    buildUI: function() {
        var ui = $('<div></div>')
            .append(this.viewController.buildChartSection('JVM Memory', 'chart_jvm_memory_container'))
            .append(this.viewController.buildChartSection('Threads', 'chart_threads_container'))
            .append(this.viewController.buildTwoChartSection('Garbage Collectors', 'chart_gc1_container', 'chart_gc2_container'));

        return ui;
    },


    loadJvmMemoryChart: function() {
        var that = this;

        var chart = Highcharts.chart('chart_jvm_memory_container', {
            title: {
                text: 'JVM memory usage'
            },
            subtitle: {
                text: ''
            },
            yAxis: {
                title: {
                    text: 'Memory'
                },
                labels: {
                    formatter: function () {
                        return this.value / 1000000 + ' MB';
                    }
                },
                min: 0
            },
            legend: {
                enabled: true
            },
            tooltip: {
                pointFormat: 'Memory: <b>{point.y} bytes</b>'
            },
            data: {
                csvURL: window.location.origin + '/csv/jmx/jvm_memory_usage',
                enablePolling: true,
                beforeParse: function(data) {
                    return that.filterCsv(data, ['timestamp', 'used', 'committed', 'max']);
                }
            }
        });

        this.allCharts.push(chart);
    },


    loadThreadsChart: function() {
        var that = this;

        var chart = Highcharts.chart('chart_threads_container', {
            title: {
                text: 'Threads'
            },
            subtitle: {
                text: ''
            },
            yAxis: {
                title: {
                    text: 'Threads'
                },
                labels: {
                    formatter: function () {
                        return this.value;
                    }
                },
                min: 0,
                minTickInterval: 1
            },
            legend: {
                enabled: true
            },
            tooltip: {
                pointFormat: 'Threads: <b>{point.y}</b>'
            },
            data: {
                csvURL: window.location.origin + '/csv/jmx/threads',
                enablePolling: true
            }
        });

        this.allCharts.push(chart);
    },


    loadGcChart: function(gcName, apiPath, containerId) {
        var that = this;

        var chart = Highcharts.chart(containerId, {
            title: {
                text: 'Garbage Collector - ' + gcName
            },
            subtitle: {
                text: ''
            },
            yAxis: {
                title: {
                    text: 'Time'
                },
                labels: {
                    formatter: function () {
                        return this.value / 1000 + ' s';
                    }
                },
                min: 0
            },
            legend: {
                enabled: true
            },
            tooltip: {
                pointFormat: 'Time: <b>{point.y} ms</b>'
            },
            data: {
                csvURL: window.location.origin + apiPath,
                enablePolling: true,
                beforeParse: function(data) {
                    return that.filterCsv(data, ['timestamp', 'time']);
                }
            }
        });

        this.allCharts.push(chart);
    }
});
