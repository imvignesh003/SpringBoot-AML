TO-DO List

BACKEND
- BUG: when there is an apostrophe in the book name or author name
- Resist the temptation to expand to films, documentaries, music, etc.
  - Expand the app using Book only
  - Watch SpringBoot tutorials
  - Move to mySQL?
  - Front-end?
- Look through CS489 notes for project planning/tips
  - Add these to your TO-DO list
- Add "credit", "description" columns (string) to Pictures table
- Create Excel file full of entries
  - then write a script that can send each row as a PUT request
  - or, if that's too hard, write a script that will convert the Excel file to JSON where the first row represents headers
- Create backup tables that you can manually copy to so you don't lose your data when editing the frontend anymore
  - Regularly run a command that deletes pictures that aren't referenced in Book
  - Don't worry, if anything ever happens you always have your scripts to re-run
  - https://stackoverflow.com/questions/3682866/how-to-create-a-backup-of-a-single-table-in-a-postgres-database
- Add column to table BOOK: Genres
  - Leaning towards storing these as a single string (list of genres)
  - Somewhere else (probably in Spring or React), I should have a list of allowed genres
    - In the future, we can add genres to books using POST requests
- Scraping Tool
  - Go through each book on the page and get info
  - Go through each page
  - Fix the word count and year published
    - Currently, the year published only has numbers of the date. This is a problem if a full date is provided (ex. April 25, 1797)
- Change scripts to check whether a book already has a picture
  - If it does, then don't add one
  - You'll need to add a GET request (boolean: hasPicture)
  - OR: use the current GET request and its pictureID property
    - is it null or not?

FRONT-END
- Deploy your endpoint to NGROK(?), then do the FreeCodeCamp frontend deployment method
  - https://ngrok.com/docs/http/
- In the frontend, move all api calls into an api utility function module
- Start further frontend learning here:
  - https://www.youtube.com/watch?v=bMknfKXIFA8 at minute 42
- Add options for you to specify the details of the book you'd like to add
  - ex. ranges for word count, year published
  - Design it like one of those Fifa Database searches (i.e., with searches and filters)
- Add sorting options on the table
- work_name -> work_title
- Pictures
  - Better Display
  - Add ability to upload an image (png or jpeg), convert it to a byte array, and api PUT
- Move interfaces into separate utils/interfaces functions


LONG-TERM
- Write Unit and Integration tests
- Configure with some of these: https://github.com/Adam1302/SpringBoot-AML/actions/new
- Accounts (Username and Password) + "My Books" Page
- CS489: Project Setup and Improvement Ideas
  - Notes
  - Always use for PRs: https://docs.github.com/en/issues/tracking-your-work-with-issues/linking-a-pull-request-to-an-issue
- GitHub action with SonarCloud (extra steps needed for Maven)
  - https://github.com/SonarSource/sonarcloud-github-action
  - https://community.sonarsource.com/t/github-actions-setup-for-maven-and-ts/46584

VISION
- I'd like this to be the centre for discussions and resources on Art, Media, Literature
  - Experts can write about topics, host discussions, etc.
    - Currently most "discussions" are scarce and out of sight, with many brilliant minds in low places, speaking to small audiences. This site can change that.
    - ex. Michael Sugrue Plato discussions
- Scheduled discussions, forums, pages for each work
- This site is awesome: https://book.io/book/beyond-good-and-evil/
  - Look at how they include AI-generated cover art based on the work -> very cool
- I found this site now which is basically the book version of my goal: https://openlibrary.org
  - I intend to try to start with something like this, but improve on it
    - Discussions groups, etc.

