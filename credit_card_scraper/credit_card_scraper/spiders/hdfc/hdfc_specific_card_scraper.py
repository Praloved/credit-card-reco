import scrapy

from credit_card_scraper.items import CreditCardItem


class CardDetailsSpider(scrapy.Spider):
    name = "CardDetailsSpider"
    card_names = ['moneyback-plus', 'regalia-gold-credit-card']
    start_urls = [f"https://www.hdfcbank.com/personal/pay/cards/credit-cards/{name}" for name in card_names]


    def parse(self, response):
        items = CreditCardItem()
        containers  = response.css('cardWrapper clearfix')
        card_name = response.css('.card-name::text').extract()
        rewards = response.css('.content-body:nth-child(13) .right-section').css('::text').extract()
        lounge_access = response.css('.content-body:nth-child(5) .right-section').css('::text').extract()
        milestone_benefits = response.css('.content-body:nth-child(6) p').css('::text').extract()

        items['rewards'] = rewards
        items['lounge_access'] = lounge_access
        items['milestone_benefits'] = milestone_benefits
        yield items
