TO-DO List

BACKEND
- "get" lists of authors, titles (MORE GET, PUT requests)
  - Add filters for:
    - Word Count Ranges (ex. word count less than X)
    - Year Ranges (ex. published between )
- Add "get" list of books by author functionality
  - Sort by: alphabetically, number of titles, popularity, reviews
  - Add "sort by" as another parameter
- Resist the temptation to expand to films, documentaries, music, etc.
  - Expand the app using Book only
  - Watch SpringBoot tutorials
  - Move to mySQL?
  - Front-end?
- Add pictures stored in DB (later, this seems more advanced)
- Remove specific "byAuthor", etc. endpoint paths
  - Instead, have it as another parameter, then make the queries according to that (logic in the service module)
- work_name -> work_title
- Fix the capitalization issues 
- Move TO-DO list to GitHub Issues
- Look through CS489 notes for project planning/tips
  - Add these to your TO-DO list

FRONT-END
- Deploy your endpoint to NGROK(?), then do the FreeCodeCamp frontend deployment method
  - https://ngrok.com/docs/http/
- In the frontend, move all api calls into an api utility function module
- Start further frontend learning here:
  - https://www.youtube.com/watch?v=bMknfKXIFA8 at minute 42
- Add options for you to specify the details of the book you'd like to add
  - ex. ranges for word count, year published
  - Design it like one of those Fifa Database searches