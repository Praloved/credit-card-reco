import scrapy
from credit_card_scraper.items import CreditCardItem
import uuid  # To generate the 'id' for each credit card


class CreditCardSpider(scrapy.Spider):
    name = "credit_card_scraper"

    # `start_urls` will be dynamically provided (based on user input)
    start_urls = [
        "https://www.hdfcbank.com/personal/pay/cards/credit-cards",
    ]

    def parse(self, response):
        # Loop through all credit card elements
        for card in response.css('div.card-container'):  # Change selector if needed
            item = CreditCardItem()

            # Generate a unique ID
            item['id'] = str(uuid.uuid4())

            # Extract data fields
            item['name'] = card.css('h5.card-title::text').get()
            item['parentCompany'] = "HDFC Bank"  # Hardcoded for this case
            item['suitableFor'] = card.css('ul.suitable-for li::text').getall()
            item['loungeBenefits'] = "lounge access" in card.css('div.benefits::text').get('').lower()
            item['extraBenefits'] = card.css('ul.extra-benefits li::text').getall()
            item['annualFee'] = float(card.css('span.annual-fee-value::text').re_first(r'\d+\.?\d*') or 0)
            item['premium'] = "premium" in card.css('div.card-type::text').get('').lower()
            item['interestRate'] = float(card.css('div.interest-rate span::text').re_first(r'\d+\.?\d*') or 0)
            item['foreignTransactionFee'] = float(
                card.css('span.foreign-transaction-fee::text').re_first(r'\d+\.?\d*') or 0)
            item['rewardExpiry'] = int(card.css('span.reward-expiry::text').re_first(r'\d+') or 0)
            item['fuelSurchargeWaiver'] = "fuel surcharge waiver" in card.css('div.surcharge::text').get('').lower()
            item['welcomeBonus'] = card.css('div.welcome-bonus::text').get()
            item['eligibilityCriteria'] = card.css('div.eligibility::text').get()
            item['issuedBy'] = "HDFC Bank"  # Hardcoded for this case
            item['maxCashbackLimit'] = float(card.css('span.max-cashback-limit::text').re_first(r'\d+\.?\d*') or 0)

            # Extract rewards (nested data)
            rewards = []
            for reward in card.css('ul.rewards li'):
                rewards.append({
                    'category': reward.css('span.category::text').get(),
                    'pointsPerSpend': float(reward.css('span.points-per-spend::text').re_first(r'\d+\.?\d*') or 0),
                    'spendAmount': float(reward.css('span.spend-amount::text').re_first(r'\d+\.?\d*') or 0),
                })
            item['rewards'] = rewards

            yield item
