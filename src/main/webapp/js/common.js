//################################################
//  getByteLength(obj) : Get a count of litter.
//################################################
function getByteLength(obj){
    var s = obj.value;
    var len = 0;
    if ( s == null ) return true;
    for(var i=0;i<s.length;i++){
        var c = escape(s.charAt(i));
        if ( c.length == 1 ) len ++;
        else if ( c.indexOf("%u") != -1 ) len += 2;
        else if ( c.indexOf("%") != -1 ) len += c.length/3;

        if(len >= 210 ){
            alert(!"210byte以上の文字を入力できないです。");
            obj.value = s.substring(0,i - 2);
            return false;
        }

    }
    return true;
}

/*
// JQuery for json to table
$.getJSON(url , function(data) {
    var tbl_body = "";
    $.each(data, function() {
        var tbl_row = "";
        $.each(this, function(k , v) {
            tbl_row += "<td>"+v+"</td>";
        })
        tbl_body += "<tr>"+tbl_row+"</tr>";                 
    })
    $("#target_table_id tbody").html(tbl_body);
});

var expected_keys = { key_1 : true, key_2 : true, key_3 : false, key_4 : true };

if ( ( k in expected_keys ) && expected_keys[k] ) {
	...
	}
*/
