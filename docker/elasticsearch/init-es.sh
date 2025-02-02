#!/bin/sh

echo "Waiting for Elasticsearch to be available..."
until curl -s -o /dev/null "http://elasticsearch:9200/_cluster/health?wait_for_status=yellow"; do
  echo "Elasticsearch is not ready yet. Waiting..."
  sleep 5
done

echo "Checking if credit-cards.json exists..."
if [ ! -f "/mappings/credit-cards.json" ]; then
  echo "Error: mappings.json file not found!"
  exit 1
fi

echo "Creating credit_cards index..."
curl -X PUT "http://elasticsearch:9200/credit_cards" -H "Content-Type: application/json" -d @/mappings/credit-cards.json
echo "✅ Index created successfully!"

# ✅ Insert Sample Data
if [ -f "/sample_data/credit_cards_data.json" ]; then
    echo "Inserting credit card records..."

    while IFS= read -r line
    do
      curl -X POST "http://elasticsearch:9200/credit_cards/_doc/" -H "Content-Type: application/json" -d "$line"
      echo "Inserted: $line"
    done < /sample_data/credit_cards_data.json

    echo "✅ Sample records inserted successfully!"
else
    echo "Error: Sample data file not found!"
fi
