from hdfc.hdfc_credit_card_details_extractor import HDFCCreditCardScraper

if __name__ == '__main__':
    hdfc = HDFCCreditCardScraper()
    hdfcCards = hdfc.process()
    print(hdfcCards)