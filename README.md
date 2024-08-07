# Android App User Guide

Welcome to our Android app! This guide provides an overview of what our app does and how it can enhance your experience.

## What This App Does

Our Android app is designed to provide you with an interactive travel experience. Here's what you can do:

1. **Route Exploration**:
   - View different routes and get detailed information about them.
   - Discover new places and plan your trips effectively.

2. **Personal Trip Recording**:
   - Record your own trips and create a personal travel log.
   - Keep track of your adventures and revisit your memories.

3. **Nearby Travelers**:
   - See other travelers around you.

4. **Real-time Hazard Alerts**:
   - Receive notifications about potential hazards near your location.
   - Stay informed and safe during your travels.

## How It Works

- The app communicates directly with our server to ensure you always have the most up-to-date information.
- When you perform any action in the app, it securely transmits the necessary information to our server.
- Our background service constantly monitors your location to provide timely hazard alerts.

## Benefits for You

- **Informed Travel**: Access comprehensive information about various routes and destinations.
- **Personal Tracking**: Keep a record of your own travels and experiences.
- **Traveler Awareness**: Know about other app users in your area.
- **Safety First**: Stay aware of potential hazards in your area with real-time alerts.

## Technical Overview

For those interested, our app is built using:
- Android Studio
- Direct communication with our Node.js and Express server
- Location-based services for hazard alerts and nearby traveler detection

## Troubleshooting: Bottom Navigation Issues

If you experience issues with the bottom navigation not working as expected, please follow these steps:

1. Open Logcat in Android Studio.
2. In the Logcat filter, add the following tag: `MyMainActivity`
3. Start clicking on the options in the bottom navigation.
4. Observe the numbers (item IDs) logged for each option.
5. Go to the `Constants` file in the project.
6. Update the `final int` values for each option to match the observed numbers.

This process will ensure that the bottom navigation IDs are correctly mapped and functioning properly.

## For Developers

If you're a developer looking for technical details about this Android application, please refer to our [Technical Documentation](TECHNICAL.md).