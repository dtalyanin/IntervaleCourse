package ru.intervale.course.external.alfabank.model;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

/**
 * Список курсов валют Альфа-банка
 */
public class RateListResponse {
    private List<Rate> rates = null;

    public RateListResponse rates(List<Rate> rates) {
        this.rates = rates;
        return this;
    }

    public RateListResponse addRatesItem(Rate ratesItem) {
        if (this.rates == null) {
            this.rates = new ArrayList<>();
        }
        this.rates.add(ratesItem);
        return this;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RateListResponse rateListResponse = (RateListResponse) o;
        return Objects.equals(this.rates, rateListResponse.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rates);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RateListResponse {\n");

        sb.append("    rates: ").append(toIndentedString(rates)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}