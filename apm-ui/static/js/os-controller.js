OSController = function(viewController) {
    this.init(viewController);
}


_.extend(OSController.prototype, BaseController.prototype, {
    init: function(viewController) {
        this.viewController = viewController;
    },

    load: function() {
        this.allCharts = [];

        this.viewController.setContent(this.buildUI());

        this.loadCpuChart();
        this.loadMemoryChart();
        this.loadNetworkReceivedChart();
        this.loadNetworkSentChart();
    },

    unload: function() {
        _.each(this.allCharts, function(chart) {
            chart.destroy();
        });

        this.allCharts = [];
    },

    buildUI: function() {
        var ui = $('<div></div>')
            .append(this.viewController.buildChartSection('CPU', 'chart_cpu_container'))
            .append(this.viewController.buildChartSection('Memory', 'chart_memory_container'))
            .append(this.viewController.buildTwoChartSection('Network', 'chart_network_received_container', 'chart_network_sent_container'));

        return ui;
    },


    loadCpuChart: function() {
        var chart = Highcharts.chart('chart_cpu_container', {
            chart: {
                type: 'area'
            },
            title: {
                text: 'CPU usage'
            },
            subtitle: {
                text: 'Average use of CPU between metrics collections'
            },
            yAxis: {
                title: {
                    text: 'CPU'
                },
                labels: {
                    formatter: function () {
                        return this.value + '%';
                    }
                },
                min: 0,
                max: 100
            },
            legend: {
                enabled: false
            },
            tooltip: {
                pointFormat: 'CPU: <b>{point.y}%</b>'
            },
            plotOptions: {
                area: {
                    marker: {
                        enabled: false,
                        symbol: 'circle',
                        radius: 2,
                        states: {
                            hover: {
                                enabled: true
                            }
                        }
                    }
                }
            },
            data: {
                csvURL: window.location.origin + '/csv/os/cpu',
                enablePolling: true
            }
        });

        this.allCharts.push(chart);
    },


    loadMemoryChart: function() {
        var that = this;

        var chart = Highcharts.chart('chart_memory_container', {
            title: {
                text: 'OS memory usage'
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
                        return this.value / 1000000 + ' GB';
                    }
                },
                min: 0
            },
            legend: {
                enabled: true
            },
            tooltip: {
                pointFormatter: function() {
                    return 'Memory: <b>' + Math.round(this.y / 1000) + ' MB</b>';
                }
            },
            data: {
                csvURL: window.location.origin + '/csv/os/memory',
                enablePolling: true,
                beforeParse: function(data) {
                    return that.filterCsv(data, ['timestamp', 'memory_total', 'memory_free', 'memory_available']);
                }
            }
        });

        this.allCharts.push(chart);
    },


    loadNetworkReceivedChart: function() {
        var that = this;

        var chart = Highcharts.chart('chart_network_received_container', {
            title: {
                text: 'Network - bytes received'
            },
            subtitle: {
                text: ''
            },
            yAxis: {
                title: {
                    text: 'Network - bytes received'
                },
                labels: {
                    formatter: function () {
                        return this.value / 1000 + ' kB';
                    }
                },
                min: 0
            },
            legend: {
                enabled: true
            },
            tooltip: {
                pointFormat: 'Bytes received: <b>{point.y} bytes</b>'
            },
            data: {
                csvURL: window.location.origin + '/csv/os/network-eth0',
                enablePolling: true,
                beforeParse: function(data) {
                    return that.filterCsv(data, ['timestamp', 'bytes_received']);
                }
            }
        });

        this.allCharts.push(chart);
    },


    loadNetworkSentChart: function() {
        var that = this;

        var chart = Highcharts.chart('chart_network_sent_container', {
            title: {
                text: 'Network - bytes sent'
            },
            subtitle: {
                text: ''
            },
            yAxis: {
                title: {
                    text: 'Network - bytes sent'
                },
                labels: {
                    formatter: function () {
                        return this.value / 1000 + ' kB';
                    }
                },
                min: 0
            },
            legend: {
                enabled: true
            },
            tooltip: {
                pointFormat: 'Bytes sent: <b>{point.y} bytes</b>'
            },
            data: {
                csvURL: window.location.origin + '/csv/os/network-eth0',
                enablePolling: true,
                beforeParse: function(data) {
                    return that.filterCsv(data, ['timestamp', 'bytes_sent']);
                }
            }
        });

        this.allCharts.push(chart);
    }
});
