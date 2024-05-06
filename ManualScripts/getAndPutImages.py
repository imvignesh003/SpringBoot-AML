
import csv
import requests
import urllib.request
import base64

# Uploads all book covers from a CSV file
# TO-DO: write a script to put all existing entries in the DB into a CSV here

def convert_to_base64(image_path):
    with open(image_path, 'rb') as file:
        image_bytes = file.read()
        base64_bytes = base64.b64encode(image_bytes)
        return base64_bytes.decode('utf-8')

def upload_book_covers():
    csv_file = 'booksTemp1.csv'
    cover_endpoint = 'http://bookcover.longitood.com/bookcover'
    book_endpoint = 'http://localhost:8080/api/v1/book/byNameAndAuthor'
    image_endpoint_template = 'http://localhost:8080/api/v1/book/image/{}'

    with open(csv_file, mode='r', newline='', encoding='utf-8') as file:
        reader = csv.DictReader(file)

        for row in reader:
            book_title = row['work_title']
            author_name = row['primary_author']

            # Make a GET request to get book info
            book_params = {
                'work_title': book_title,
                'primary_author': author_name
            }
            book_info_response = requests.get(book_endpoint, params=book_params)

            if book_info_response.status_code == 200:
                book_info = book_info_response.json()
                picture_id = book_info.get('id')
                current_pic_id = book_info.get('picture')

                # Make a PUT request to upload the image
                if picture_id and not current_pic_id:

                    params = {
                        'book_title': book_title,
                        'author_name': author_name
                    }

                    # Fetch book cover URL
                    response = requests.get(cover_endpoint, params=params)

                    if response.status_code == 200:
                        data = response.json()
                        image_url = data.get('url')

                        if image_url:
                            # Download image
                            image_response = requests.get(image_url)

                            if image_response.status_code == 200:
                                image_bytes = image_response.content
                                base64_image = base64.b64encode(image_bytes).decode('utf-8')

                                image_endpoint = image_endpoint_template.format(picture_id)
                                payload = {'picture': base64_image}
                                upload_response = requests.put(image_endpoint, json=payload)

                                if upload_response.status_code == 200:
                                    print(f"Uploaded book cover for '{book_title}' by '{author_name}'")
                                else:
                                    print(f"Failed to upload book cover for '{book_title}' by '{author_name}'")
                            else:
                                print(f"Failed to download image for '{book_title}' by '{author_name}'")
                        else:
                            print(f"Failed to fetch book cover for '{book_title}' by '{author_name}'")
                    else:
                        print(f"No book cover found for '{book_title}' by '{author_name}'")
                else:
                    print(f"No picture ID found or already have picture for '{book_title}' by '{author_name}'")
            else:
                print(f"Failed to get book info for '{book_title}' by '{author_name}'")

if __name__ == "__main__":
    upload_book_covers()
