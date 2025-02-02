from scrapy.crawler import CrawlerProcess
from credit_card_scraper.spiders.credit_card_scraper import CreditCardSpider

if __name__ == "__main__":
    process = CrawlerProcess()
    process.crawl(CreditCardSpider)
    process.start()
