package com.example.hike_with_me_client.Utils;

public class DateVerification {

    public static boolean isValidDate(String dateStr) {
        // Check if the date matches the dd/mm/yy format
        String datePattern = "^[0-3]\\d/[0-1]\\d/\\d{2}$";
        if (!dateStr.matches(datePattern)) {
            return false;
        }

        // Split the date string into day, month, and year
        String[] parts = dateStr.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]) + 2000; // Convert to four-digit year

        // Validate month
        if (month < 1 || month > 12) {
            return false;
        }

        // Validate day based on the month
        if (day < 1 || day > daysInMonth(month, year)) {
            return false;
        }

        return true;
    }

    private static int daysInMonth(int month, int year) {
        switch (month) {
            case 2:
                // Check for leap year
                return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    public static boolean isStartDateBeforeOrSameAsEndDate(String startDateStr, String endDateStr) {
        // Split the start date string into day, month, and year
        String[] startParts = startDateStr.split("/");
        int startDay = Integer.parseInt(startParts[0]);
        int startMonth = Integer.parseInt(startParts[1]);
        int startYear = Integer.parseInt(startParts[2]) + 2000;

        // Split the end date string into day, month, and year
        String[] endParts = endDateStr.split("/");
        int endDay = Integer.parseInt(endParts[0]);
        int endMonth = Integer.parseInt(endParts[1]);
        int endYear = Integer.parseInt(endParts[2]) + 2000;

        // Compare the dates
        if (startYear < endYear) {
            return true;
        } else if (startYear == endYear) {
            if (startMonth < endMonth) {
                return true;
            } else if (startMonth == endMonth) {
                return startDay <= endDay;
            }
        }
        return false;
    }
}
