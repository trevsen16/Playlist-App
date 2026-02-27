# My Personal Project

## Song Playlist

The Playlist application will store different songs
of the users input and store them onto a playlist. Each song will have 
its name, artist, genre and length of song. The user can 
input each time they listen to a song and a statistical visualization 
could show their top song and how much they've listened to it. 

The application could be used for someone who wants to track their music, 
artists, songs and genres and see what type of music they are most
attracted to. I am interested in this project because music is a big part 
of my life and this application will be useful to track the songs I listen to
and all the statistics on my top song.

***User Stories* list:**
- As a user, I want to be able to add a song to the playlist.
- As a user, I want to be able to view the playlist full of songs.
- As a user, I want to be able to see my most listened to song.
- As a user, I want to be able to delete a song from my playlist.
- As a user, I want to be able to track a song I listened to.
- As a user, I want to have the option to save my playlist to a file.
- As a user, I want to have the option to load my playlist from a file.

# Instructions for Grader

- You can generate the first required action related to the user story by inputing the required information into 
the textboxes aligned with the add Song button and then click the add song button.
- You can generate the second required action related to the user story by typing the song name that you want to delete
and clicking the delete button.
- You can locate my visual component by starting the program and it will be on the left.
- You can save the state of my application by clicking the save button at the bottom of the interface.
- You can reload the state of my application by clicking the load button at the bottom of the interface.

# Phase 4: Task 2

Thu Nov 30 19:00:25 PST 2023
Added Hotel California to the playlist


Thu Nov 30 19:00:45 PST 2023
Added Back in Black to the playlist


Thu Nov 30 19:01:10 PST 2023
Removed Hotel California from the playlist


Thu Nov 30 19:01:14 PST 2023
Data saved to file


Thu Nov 30 19:01:17 PST 2023
Data loaded to file


Thu Nov 30 19:01:17 PST 2023
Added Back in Black to the playlist

# Phase 4: Task  3

In my project if I had more time I would refactor my code to be able to catch errors better. Currently, you can add 
multiple of the same song to the playlist and I could throw an exception to eliminate that. In addition, I could
throw an exception if one of the elements of the song is empty and not allow it to be added to the playlist. I would 
want to implement better error handling to create a more suitable and realistic add song method. I want 
each song to be a valid song with all the elements included and no duplicate songs.