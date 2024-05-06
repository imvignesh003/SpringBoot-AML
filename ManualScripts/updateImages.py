
import csv
import requests
import base64

# Uploads all book covers from a CSV file
# To use this, you need a CSV file in the same directory called 'existingBooksInTable.csv'

def convert_to_base64(image_path):
    with open(image_path, 'rb') as file:
        image_bytes = file.read()
        base64_bytes = base64.b64encode(image_bytes)
        return base64_bytes.decode('utf-8')

def upload_book_covers():
    csv_file = 'existingBooksInTable.csv'
    cover_endpoint = 'http://bookcover.longitood.com/bookcover'
    image_endpoint_template = 'http://localhost:8080/api/v1/book/image/{}'

    with open(csv_file, mode='r', newline='', encoding='utf-8') as file:
        reader = csv.DictReader(file)

        for row in reader:
            work_id = row['id']
            work_title = row['work_title']
            author_name = row['primary_author']
            picture_id = row['picture_id']

            # Make a PUT request to upload the image
            if work_id and not picture_id:

                params = {
                    'book_title': work_title,
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

                            image_endpoint = image_endpoint_template.format(work_id)
                            payload = {'picture': base64_image}
                            upload_response = requests.put(image_endpoint, json=payload)

                            if upload_response.status_code == 200:
                                print(f"Uploaded book cover for '{work_title}' by '{author_name}'")
                            else:
                                print(f"Failed to upload book cover for '{work_title}' by '{author_name}'")
                        else:
                            print(f"Failed to download image for '{work_title}' by '{author_name}'")
                    else:
                        print(f"Failed to fetch book cover for '{work_title}' by '{author_name}'")
                else:
                    print(f"No book cover found for '{work_title}' by '{author_name}'")
            else:
                print(f"No picture ID found or already have picture for '{work_title}' by '{author_name}'")
        else:
            print(f"Failed to get book info for '{work_title}' by '{author_name}'")

if __name__ == "__main__":
    upload_book_covers()
