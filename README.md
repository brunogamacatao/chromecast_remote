# Chromecast System Tray Remote Control

![screenshot](https://github.com/brunogamacatao/chromecast_remote/blob/master/screenshots/shot.png?raw=true)

This is a simple toy project used to quickly control a chromecast dongle via system tray icons (play, pause, stop).

The software was created because sometimes it is very frustrating pause or resume a video via an IOS mobile app (eg. YouTube or Netflix). Sometimes it takes a lot to reopen the app and wait until the app connects to Chromecast to, finally, being able to control it.

Future improvements:
- Add more buttons (fast forward and rewind);
- Implement Arduino support (to control Chromecast via an IR remote control).

Please, feel free to fork this repo, propose changes or tweaks.

## Building and Running

The chromecast system tray remote control (need to think a better name) is built using gradle, so to build just type:

`gradle compile`

To execute, type:

`gradle execute`

Just in case, you are using OSX, you can also package the chromecast... as a Mac App by typing:

`gradle macAppBundle` 
