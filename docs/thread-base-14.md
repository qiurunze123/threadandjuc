    
    **AQS**
    
    https://juejin.im/post/5bbf04d5f265da0ad947f05b 
    
    
    
    
    暂时记录
    一、MySQL 获得当前日期时间 函数
    1.1 获得当前日期+时间（date + time）函数：now()
    mysql> select now();
    
    +---------------------+
    | now() |
    +---------------------+
    | 2008-08-08 22:20:46 |
    +---------------------+
    
    除了 now() 函数能获得当前的日期时间外，MySQL 中还有下面的函数：
    current_timestamp()
    ,current_timestamp
    ,localtime()
    ,localtime
    ,localtimestamp -- (v4.0.6)
    ,localtimestamp() -- (v4.0.6)
    
    这些日期时间函数，都等同于 now()。鉴于 now() 函数简短易记，建议总是使用 now() 来替代上面列出的函数。
    1.2 获得当前日期+时间（date + time）函数：sysdate()
    sysdate() 日期时间函数跟 now() 类似，不同之处在于：now() 在执行开始时值就得到了， sysdate() 在函数执行时动态得到值。看下面的例子就明白了：
    mysql> select now(), sleep(3), now();
    
    +---------------------+----------+---------------------+
    | now() | sleep(3) | now() |
    +---------------------+----------+---------------------+
    | 2008-08-08 22:28:21 | 0 | 2008-08-08 22:28:21 |
    +---------------------+----------+---------------------+
    
    mysql> select sysdate(), sleep(3), sysdate();
    
    +---------------------+----------+---------------------+
    | sysdate() | sleep(3) | sysdate() |
    +---------------------+----------+---------------------+
    | 2008-08-08 22:28:41 | 0 | 2008-08-08 22:28:44 |
    +---------------------+----------+---------------------+
    
    可以看到，虽然中途 sleep 3 秒，但 now() 函数两次的时间值是相同的； sysdate() 函数两次得到的时间值相差 3 秒。MySQL Manual 中是这样描述 sysdate() 的：Return the time at which the function executes。
    sysdate() 日期时间函数，一般情况下很少用到。
    2. 获得当前日期（date）函数：curdate()
    mysql> select curdate();
    
    +------------+
    | curdate() |
    +------------+
    | 2008-08-08 |
    +------------+
    
    其中，下面的两个日期函数等同于 curdate()：
    current_date()
    ,current_date
    
    3. 获得当前时间（time）函数：curtime()
    mysql> select curtime();
    
    +-----------+
    | curtime() |
    +-----------+
    | 22:41:30 |
    +-----------+
    
    其中，下面的两个时间函数等同于 curtime()：
    current_time()
    ,current_time
    
    4. 获得当前 UTC 日期时间函数：utc_date(), utc_time(), utc_timestamp()
    mysql> select utc_timestamp(), utc_date(), utc_time(), now()
    
    +---------------------+------------+------------+---------------------+
    | utc_timestamp() | utc_date() | utc_time() | now() |
    +---------------------+------------+------------+---------------------+
    | 2008-08-08 14:47:11 | 2008-08-08 | 14:47:11 | 2008-08-08 22:47:11 |
    +---------------------+------------+------------+---------------------+
    
    因为我国位于东八时区，所以本地时间 = UTC 时间 + 8 小时。UTC 时间在业务涉及多个国家和地区的时候，非常有用。
    
    二、MySQL 日期时间 Extract（选取） 函数。
    1. 选取日期时间的各个部分：日期、时间、年、季度、月、日、小时、分钟、秒、微秒
    set @dt = '2008-09-10 07:15:30.123456';
    
    select date(@dt); -- 2008-09-10
    select time(@dt); -- 07:15:30.123456
    select year(@dt); -- 2008
    select quarter(@dt); -- 3
    select month(@dt); -- 9
    select week(@dt); -- 36
    select day(@dt); -- 10
    select hour(@dt); -- 7
    select minute(@dt); -- 15
    select second(@dt); -- 30
    select microsecond(@dt); -- 123456
    
    2. MySQL Extract() 函数，可以上面实现类似的功能：
    set @dt = '2008-09-10 07:15:30.123456';
    
    select extract(year from @dt); -- 2008
    select extract(quarter from @dt); -- 3
    select extract(month from @dt); -- 9
    select extract(week from @dt); -- 36
    select extract(day from @dt); -- 10
    select extract(hour from @dt); -- 7
    select extract(minute from @dt); -- 15
    select extract(second from @dt); -- 30
    select extract(microsecond from @dt); -- 123456
    
    select extract(year_month from @dt); -- 200809
    select extract(day_hour from @dt); -- 1007
    select extract(day_minute from @dt); -- 100715
    select extract(day_second from @dt); -- 10071530
    select extract(day_microsecond from @dt); -- 10071530123456
    select extract(hour_minute from @dt); -- 715
    select extract(hour_second from @dt); -- 71530
    select extract(hour_microsecond from @dt); -- 71530123456
    select extract(minute_second from @dt); -- 1530
    select extract(minute_microsecond from @dt); -- 1530123456
    select extract(second_microsecond from @dt); -- 30123456
    
    MySQL Extract() 函数除了没有date(),time() 的功能外，其他功能一应具全。并且还具有选取‘day_microsecond’ 等功能。注意这里不是只选取 day 和 microsecond，而是从日期的 day 部分一直选取到 microsecond 部分。够强悍的吧！
    MySQL Extract() 函数唯一不好的地方在于：你需要多敲几次键盘。
    3. MySQL dayof... 函数：dayofweek(), dayofmonth(), dayofyear()
    分别返回日期参数，在一周、一月、一年中的位置。
    set @dt = '2008-08-08';
    
    select dayofweek(@dt); -- 6
    select dayofmonth(@dt); -- 8
    select dayofyear(@dt); -- 221
    
    日期 '2008-08-08' 是一周中的第 6 天（1 = Sunday, 2 = Monday, ..., 7 = Saturday）；一月中的第 8 天；一年中的第 221 天。
    4. MySQL week... 函数：week(), weekofyear(), dayofweek(), weekday(), yearweek()
    set @dt = '2008-08-08';
    
    select week(@dt); -- 31
    select week(@dt,3); -- 32
    select weekofyear(@dt); -- 32
    
    select dayofweek(@dt); -- 6
    select weekday(@dt); -- 4
    
    select yearweek(@dt); -- 200831
    
    MySQL week() 函数，可以有两个参数，具体可看手册。 weekofyear() 和 week() 一样，都是计算“某天”是位于一年中的第几周。 weekofyear(@dt) 等价于 week(@dt,3)。
    MySQL weekday() 函数和 dayofweek() 类似，都是返回“某天”在一周中的位置。不同点在于参考的标准， weekday：(0 = Monday, 1 = Tuesday, ..., 6 = Sunday)； dayofweek：（1 = Sunday, 2 = Monday, ..., 7 = Saturday）
    MySQL yearweek() 函数，返回 year(2008) + week 位置(31)。
    5. MySQL 返回星期和月份名称函数：dayname(), monthname()
    set @dt = '2008-08-08';
    
    select dayname(@dt); -- Friday
    select monthname(@dt); -- August
    
    思考，如何返回中文的名称呢？
    6. MySQL last_day() 函数：返回月份中的最后一天。
    select last_day('2008-02-01'); -- 2008-02-29
    select last_day('2008-08-08'); -- 2008-08-31
    
    MySQL last_day() 函数非常有用，比如我想得到当前月份中有多少天，可以这样来计算：
    mysql> select now(), day(last_day(now())) as days;
    
    +---------------------+------+
    | now() | days |
    +---------------------+------+
    | 2008-08-09 11:45:45 | 31 |
    +---------------------+------+
    
    三、MySQL 日期时间计算函数
    1. MySQL 为日期增加一个时间间隔：date_add()
    set @dt = now();
    
    select date_add(@dt, interval 1 day); -- add 1 day
    select date_add(@dt, interval 1 hour); -- add 1 hour
    select date_add(@dt, interval 1 minute); -- ...
    select date_add(@dt, interval 1 second);
    select date_add(@dt, interval 1 microsecond);
    select date_add(@dt, interval 1 week);
    select date_add(@dt, interval 1 month);
    select date_add(@dt, interval 1 quarter);
    select date_add(@dt, interval 1 year);
    
    select date_add(@dt, interval -1 day); -- sub 1 day
    
    MySQL adddate(), addtime()函数，可以用 date_add() 来替代。下面是 date_add() 实现 addtime() 功能示例：
    mysql> set @dt = '2008-08-09 12:12:33';
    
    mysql>
    mysql> select date_add(@dt, interval '01:15:30' hour_second);
    
    +------------------------------------------------+
    | date_add(@dt, interval '01:15:30' hour_second) |
    +------------------------------------------------+
    | 2008-08-09 13:28:03 |
    +------------------------------------------------+
    
    mysql> select date_add(@dt, interval '1 01:15:30' day_second);
    
    +-------------------------------------------------+
    | date_add(@dt, interval '1 01:15:30' day_second) |
    +-------------------------------------------------+
    | 2008-08-10 13:28:03 |
    +-------------------------------------------------+
    
    date_add() 函数，分别为 @dt 增加了“1小时 15分 30秒” 和 “1天 1小时 15分 30秒”。建议：总是使用 date_add() 日期时间函数来替代 adddate(), addtime()。
    2. MySQL 为日期减去一个时间间隔：date_sub()
    mysql> select date_sub('1998-01-01 00:00:00', interval '1 1:1:1' day_second);
    
    +----------------------------------------------------------------+
    | date_sub('1998-01-01 00:00:00', interval '1 1:1:1' day_second) |
    +----------------------------------------------------------------+
    | 1997-12-30 22:58:59 |
    +----------------------------------------------------------------+
    
    MySQL date_sub() 日期时间函数 和 date_add() 用法一致，不再赘述。另外，MySQL 中还有两个函数 subdate(), subtime()，建议，用 date_sub() 来替代。
    3. MySQL 另类日期函数：period_add(P,N), period_diff(P1,P2)
    函数参数“P” 的格式为“YYYYMM” 或者 “YYMM”，第二个参数“N” 表示增加或减去 N month（月）。
    MySQL period_add(P,N)：日期加/减去N月。
    mysql> select period_add(200808,2), period_add(20080808,-2)
    
    +----------------------+-------------------------+
    | period_add(200808,2) | period_add(20080808,-2) |
    +----------------------+-------------------------+
    | 200810 | 20080806 |
    +----------------------+-------------------------+
    
    MySQL period_diff(P1,P2)：日期 P1-P2，返回 N 个月。
    mysql> select period_diff(200808, 200801);
    
    +-----------------------------+
    | period_diff(200808, 200801) |
    +-----------------------------+
    | 7 |
    +-----------------------------+
    
    在 MySQL 中，这两个日期函数，一般情况下很少用到。
    4. MySQL 日期、时间相减函数：datediff(date1,date2), timediff(time1,time2)
    MySQL datediff(date1,date2)：两个日期相减 date1 - date2，返回天数。
    select datediff('2008-08-08', '2008-08-01'); -- 7
    select datediff('2008-08-01', '2008-08-08'); -- -7
    
    MySQL timediff(time1,time2)：两个日期相减 time1 - time2，返回 time 差值。
    select timediff('2008-08-08 08:08:08', '2008-08-08 00:00:00'); -- 08:08:08
    select timediff('08:08:08', '00:00:00'); -- 08:08:08
    
    注意：timediff(time1,time2) 函数的两个参数类型必须相同。
    
    四、MySQL 日期转换函数、时间转换函数
    1. MySQL （时间、秒）转换函数：time_to_sec(time), sec_to_time(seconds)
    select time_to_sec('01:00:05'); -- 3605
    select sec_to_time(3605); -- '01:00:05'
    
    2. MySQL （日期、天数）转换函数：to_days(date), from_days(days)
    select to_days('0000-00-00'); -- 0
    select to_days('2008-08-08'); -- 733627
    
    select from_days(0); -- '0000-00-00'
    select from_days(733627); -- '2008-08-08'
    
    3. MySQL Str to Date （字符串转换为日期）函数：str_to_date(str, format)
    select str_to_date('08/09/2008', '%m/%d/%Y'); -- 2008-08-09
    select str_to_date('08/09/08' , '%m/%d/%y'); -- 2008-08-09
    select str_to_date('08.09.2008', '%m.%d.%Y'); -- 2008-08-09
    select str_to_date('08:09:30', '%h:%i:%s'); -- 08:09:30
    select str_to_date('08.09.2008 08:09:30', '%m.%d.%Y %h:%i:%s'); -- 2008-08-09 08:09:30
    
    可以看到，str_to_date(str,format) 转换函数，可以把一些杂乱无章的字符串转换为日期格式。另外，它也可以转换为时间。“format” 可以参看 MySQL 手册。
    4. MySQL Date/Time to Str（日期/时间转换为字符串）函数：date_format(date,format), time_format(time,format)
    mysql> select date_format('2008-08-08 22:23:00', '%W %M %Y');
    
    +------------------------------------------------+
    | date_format('2008-08-08 22:23:00', '%W %M %Y') |
    +------------------------------------------------+
    | Friday August 2008 |
    +------------------------------------------------+
    
    mysql> select date_format('2008-08-08 22:23:01', '%Y%m%d%H%i%s');
    
    +----------------------------------------------------+
    | date_format('2008-08-08 22:23:01', '%Y%m%d%H%i%s') |
    +----------------------------------------------------+
    | 20080808222301 |
    +----------------------------------------------------+
    
    mysql> select time_format('22:23:01', '%H.%i.%s');
    
    +-------------------------------------+
    | time_format('22:23:01', '%H.%i.%s') |
    +-------------------------------------+
    | 22.23.01 |
    +-------------------------------------+
    
    MySQL 日期、时间转换函数：date_format(date,format), time_format(time,format) 能够把一个日期/时间转换成各种各样的字符串格式。它是 str_to_date(str,format) 函数的 一个逆转换。
    5. MySQL 获得国家地区时间格式函数：get_format()
    MySQL get_format() 语法：
    get_format(date|time|datetime, 'eur'|'usa'|'jis'|'iso'|'internal'
    
    MySQL get_format() 用法的全部示例：
    select get_format(date,'usa') ; -- '%m.%d.%Y'
    select get_format(date,'jis') ; -- '%Y-%m-%d'
    select get_format(date,'iso') ; -- '%Y-%m-%d'
    select get_format(date,'eur') ; -- '%d.%m.%Y'
    select get_format(date,'internal') ; -- '%Y%m%d'
    select get_format(datetime,'usa') ; -- '%Y-%m-%d %H.%i.%s'
    select get_format(datetime,'jis') ; -- '%Y-%m-%d %H:%i:%s'
    select get_format(datetime,'iso') ; -- '%Y-%m-%d %H:%i:%s'
    select get_format(datetime,'eur') ; -- '%Y-%m-%d %H.%i.%s'
    select get_format(datetime,'internal') ; -- '%Y%m%d%H%i%s'
    select get_format(time,'usa') ; -- '%h:%i:%s %p'
    select get_format(time,'jis') ; -- '%H:%i:%s'
    select get_format(time,'iso') ; -- '%H:%i:%s'
    select get_format(time,'eur') ; -- '%H.%i.%s'
    select get_format(time,'internal') ; -- '%H%i%s'
    
    MySQL get_format() 函数在实际中用到机会的比较少。
    6. MySQL 拼凑日期、时间函数：makdedate(year,dayofyear), maketime(hour,minute,second)
    select makedate(2001,31); -- '2001-01-31'
    select makedate(2001,32); -- '2001-02-01'
    
    select maketime(12,15,30); -- '12:15:30'
    
    五、MySQL 时间戳（Timestamp）函数
    1. MySQL 获得当前时间戳函数：current_timestamp, current_timestamp()
    mysql> select current_timestamp, current_timestamp();
    
    +---------------------+---------------------+
    | current_timestamp | current_timestamp() |
    +---------------------+---------------------+
    | 2008-08-09 23:22:24 | 2008-08-09 23:22:24 |
    +---------------------+---------------------+
    
    2. MySQL （Unix 时间戳、日期）转换函数：
    unix_timestamp(),
    unix_timestamp(date),
    from_unixtime(unix_timestamp),
    from_unixtime(unix_timestamp,format)
    
    下面是示例：
    select unix_timestamp(); -- 1218290027
    select unix_timestamp('2008-08-08'); -- 1218124800
    select unix_timestamp('2008-08-08 12:30:00'); -- 1218169800
    
    select from_unixtime(1218290027); -- '2008-08-09 21:53:47'
    select from_unixtime(1218124800); -- '2008-08-08 00:00:00'
    select from_unixtime(1218169800); -- '2008-08-08 12:30:00'
    
    select from_unixtime(1218169800, '%Y %D %M %h:%i:%s %x'); -- '2008 8th August 12:30:00 2008'
    
    3. MySQL 时间戳（timestamp）转换、增、减函数：
    timestamp(date) -- date to timestamp
    timestamp(dt,time) -- dt + time
    timestampadd(unit,interval,datetime_expr) --
    timestampdiff(unit,datetime_expr1,datetime_expr2) --
    
    请看示例部分：
    select timestamp('2008-08-08'); -- 2008-08-08 00:00:00
    select timestamp('2008-08-08 08:00:00', '01:01:01'); -- 2008-08-08 09:01:01
    select timestamp('2008-08-08 08:00:00', '10 01:01:01'); -- 2008-08-18 09:01:01
    
    select timestampadd(day, 1, '2008-08-08 08:00:00'); -- 2008-08-09 08:00:00
    select date_add('2008-08-08 08:00:00', interval 1 day); -- 2008-08-09 08:00:00
    
    MySQL timestampadd() 函数类似于 date_add()。
    select timestampdiff(year,'2002-05-01','2001-01-01'); -- -1
    select timestampdiff(day ,'2002-05-01','2001-01-01'); -- -485
    select timestampdiff(hour,'2008-08-08 12:00:00','2008-08-08 00:00:00'); -- -12
    
    select datediff('2008-08-08 12:00:00', '2008-08-01 00:00:00'); -- 7
    
    MySQL timestampdiff() 函数就比 datediff() 功能强多了，datediff() 只能计算两个日期（date）之间相差的天数。
    
    六、MySQL 时区（timezone）转换函数
    convert_tz(dt,from_tz,to_tz)
    
    select convert_tz('2008-08-08 12:00:00', '+08:00', '+00:00'); -- 2008-08-08 04:00:00
    
    时区转换也可以通过 date_add, date_sub, timestampadd 来实现。
    select date_add('2008-08-08 12:00:00', interval -8 hour); -- 2008-08-08 04:00:00
    select date_sub('2008-08-08 12:00:00', interval 8 hour); -- 2008-08-08 04:00:00
    select timestampadd(hour, -8, '2008-08-08 12:00:00'); -- 2008-08-08 04:00:00