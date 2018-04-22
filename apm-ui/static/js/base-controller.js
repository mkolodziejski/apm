BaseController = function() { }


_.extend(BaseController.prototype, {

    filterCsv: function(csv, desiredColumns) {
        var newCsv = '';

        var lines = csv.split("\n");

        var desiredCols = [];
        var header = lines[0];
        var columnNames = header.split(';');
        var firstHeaderCol = true;
        for (var i = 0; i < columnNames.length; ++i) {
            if(!_.contains(desiredColumns, columnNames[i])) {
                continue;
            }

            desiredCols.push(i);

            if(!firstHeaderCol) {
                newCsv += ";";
            }
            firstHeaderCol = false;
            newCsv += columnNames[i];
        }

        newCsv += "\n";

        for (var i = 1; i < lines.length; ++i) {
            var line = lines[i];
            var columns = line.split(';');

            var firstCol = true;
            for (var j = 0; j < columns.length; ++j) {
                if(!_.contains(desiredCols, j)) {
                    continue;
                }

                if(!firstCol) {
                    newCsv += ';';
                }
                firstCol = false;

                newCsv += columns[j];
            }
            newCsv += "\n";
        }

        return newCsv;
    }
});
