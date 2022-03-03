package ru.intervale.course.external.alfa_bank.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;
import java.math.BigDecimal;

/**
 * Курс валюты Национального банка Республики Беларусь
 */
public class NationalRate {

  private BigDecimal rate = null;

  private String iso = null;

  private Integer code = null;

  private Integer quantity = null;

  @JsonFormat(pattern = "dd.MM.yyyy")
  private LocalDate date = null;

  private String name = null;

  public NationalRate rate(BigDecimal rate) {
    this.rate = rate;
    return this;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }

  public NationalRate iso(String iso) {
    this.iso = iso;
    return this;
  }

  public String getIso() {
    return iso;
  }

  public void setIso(String iso) {
    this.iso = iso;
  }

  public NationalRate code(Integer code) {
    this.code = code;
    return this;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public NationalRate quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public NationalRate date(LocalDate date) {
    this.date = date;
    return this;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public NationalRate name(String name) {
    this.name = name;
    return this;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NationalRate nationalRate = (NationalRate) o;
    return Objects.equals(this.rate, nationalRate.rate) &&
        Objects.equals(this.iso, nationalRate.iso) &&
        Objects.equals(this.code, nationalRate.code) &&
        Objects.equals(this.quantity, nationalRate.quantity) &&
        Objects.equals(this.date, nationalRate.date) &&
        Objects.equals(this.name, nationalRate.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rate, iso, code, quantity, date, name);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NationalRate {\n");
    
    sb.append("    rate: ").append(toIndentedString(rate)).append("\n");
    sb.append("    iso: ").append(toIndentedString(iso)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

