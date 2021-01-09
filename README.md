**MusicAdvisor**

Simple application advising music for one's account, made under the supervision of JetBrains Academy (https://hyperskill.org/projects/62?track=1).

While the application is running, you can type the following commands:
* `>featured` — a list of Spotify-featured playlists with their links fetched from API;
* `>new` — a list of new albums with artists and links on Spotify;
* `>categories` — a list of all available categories on Spotify (just their names);
* `>playlists` C_NAME, where C_NAME is the name of category. The list contains playlists of this category and their links on Spotify;
* `>next` - shows the next page,
* `>prev` - shows the previous page
* `>exit` - shuts down the application.

Before those you should provide acces for your appliaction. Type
`>auth`
then click the provided link and confirm.
The authorization is possible only for specified CLIENT_ID and CLIENT_SECRET (you can chcange those in class Authorization). You can get them by creating new app on Spotify for Developers.

Also, the application can be launched with three optional arguments:
* `-access` - server path (default: https://accounts.spotify.com)
* `-resource` - api server path (default: https://api.spotify.com)
* `-page` - elements of output to be displayed on one page (default: 5)


**Example of application input and output for default arguments:**
```
>auth
use this link to request the access code:
https://accounts.spotify.com/authorize?client_id=81872b06315f4b9eaff3ead664f3c376&redirect_uri=http://localhost:8080&response_type=code
waiting for code...
code received
<received code>
making http request for access token...
response:
<response body>
---SUCCESS---
>new
Hawái (Remix)
[Maluma, The Weeknd]
https://open.spotify.com/album/1Ag9EPbzibwzz0S0WpgX3v

From Dark to Light
[NLE Choppa]
https://open.spotify.com/album/1Dp8DJTwLzqxscHc6nHrio

Confetti
[Little Mix]
https://open.spotify.com/album/33a8Ha3pE7fo2o4T4xLabF

Emergency Tsunami
[NAV]
https://open.spotify.com/album/1tihFpzHHM9mDSoFbhMoZL

Happen To Me
[BENEE]
https://open.spotify.com/album/7BHTjC0GfwdDkEDDvniZj7

---PAGE 1 OF 4---
>next
Quarantine Thick (feat. Mulatto)
[2 Chainz, Mulatto]
https://open.spotify.com/album/6clmqh76lbdiSsZGVrhjIN

I Admit It (feat. 24kGoldn)
[ZHU]
https://open.spotify.com/album/2pHkZymG4rqjYfIP5GfeDO

Minefields
[Faouzia, John Legend]
https://open.spotify.com/album/6Pp5U1A6z6OogrtQEL5Q89

Words Of A Fool
[Barry Gibb]
https://open.spotify.com/album/3pv872VMTwdU86dz05wqh1

Take Me Home For Christmas
[Dan + Shay]
https://open.spotify.com/album/5nzOzmqaWGkrlfWfk9Jrfh

---PAGE 2 OF 4---
>categories
Top Lists
Pop
Hip Hop
Mood
At Home
---PAGE 1 OF 4---
>playlists Hip Hop
RapCaviar
https://open.spotify.com/playlist/37i9dQZF1DX0XUsuxWHRQd

Most Necessary
https://open.spotify.com/playlist/37i9dQZF1DX2RxBh64BHjQ

Gold School
https://open.spotify.com/playlist/37i9dQZF1DWVA1Gq4XHa6U

Signed XOXO
https://open.spotify.com/playlist/37i9dQZF1DX2A29LI7xHn1

Feelin' Myself
https://open.spotify.com/playlist/37i9dQZF1DX6GwdWRQMQpq

---PAGE 1 OF 2---
>prev
No more pages.
>exit

Process finished with exit code 0
```
