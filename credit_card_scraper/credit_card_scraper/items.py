import scrapy


class CreditCardItem(scrapy.Item):
    id = scrapy.Field()
    name = scrapy.Field()
    parentCompany = scrapy.Field()
    suitableFor = scrapy.Field()
    loungeBenefits = scrapy.Field()
    extraBenefits = scrapy.Field()
    annualFee = scrapy.Field()
    premium = scrapy.Field()
    interestRate = scrapy.Field()
    foreignTransactionFee = scrapy.Field()
    rewardExpiry = scrapy.Field()
    fuelSurchargeWaiver = scrapy.Field()
    welcomeBonus = scrapy.Field()
    eligibilityCriteria = scrapy.Field()
    issuedBy = scrapy.Field()
    maxCashbackLimit = scrapy.Field()
    rewards = scrapy.Field()  # This will contain a list of dictionaries (one per reward)
