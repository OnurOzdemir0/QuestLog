# QuestLog 
Questlog is an online game journal, where you can track the games you play or review.
Main architecture is MVVM, DifUtils and Databinding can be found in the project.

We first tried API(https://api-docs.igdb.com/#getting-started), but we had difficulties on authorization of the token in multiple environments. Then we decided to get the information we needed outside of Kotlin (used Postman), then upload that JSON to the firestore, and use that to retrieve info.

## Features
**Games:** Games are retrieved from Firestore. Each game has a 
  - cover image_id, 
  - id, 
  - name, 
  - rating, 
  - screenshot image_id's, 
  - and a description.

**Playlists:** These are the game you add from **Games**

**Reviews:** You can enter reviews for each game in your playlist.

## Database
The story behind why we use Firestore is explained above. 
### Image Retrival: 
Images are retrieved by their image_id, which is a hashed string.
- Base URL is https://images.igdb.com/igdb/image/upload/t_{size}/{image_id}.jpg
- for covers I use t_cover_big
- for screenshots I use t_1080p
  
  Covers are portrait format so they are used in the playlist and Games lists. 
  Screenshots are landscape so they are used in reviews.
  Similar logic is found in Letterboxd, where the cover is portrait and the screenshots/fan artworks are landscape.
  so detailed page has screenshot as the primary image.
Glide is used to retrieve the image from the Url.

## Authentication
Since we decided to go through 2nd option(using firebase firestore), we didn't implemented the full authentication. However we still have a valid register and login which again validated through the firestore database.
There is a collection named "users", and its documents are used to check if such user exists. And the register uploads a new document with the given username and password.

## Design
In order to follow Material Design 3 Standards the Carousel (https://m3.material.io/components/carousel/overview) is implemented in Reviews section. Screenshots are items that can be seen as sliding horizontally. After the slide, it snaps the next element in the center.

