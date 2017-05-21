SELECT
    *
FROM
    workresult_day
WHERE
    user_id = /* userId */1
    AND result_date >= /* from */'2016-03-01'
    AND result_date <= /* to */'2016-03-31'
ORDER BY
    result_date

