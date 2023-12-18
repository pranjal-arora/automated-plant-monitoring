import requests
import config as cnfg

url= cnfg.API

try:
    response = requests.get(url)
    data = response.json()

    # Extracting sensor data
    feeds = data.get('feeds', [])
    for feed in feeds:
        created_at = feed.get('created_at')
        field1_value = feed.get('field1')
        field2_value = feed.get('field1')
        # Add more fields as needed

        print(f"Timestamp: {created_at}, Field1: {field1_value}, Field2: {field2_value}")
        # Print or process other fields as needed

except requests.RequestException as e:
    print(f"Error fetching data: {e}")
