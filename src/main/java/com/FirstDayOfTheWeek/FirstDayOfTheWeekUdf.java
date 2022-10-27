package com.firstDayOfTheWeekUdf;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.DateTimeZone;

@UdfDescription(name = "firstDayOfTheWeek", author = "Mohammad Reza Hassanzadeh (github.com/MohammadRhz)", version = "1.0.0", description = "Get the date of the first day of the week when the date you want is in the range of that week", category = "DATE / TIME")
public class FirstDayOfTheWeekUdf {
  
    
    @Udf(description = "Returns timestamp of the first day of the week")
    public long firstDayOfTheWeek(@UdfParameter(value = "timestamp", description="Unix timestamp in milliseconds") final long timestamp,@UdfParameter(value = "timezone", description="Timezone (set GMT as default)") final String timezone,@UdfParameter(value = "shift", description="Useful when the first day of the week is other than Monday. Use -2 for Saturday and -1 for Sunday.") final int shift,@UdfParameter(value = "truncateTime", description = "Makes Hour, Minute, Second, and Millisecond 0") final boolean truncateTime) {
      
        int sh = shift * -1;
        
        DateTime dateTime = new DateTime(timestamp).withZone(DateTimeZone.forID(timezone));
        
        dateTime = dateTime.plusDays(sh);
        
		    dateTime = dateTime.minusDays(sh + dateTime.getDayOfWeek() - 1);
		    
		    if(truncateTime)
		    {
		      dateTime = dateTime.withTime(0, 0, 0, 0);
		    }
		    
		    return dateTime.getMillis();
    }
    
    
    @Udf(description = "Returns a formatted string of the date(time) of the first day of the week")
    public String firstDayOfTheWeek(@UdfParameter(value = "dateTimeStr", description="A formatted date and time string. e.g.: 2022-10-27 22:19:01.000") final String dateTimeStr,@UdfParameter(value = "formatPattern", description = "Format of the input dateTimeStr (Returned format will be the same as the input format). e.g.: yyyy-MM-dd HH:mm:ss.fff") final String formatPattern,@UdfParameter(value = "timezone", description="Timezone (set GMT as default)") final String timezone,@UdfParameter(value = "shift", description="Useful when the first day of the week is other than Monday. Use -2 for Saturday and -1 for Sunday.") final int shift,@UdfParameter(value = "truncateTime", description = "Makes Hour, Minute, Second, and Millisecond 0") final boolean truncateTime) {
      
        return firstDayOfTheWeek(dateTimeStr,formatPattern,formatPattern,timezone,shift,truncateTime);
    }
    
    
    @Udf(description = "Returns a formatted string of the date(time) of the first day of the week")
    public String firstDayOfTheWeek(@UdfParameter(value = "dateTimeStr") final String dateTimeStr,@UdfParameter(value = "inFormatPattern", description = "Format of the input dateTimeStr. e.g.: yyyy-MM-dd HH:mm:ss.fff") final String inFormatPattern,@UdfParameter(value = "outFormatPattern", description = "Format of the output dateTimeStr. e.g.: yyyy-MM-dd") final String outFormatPattern,@UdfParameter(value = "timezone", description="Timezone (set GMT as default)") final String timezone,@UdfParameter(value = "shift", description="Useful when the first day of the week is other than Monday. Use -2 for Saturday and -1 for Sunday.") final int shift,@UdfParameter(value = "truncateTime", description = "Makes Hour, Minute, Second, and Millisecond 0") final boolean truncateTime) {
       
        int sh = shift * -1;
        
        DateTimeFormatter formatter = DateTimeFormat.forPattern(inFormatPattern).withZone(DateTimeZone.forID(timezone));
        
        DateTime dateTime = DateTime.parse(dateTimeStr,formatter);
        
        dateTime = dateTime.plusDays(sh);
        
		    dateTime = dateTime.minusDays(sh + dateTime.getDayOfWeek() - 1);
		    
		    if(truncateTime)
		    {
		      dateTime = dateTime.withTime(0, 0, 0, 0);
		    }
		    
		    return dateTime.toString(outFormatPattern);
    }


}