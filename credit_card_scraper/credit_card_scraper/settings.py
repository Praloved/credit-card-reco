USER_AGENT = 'my_scrapy_spider (+http://www.example.com)'
DOWNLOAD_DELAY = 1  # Avoid aggressive scraping
ROBOTSTXT_OBEY = True
FEED_EXPORT_ENCODING = 'utf-8'  # For correct character encoding

# Output format (optional if not using pipelines)
FEEDS = {
    'output.json': {
        'format': 'json',
        'encoding': 'utf8',
    },
}
