package com.example.dividendproject.scraper;


import com.example.dividendproject.domain.constant.Month;
import com.example.dividendproject.dto.Company;
import com.example.dividendproject.dto.Dividend;
import com.example.dividendproject.dto.ScrapedResult;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Component
public class YahooFinancialScraper implements Scraper{

    private static final String STATICS_URL = "https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&interval=1mo";
    private static final String SUMMARY_URL = "https://finance.yahoo.com/quote/%s?p=%s";
    private static final long START_TIME = 86400; // 60 * 60 * 24

    @Override
    public ScrapedResult scrap(Company company) { // 회사 정보(Company) 에 대한 Scrap 정보 가져오는 메소드

        var scrapedResult = new ScrapedResult();
        scrapedResult.setCompany(company);
        try {

            long now = System.currentTimeMillis() / 1000;
            String url = String.format(STATICS_URL, company.getTicker(), START_TIME, now);

            Connection con
                    = Jsoup.connect(url);

            Document doc = con.get();

            Elements parsingDivs = doc.getElementsByAttributeValue("data-test", "historical-prices");
            Element tableEle = parsingDivs.get(0);

            Element tbody = tableEle.children().get(1);
            List<Dividend> dividends = new ArrayList<>();

            for (Element e : tbody.children()) {
                String txt = e.text();
                if (!txt.endsWith("Dividend")) {
                    continue;
                }

                String[] splits = txt.split(" ");
                int month = Month.strToNumber(splits[0]);
                int day = Integer.parseInt(splits[1].replace(",", ""));
                int year = Integer.parseInt(splits[2]);
                String dividend = splits[3];

                if (month < 0) {
                    throw new RuntimeException("Unexpected Month enum Value -> " + splits[0]);
                }

                Dividend.builder()
                        .date(LocalDateTime.of(year, month, day, 0, 0))
                        .dividend(dividend)
                        .build();
            }
            scrapedResult.setDividendEntities(dividends);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return scrapedResult;
    }

    @Override
    public Company scrapCompanyByTicker(String ticker) { // ticker 에 대한 회사 정보(Company) 가져오기

        String url = String.format(SUMMARY_URL, ticker, ticker);

        try {
            Document document = Jsoup.connect(url).get();
            Element titleEle = document.getElementsByTag("h1").get(0);
            String title = titleEle.text().trim();

            return Company.builder()
                    .ticker(ticker)
                    .name(title)
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
