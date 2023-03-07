package com.example.dividendproject.scraper;

import com.example.dividendproject.dto.Company;
import com.example.dividendproject.dto.ScrapedResult;

public interface Scraper {

    Company scrapCompanyByTicker(String ticker);

    ScrapedResult scrap(Company company);
}
