import requests
from bs4 import BeautifulSoup
import re
import logging
import time
from API.kernel.search import DBRecordSetParams, ColumnDescriptor
from jarray import array

logging.basicConfig(level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s")
logger = logging.getLogger(__name__)


def load(session):
    desc = ColumnDescriptor("Ссылка на сайт с ценой")
    params = DBRecordSetParams(None, array([desc], ColumnDescriptor))
    print(params)


class SiteParser:

    def __init__(self, timeout=10, max_retries=3, headers=None):
        self.timeout = timeout
        self.max_retries = max_retries
        self.headers = headers or {
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36",
            "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
            "Accept-Language": "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7"
        }
        self.session = requests.Session()
        self.session.headers.update(self.headers)

    def get_sites(self):
        return [
            {
                "url": "https://bianit.ru/podshipniki/sharikovyie/upornyie/nsk-51108",
                "selector": r"span[data-price].product__price"
            }
        ]

    def parse_site(self, url):
        for attempt in range(self.max_retries):
            try:
                response = self.session.get(url, timeout=self.timeout)
                response.raise_for_status()
                return response.text if isinstance(response.text, str) else response.content.decode('utf-8',
                                                                                                    errors='ignore')
            except requests.RequestException as e:
                logger.warning("[Attempt {}/{}] Ошибка загрузки {}: {}".format(
                    attempt + 1, self.max_retries, url, e
                ))
                if attempt < self.max_retries - 1:
                    time.sleep(2 ** attempt)
        raise requests.exceptions.ConnectionError("Не удалось загрузить {} после {} попыток".format(
            url, self.max_retries
        ))

    def get_price(self, url, price_selector):
        html = self.parse_site(url)
        soup = BeautifulSoup(html, "html.parser")

        elem = soup.select_one(price_selector)
        raw_price = elem.get_text(strip=True) if elem else None

        if not raw_price:
            logger.warning("Цена не найдена по селектору: {} ({})".format(price_selector, url))
            return None

        return raw_price

    @staticmethod
    def _clean_price(price_str):
        if not price_str:
            return None
        cleaned = re.sub(r"[^\d.,]", "", str(price_str))
        if "," in cleaned and "." not in cleaned:
            cleaned = cleaned.replace(",", ".")
        try:
            price = float(cleaned)
            return price if price > 0 else None
        except (ValueError, TypeError):
            logger.warning("Не удалось преобразовать цену: '{}'".format(price_str))
            return None


if __name__ == "__main__":
    parser = SiteParser(timeout=10, max_retries=3)
    tasks = parser.get_sites()

    for task in tasks:
        url = task["url"]
        selector = task["selector"]

        print("Parse: {}".format(url))
        price = parser.get_price(url, selector)

        if price:
            print("Price was found: {}".format(price[:-2]))
        else:
            print("Price wasn't found")
        print("-" * 50)