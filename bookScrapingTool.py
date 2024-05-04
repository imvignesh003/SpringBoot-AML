
import requests
import re
from bs4 import BeautifulSoup

def scrape_books():

    url = "https://book.io/books/?order=ASC&orderby=published-date"
    bookListResponse = requests.get(url)

    if bookListResponse.status_code == 200:

        mainPageSoup = BeautifulSoup(bookListResponse.content, 'html.parser')
        mainPageContent = mainPageSoup.find("div", class_="book-grid-outer")
        all_books_on_page = mainPageContent.find_all("a", class_="book-grid-title wc-block-grid__product-title")

        all_children_books = mainPageSoup.find_all("div", class_="book-grid-block")

        for bookItem in all_children_books:

            bookItemImage = bookItem.find("div", class_="book-grid-image")

            bookUrl = bookItemImage.find("a", href=True)['href']

            individualBookResponse = requests.get(bookUrl)

            if individualBookResponse.status_code == 200:

                soup = BeautifulSoup(individualBookResponse.content, 'html.parser')
                singleBookContent = soup.find("section", class_="single-book-content")

                work_name = ''
                pub_year = ''
                word_count = ''
                author_name = ''
                work_name = singleBookContent.find("h1").text.strip()

                details = soup.find("h2", string="Details").parent

                for item in details.find_all("p"):
                    if "First Publication Date : " in item.text:
                        pub_year = re.sub("[^0-9]", "", item.text)

                    if "Word Count" in item.text:
                        word_count = re.sub("[^0-9]", "", item.text)

                for item in details.find_all("span"):
                    if "Author : " in item.text:
                        author_name = item.find_next_sibling().text

                print("Work Name: ", work_name)
                print("Author: ", author_name)
                print("Year: ", pub_year)
                print("Word Count: ", word_count)

            else:
                print("Failed to retrieve the webpage.")
    else:
        print("Failed to retrieve the webpage.")

if __name__ == "__main__":
    scrape_books()
