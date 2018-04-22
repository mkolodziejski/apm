ViewController = function() { }


_.extend(ViewController.prototype, {

    clearContent: function() {
        $('#content_container').html('');
    },

    setContent: function(html) {
        $('#content_container').html(html).scrollTop(0);
    },

    buildChartSection: function(title, chartContainerId) {
        var header = $('<h2></h2>').html(title);
        var chartContainer = $('<div></div>')
            .attr({
                id: chartContainerId,
                class: 'chart_container'
            });

        var sectionDiv = $('<div></div>')
                .addClass('chart_section')
            .append(header)
            .append(chartContainer);

        return sectionDiv;
    },

    buildTwoChartSection: function(title, chartContainerId1, chartContainerId2) {
        var header = $('<h2></h2>').html(title);
        var chartContainer1 = $('<div></div>')
            .attr({
                id: chartContainerId1,
                class: 'small_chart_container'
            });
        var chartContainer2 = $('<div></div>')
            .attr({
                id: chartContainerId2,
                class: 'small_chart_container'
            });

        var clearDiv = $('<div></div>')
            .attr({
                style: 'clear: both;'
            })

        var sectionDiv = $('<div></div>')
                .addClass('chart_section')
            .append(header)
            .append(chartContainer1)
            .append(chartContainer2)
            .append(clearDiv);

        return sectionDiv;
    }
});
