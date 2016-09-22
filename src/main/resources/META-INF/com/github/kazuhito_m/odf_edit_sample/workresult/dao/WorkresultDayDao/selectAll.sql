SELECT
    id
    ,user_id
    ,result_date
    ,start_time
    ,end_time
    ,break_hour
    ,description
FROM
    workresult_day
ORDER BY
    user_id
    ,result_date

