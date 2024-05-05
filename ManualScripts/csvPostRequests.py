import csv
import requests

def send_post_requests():
    csv_file = 'books.csv'
    endpoint = 'http://localhost:8080/api/v1/book'

    with open(csv_file, mode='r', newline='', encoding='utf-8') as file:
        reader = csv.DictReader(file)

        for row in reader:
            payload = {
                'work_title': row['work_title'],
                'primary_author': row['primary_author'],
                'year_published': row['year_published'],
                'word_count': row['word_count']
            }

            response = requests.post(endpoint, json=payload)

            if response.status_code == 200:
                print(f"Successfully sent POST request for '{row['work_title']}'")
            else:
                print(f"Failed to send POST request for '{row['work_title']}'")

if __name__ == "__main__":
    send_post_requests()
