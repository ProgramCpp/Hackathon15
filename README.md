# Hacker earth hackathon for women, 2015

A fun productivity app for android to restrict android smart phone usage!
The app lets users to impose challenge against their friends to restrict an app usage to a particular duration.
The user who uses the app beyond the challenged duration loses. The user earns points for every challenge won. Earns trophies for every milestone achieved.

WorkFlow:<br/>
1. Challenge a friend with an app for a particular duration.<br/>
2. The challenge begins when the friend accepts the challenge.<br/>
3. The app usage on both the smart phones are monitered.<br/>
4. The winner is announced at the end of the time duration.<br/>

Technical Details:

1. An android app to interact with the users - to login, challenge, check stats and win.
2. An android service to monitor the app usage.
3. A broadcast receiver to receive notifications.
4. Google cloud Messaging for notifications.<br/>
<img src="https://hprog99.files.wordpress.com/2015/01/gcm1.png" width="200" height="175" /><br/>
5. 3rd party server in PHP [yet to be hosted] to track the challenges and send notifications.
6. MySQL to manage the users and challenges.
6. Google+ login to identify the users and friends (google+ circles)

src description:<br/>
Android client: /AndroidClient/gplus-test-android/<br/>
3rd party server: PHP GCM Server/GcmServer<br/>

ScreenShots: <a href= "https://www.dropbox.com/sh/wzzn2djkyqkh985/AAD8ddu48GKItB1CTb2hc0jMa?dl=0">DropBox</a><br/>
Presentation: <a href= "http://www.slideshare.net/secret/sbqTIFokpUvmGz"> SlideShare</a>

