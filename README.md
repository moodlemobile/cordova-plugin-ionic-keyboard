# cordova-plugin-ionic-keyboard (fork)

This is a fork of `cordova-plugin-ionic-keyboard` by [Moodle HQ](https://moodle.com/). If you are looking for the documentation, you can read the original at [ionic-team/cordova-plugin-ionic-keyboard](hhttps://github.com/ionic-team/cordova-plugin-ionic-keyboard).

## Modifications from the original

We created this fork because we needed to include the following modifications in [our mobile application](https://github.com/moodlehq/moodleapp):

| PR | Description |
| -- | ----------- |
| [#883](https://github.com/ionic-team/cordova-plugin-ionic-keyboard/pull/181) | Fix ion-footer hidden behind keyboard on iOS |
| - | Port Android implementation from Capacitor plugin |
| - | Set a CSS variable with the height of the keyboard on iOS |

You can see all the changes here: [master...moodlemobile:master](https://github.com/ionic-team/cordova-plugin-ionic-keyboard/compare/master...moodlemobile:master)

## Installation

You can install this package using the [original installation instructions](https://github.com/ionic-team/cordova-plugin-ionic-keyboard#installation), but installing this package instead:

```sh
cordova plugin add @moodlehq/cordova-plugin-ionic-keyboard@2.2.0-moodle.1
```
