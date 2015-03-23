<!DOCTYPE html>
<html>
<head>
	<title>jQuery : jsonTable</title>
	<script type="text/javascript" src="/js/json-to-table.js"></script>

</head>
<body>

<script type="text/javascript">

    //Example data, nested Object. This data will create nested table also.
    var nestedTable = [{
        key1: "val1",
        key2: "val2",
        key3: {
            tableId: "tblIdNested1",
            tableClassName: "clsNested",
            linkText: "Download",
            data: [{
                subkey1: "subval1",
                subkey2: "subval2",
	            data1: [{
	                subkey11: "subval11",
	                subkey12: "subval12",
	                subkey13: "subval13"
	            }]
            }]
        }
    }];

    var jsonHtmlTable = ConvertJsonToTable(nestedTable, 'jsonTable', null, 'Download');

	document.write(jsonHtmlTable);

</script>
</body>
</html>