package com.urbanladder.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

	public static ExtentReports report;

	public static ExtentReports getInstance() {

		if (report == null) {
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
					System.getProperty("user.dir")
							+ "\\test-output\\testreport_" + System.currentTimeMillis() + ".html");
			report = new ExtentReports();
			report.attachReporter(htmlReporter);

			report.setSystemInfo("OS", "Windows 10");
			report.setSystemInfo("Team", "Auto-Bots");;

			htmlReporter.config().setDocumentTitle("Automation Results");
			htmlReporter.config().setReportName("Test Report");
			htmlReporter.config().setEncoding("utf-8");
			htmlReporter.config().setTheme(Theme.STANDARD);
			htmlReporter.config().setChartVisibilityOnOpen(true);
			htmlReporter.config()
					.setTestViewChartLocation(ChartLocation.BOTTOM);
			htmlReporter.config().setTimeStampFormat(
					"EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

		}

		return report;
	}

}
