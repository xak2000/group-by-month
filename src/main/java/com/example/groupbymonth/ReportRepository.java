package com.example.groupbymonth;

import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

public interface ReportRepository extends JpaRepository<Report, Integer> {

	/**
	 * This query uses internal H2 functions <a href="http://www.h2database.com/html/functions.html#year">YEAR</a>
	 * and <a href="http://www.h2database.com/html/functions.html#month">MONTH</a> to {@code GROUP BY} and then
	 * return all unique year-month combinations from all Report dates.
	 */
	@Query("select new com.example.groupbymonth.ReportRepository$InternalYearMonth(YEAR(r.date), MONTH(r.date))" +
			" from #{#entityName} r" +
			" group by YEAR(r.date), MONTH(r.date)" +
			" order by YEAR(r.date), MONTH(r.date)")
	List<InternalYearMonth> internalAllUniqueYearMonths();

	/**
	 * @return the list of all unique year-month combinations from all Report dates.
	 */
	default List<YearMonth> allUniqueYearMonths() {
		return internalAllUniqueYearMonths().stream().map(InternalYearMonth::toYearMonth).collect(Collectors.toList());
	}

	/**
	 * This class is required only because of JPQL can't create new Objects using static factory methods such as
	 * {@link YearMonth#of(int, int)}.
	 */
	@Value
	class InternalYearMonth {
		private final int year;
		private final int month;

		YearMonth toYearMonth() {
			return YearMonth.of(year, month);
		}
	}

}
