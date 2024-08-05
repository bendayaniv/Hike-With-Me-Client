package com.example.hike_with_me_client.Models.Trip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;

import com.example.hike_with_me_client.Utils.Singleton.CurrentUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TripMethodsUtils {

    static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @SuppressLint("Range")
    static String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static void initiateActiveTrips(List<trip> trips) {
        List<trip> activeTrips = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        for (trip trip : trips) {

            LocalDate startDate = TripMethodsUtils.parseDate(trip.getStartDate());
            LocalDate endDate = TripMethodsUtils.parseDate(trip.getEndDate());

            if (startDate != null && endDate != null) {
                if ((currentDate.isAfter(startDate) || currentDate.isEqual(startDate)) && (currentDate.isBefore(endDate) || currentDate.isEqual(endDate))) {
                    activeTrips.add(trip);
                }
            } else {
                Log.d("TripMethodsUtils", "Error: Invalid date format for start date or end date for " + trip.getId());
            }

        }

        CurrentUser.getInstance().setActiveTrips(activeTrips);
    }

    public static LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                        // Day, Month, Year variations
                        "[d.M.yyyy][dd.MM.yyyy][dd.M.yyyy][d.MM.yyyy]" +
                                "[d/M/yyyy][dd/MM/yyyy][dd/M/yyyy][d/MM/yyyy]" +
                                "[d-M-yyyy][dd-MM-yyyy][dd-M-yyyy][d-MM-yyyy]" +
                                "[d M yyyy][dd MM yyyy][dd M yyyy][d MM yyyy]" +

                                // Month, Day, Year variations
                                "[M.d.yyyy][MM.dd.yyyy][M.dd.yyyy][MM.d.yyyy]" +
                                "[M/d/yyyy][MM/dd/yyyy][M/dd/yyyy][MM/d/yyyy]" +
                                "[M-d-yyyy][MM-dd-yyyy][M-dd-yyyy][MM-d-yyyy]" +
                                "[M d yyyy][MM dd yyyy][M dd yyyy][MM d yyyy]" +

                                // Year, Month, Day variations
                                "[yyyy.M.d][yyyy.MM.dd][yyyy.M.dd][yyyy.MM.d]" +
                                "[yyyy/M/d][yyyy/MM/dd][yyyy/M/dd][yyyy/MM/d]" +
                                "[yyyy-M-d][yyyy-MM-dd][yyyy-M-dd][yyyy-MM-d]" +
                                "[yyyy M d][yyyy MM dd][yyyy M dd][yyyy MM d]" +

                                // Year, Day, Month variations
                                "[yyyy.d.M][yyyy.dd.MM][yyyy.dd.M][yyyy.d.MM]" +
                                "[yyyy.d/M][yyyy.dd/MM][yyyy.dd/M][yyyy.d/MM]" +
                                "[yyyy.d-M][yyyy.dd-MM][yyyy.dd-M][yyyy.d-MM]" +
                                "[yyyy.d M][yyyy.dd MM][yyyy.dd M][yyyy.d MM]" +

                                // Including month names
                                "[d MMM yyyy][dd MMM yyyy][d MMMM yyyy][dd MMMM yyyy]" +
                                "[MMM d yyyy][MMM dd yyyy][MMMM d yyyy][MMMM dd yyyy]" +

                                // Two-digit year formats
                                "[d.M.yy][dd.MM.yy][d/M/yy][dd/MM/yy][d-M-yy][dd-MM-yy]" +
                                "[M.d.yy][MM.dd.yy][M/d/yy][MM/dd/yy][M-d-yy][MM-dd-yy]" +
                                "[yy.M.d][yy.MM.dd][yy/M/d][yy/MM/dd][yy-M-d][yy-MM-dd]" +

                                // Time included
                                "[d.M.yyyy HH:mm][dd.MM.yyyy HH:mm:ss][yyyy-MM-dd'T'HH:mm:ss]" +
                                "[M/d/yyyy h:mm a][MM/dd/yyyy hh:mm:ss a][yyyy-MM-dd'T'HH:mm:ssXXX]" +

                                // Ordinal day formats
                                "[d'st' MMMM yyyy][d'nd' MMMM yyyy][d'rd' MMMM yyyy][d'th' MMMM yyyy]" +

                                // Week-based formats
                                "[xxxx-'W'ww-e][xxxx-'W'ww]" +

                                // Fiscal year formats
                                "[yyyy/yyyy][yyyy-yyyy]"
                )
                .withLocale(Locale.getDefault());
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
