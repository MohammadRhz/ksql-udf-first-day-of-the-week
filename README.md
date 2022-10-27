# KSQL UDF - First Day Of The Week
Get the date of the first day of the week when the date you want is in the range of that week

# How to install
1. Edit /etc/ksqldb/ksql-server.properties
2. Append this line:

        ksql.extension.dir=/opt/confluent-ksqldb/ext/

3. Copy FirstDayOfTheWeek-x.y.z.jar to /opt/confluent-ksqldb/ext/
4. Restart ksqldb-server
5. Query properties using:

        LIST PROPERTIES;
   
   Ensure the existence of this property:
        
         ksql.extension.dir  | KSQL  | SERVER | /opt/confluent-ksqldb/ext/
         
6. Query functions using:

        SHOW FUNCTIONS;
   
   Ensure the existence of this function:
       
        FIRSTDAYOFTHEWEEK     | DATE / TIME
        
 7. Query function description using: 

        DESCRIBE FUNCTION FirstDayOfTheWeek;

Description:

    Name        : FIRSTDAYOFTHEWEEK
    Author      : Mohammad Reza Hassanzadeh (github.com/MohammadRhz)
    Version     : 1.0.0
    Overview    : Get the date of the first day of the week when the date you want is in the range of that week
    Type        : SCALAR
    Jar         : /opt/confluent-ksqldb/ext/DateTimeUtils-1.0.0.jar
    Variations  :

          Variation   : FIRSTDAYOFTHEWEEK(timestamp BIGINT, shift INT, truncateTime BOOLEAN)
          Returns     : BIGINT
          Description : Returns timestamp of the first day of the week
          timestamp   : Unix timestamp in milliseconds
          shift       : Useful when the first day of the week is other than Monday. Use -2 for Saturday and -1 for Sunday.
          truncateTime: Makes Hour, Minute, Second, and Millisecond 0


          Variation   : FIRSTDAYOFTHEWEEK(dateTimeStr VARCHAR, inFormatPattern VARCHAR, outFormatPattern VARCHAR, shift INT, truncateTime BOOLEAN)
          Returns     : VARCHAR
          Description : Returns a formatted string of the date(time) of the first day of the week
          inFormatPattern: Format of the input dateTimeStr. e.g.: yyyy-MM-dd HH:mm:ss.fff
          outFormatPattern: Format of the output dateTimeStr. e.g.: yyyy-MM-dd
          shift       : Useful when the first day of the week is other than Monday. Use -2 for Saturday and -1 for Sunday.
          truncateTime: Makes Hour, Minute, Second, and Millisecond 0


          Variation   : FIRSTDAYOFTHEWEEK(dateTimeStr VARCHAR, formatPattern VARCHAR, shift INT, truncateTime BOOLEAN)
          Returns     : VARCHAR
          Description : Returns a formatted string of the date(time) of the first day of the week
          dateTimeStr : A formatted date and time string. e.g.: 2022-10-27 22:19:01.000
          formatPattern: Format of the input dateTimeStr (Returned format will be the same as the input format). e.g.: yyyy-MM-dd HH:mm:ss.fff
          shift       : Useful when the first day of the week is other than Monday. Use -2 for Saturday and -1 for Sunday.
          truncateTime: Makes Hour, Minute, Second, and Millisecond 0
          
          
# How to use
Read the description above and take a look at the examples below:


# Examples:
  
  ## 1. UNIX Timestamp
  
    -- 1666523533000 is Sunday, October 23, 2022 2:42:13 PM GMT+03:30
  
    select FIRSTDAYOFTHEWEEK(1666523533000,0,false) from test emit changes;
    
    +-----------------------------------------------------------------------------+
    |KSQL_COL_0                                                                   |
    +-----------------------------------------------------------------------------+
    |1666005133000  -- Monday, October 17, 2022 2:42:13 PM GMT+03:30
    
    
    
    -- In case the week in your area starts on Saturday
    select FIRSTDAYOFTHEWEEK(1666523533000,-2,false) from test emit changes;
    
    +-----------------------------------------------------------------------------+
    |KSQL_COL_0                                                                   |
    +-----------------------------------------------------------------------------+
    |1666437133000  -- Saturday, October 22, 2022 2:42:13 PM GMT+03:30
    
    
        
    select FIRSTDAYOFTHEWEEK(1666523533000,0,true) from test emit changes;
    
    +-----------------------------------------------------------------------------+
    |KSQL_COL_0                                                                   |
    +-----------------------------------------------------------------------------+
    |1665952200000  -- Monday, October 17, 2022 12:00:00 AM GMT+03:30
    
    
    
    
    
    
  

  ## 2. Formatted Date and Time

    select FIRSTDAYOFTHEWEEK('2022-10-23 12:13:14','yyyy-MM-dd HH:mm:ss',0,false) from test;
    
    +-----------------------------------------------------------------------------+
    |KSQL_COL_0                                                                   |
    +-----------------------------------------------------------------------------+
    |2022-10-17 12:13:14
    
    
    
    select FIRSTDAYOFTHEWEEK('2022-10-24 12:13:14','yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:00:00',0,false) from test;
    
    +-----------------------------------------------------------------------------+
    |KSQL_COL_0                                                                   |
    +-----------------------------------------------------------------------------+
    |2022-10-24 12:00:00
    
    
    
    select FIRSTDAYOFTHEWEEK('2022-10-25','yyyy-MM-dd', 'yyyy-MM-dd HH:00:00',0,false) from test;
    
    +-----------------------------------------------------------------------------+
    |KSQL_COL_0                                                                   |
    +-----------------------------------------------------------------------------+
    |2022-10-24 00:00:00
    
    
    
    select FIRSTDAYOFTHEWEEK('2022-10-26 22','yyyy-MM-dd HH',0,false) from test;
    
    +-----------------------------------------------------------------------------+
    |KSQL_COL_0                                                                   |
    +-----------------------------------------------------------------------------+
    |2022-10-24 22
    
    
    
    select FIRSTDAYOFTHEWEEK('2022-10-27 18:19:20','yyyy-MM-dd HH:mm:ss',0,true) from test;
    
    +-----------------------------------------------------------------------------+
    |KSQL_COL_0                                                                   |
    +-----------------------------------------------------------------------------+
    |2022-10-24 00:00:00
    
    
    -- In case the week in your area starts on Saturday
    select FIRSTDAYOFTHEWEEK('2022-10-28 18:19:20','yyyy-MM-dd HH:mm:ss',-2,true) from test;
    
    +-----------------------------------------------------------------------------+
    |KSQL_COL_0                                                                   |
    +-----------------------------------------------------------------------------+
    |2022-10-22 00:00:00
    
    


## Feel free to use, fork, and contribute!
  
