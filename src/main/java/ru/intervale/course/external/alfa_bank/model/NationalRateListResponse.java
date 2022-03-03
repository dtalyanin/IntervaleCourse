package ru.intervale.course.external.alfa_bank.model;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

/**
 * Выходная модель, содержащая курсы валют Национального банка Республики Беларусь
 */
public class NationalRateListResponse {
  private List<NationalRate> rates = null;

  public NationalRateListResponse rates(List<NationalRate> rates) {
    this.rates = rates;
    return this;
  }

  public NationalRateListResponse addRatesItem(NationalRate ratesItem) {
    if (this.rates == null) {
      this.rates = new ArrayList<NationalRate>();
    }
    this.rates.add(ratesItem);
    return this;
  }

  public List<NationalRate> getRates() {
    return rates;
  }

  public void setRates(List<NationalRate> rates) {
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
    NationalRateListResponse nationalRateListResponse = (NationalRateListResponse) o;
    return Objects.equals(this.rates, nationalRateListResponse.rates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rates);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NationalRateListResponse {\n");
    
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

