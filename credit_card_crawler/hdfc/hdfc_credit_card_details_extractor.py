import re

import requests
from bs4 import BeautifulSoup


def fetch_page(website_url):
    headers = {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"}
    response = requests.get(website_url, headers=headers)
    if response.status_code == 200:
        return response.text
    else:
        print(f"Failed to fetch page, status code: {response.status_code}")
    return None


class HDFCCreditCardScraper:
    def __init__(self):
        self.url = "https://www.hdfcbank.com/personal/pay/cards/credit-cards"

    @staticmethod
    def extract_cards_links(html):
        soup = BeautifulSoup(html, "html.parser")

        cards = []
        title_pattern = re.compile(r'know more', re.IGNORECASE)

        for card in soup.select('.cardparent'):
            button = card.find('div', class_='btnParent')
            if button is None:
                continue
            know_more_button = button.find('a', title=title_pattern)
            if know_more_button is None:
                know_more_button = button.find('a', class_='know-more-btn')
            if know_more_button is None:
                continue
            if know_more_button and 'href' in know_more_button.attrs:
                credit_card_url = know_more_button.attrs['href']
                print(credit_card_url)
                cards.append(credit_card_url)
        return cards

    @staticmethod
    def extract_cards_details(card_links):
        base_url = "https://www.hdfcbank.com"
        cards = []
        for card_link in card_links:
            card_specific_page_link = base_url + card_link
            print("processing " + card_specific_page_link)
            card_specific_page_link = card_specific_page_link.replace(
                "https://www.hdfcbank.com/personal/pay/cards/credit-cards",
                "https://www.hdfcbank.com/personal/pay/cards/credit-cards/credit-card-details"
            )
            base_credit_card_page = fetch_page(card_specific_page_link)
            if base_credit_card_page:
                soup = BeautifulSoup(base_credit_card_page, "html.parser")
                page_name = soup.find('h1', class_='page-name').string.strip()
                print(page_name)

                cards.append(page_name)
        return cards

    @staticmethod
    def extract_number(element):
        if element:
            text = element.get_text(strip=True).replace(",", "")
            match = re.search(r"\d+\.?\d*", text)
            return float(match.group()) if match else 0.0
        return 0.0

    def process(self):
        fetched_page = fetch_page(self.url)
        if fetched_page:
            card_links = self.extract_cards_links(fetched_page)
            print("fetched cards:" + str(len(card_links)))
            credit_cards = self.extract_cards_details(card_links)
            print(len(credit_cards))
        else:
            return []
