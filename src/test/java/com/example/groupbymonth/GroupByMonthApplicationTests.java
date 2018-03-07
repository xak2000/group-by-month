package com.example.groupbymonth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupByMonthApplicationTests {

	@Autowired
	private ReportRepository reportRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void shouldReturnAllUniqueYearMonthsOrderedAsc() {
		reportRepository.save(new Report(1, Instant.parse("2018-03-07T14:18:00Z")));
		reportRepository.save(new Report(2, Instant.parse("2018-03-22T14:18:00Z")));
		reportRepository.save(new Report(3, Instant.parse("2016-10-07T14:18:00Z")));
		reportRepository.save(new Report(4, Instant.parse("2016-10-07T14:18:00Z")));
		reportRepository.save(new Report(5, Instant.parse("2017-10-07T14:18:00Z")));

		List<YearMonth> allUniqueYearMonths = reportRepository.allUniqueYearMonths();
		System.err.println(allUniqueYearMonths);

		assertThat(allUniqueYearMonths).containsExactly(
				YearMonth.of(2016, 10),
				YearMonth.of(2017, 10),
				YearMonth.of(2018, 3)
		);
	}

}
