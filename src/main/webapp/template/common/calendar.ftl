<script type="text/javascript" language="javascript" src="/js/jquery.clockpick.1.2.9.js"></script>
<script type="text/javascript">
	var ng_config = {
		assests_dir: '/js/ng_calendar_all/assets/'	// the path to the assets directory
	}
</script>
<script type="text/javascript" src="/js/ng_calendar_all/ng_all.js"></script>
<script type="text/javascript" src="/js/ng_calendar_all/components/calendar.js"></script>
<script type="text/javascript">
var bookDate;
var bookDate2;
ng.ready(function(){
		// creating the calendar
		startdate = new ng.Calendar({
			input: 'startdate',	 // the input field id
			start_date: 'year - 1',	 // the start date (default is today)
			display_date: new Date() // the display date (default is start_date)
		});
		enddate = new ng.Calendar({
			input: 'enddate',	 // the input field id
			start_date: 'year - 1',	 // the start date (default is today)
			display_date: new Date() // the display date (default is start_date)
		});
	});
</script>