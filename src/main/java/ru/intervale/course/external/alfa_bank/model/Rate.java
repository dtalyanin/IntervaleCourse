package ru.intervale.course.external.alfa_bank.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;
import java.math.BigDecimal;


/**
 * Курс валюты Альфа-банка
 */

public class Rate {
  private BigDecimal sellRate = null;

  private String sellIso = null;

  private Integer sellCode = null;

  private BigDecimal buyRate = null;

  private String buyIso = null;

  private Integer buyCode = null;

  private Integer quantity = null;

  private String name = null;

  @JsonFormat(pattern = "dd.MM.yyyy")
  private LocalDate date = null;

  public Rate sellRate(BigDecimal sellRate) {
    this.sellRate = sellRate;
    return this;
  }

  public BigDecimal getSellRate() {
    return sellRate;
  }

  public void setSellRate(BigDecimal sellRate) {
    this.sellRate = sellRate;
  }

  public Rate sellIso(String sellIso) {
    this.sellIso = sellIso;
    return this;
  }

  public String getSellIso() {
    return sellIso;
  }

  public void setSellIso(String sellIso) {
    this.sellIso = sellIso;
  }

  public Rate sellCode(Integer sellCode) {
    this.sellCode = sellCode;
    return this;
  }

  public Integer getSellCode() {
    return sellCode;
  }

  public void setSellCode(Integer sellCode) {
    this.sellCode = sellCode;
  }

  public Rate buyRate(BigDecimal buyRate) {
    this.buyRate = buyRate;
    return this;
  }

  public BigDecimal getBuyRate() {
    return buyRate;
  }

  public void setBuyRate(BigDecimal buyRate) {
    this.buyRate = buyRate;
  }

  public Rate buyIso(String buyIso) {
    this.buyIso = buyIso;
    return this;
  }

  public String getBuyIso() {
    return buyIso;
  }

  public void setBuyIso(String buyIso) {
    this.buyIso = buyIso;
  }

  public Rate buyCode(Integer buyCode) {
    this.buyCode = buyCode;
    return this;
  }

  public Integer getBuyCode() {
    return buyCode;
  }

  public void setBuyCode(Integer buyCode) {
    this.buyCode = buyCode;
  }

  public Rate quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Rate name(String name) {
    this.name = name;
    return this;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Rate date(LocalDate date) {
    this.date = date;
    return this;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Rate rate = (Rate) o;
    return Objects.equals(this.sellRate, rate.sellRate) &&
        Objects.equals(this.sellIso, rate.sellIso) &&
        Objects.equals(this.sellCode, rate.sellCode) &&
        Objects.equals(this.buyRate, rate.buyRate) &&
        Objects.equals(this.buyIso, rate.buyIso) &&
        Objects.equals(this.buyCode, rate.buyCode) &&
        Objects.equals(this.quantity, rate.quantity) &&
        Objects.equals(this.name, rate.name) &&
        Objects.equals(this.date, rate.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sellRate, sellIso, sellCode, buyRate, buyIso, buyCode, quantity, name, date);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Rate {\n");
    
    sb.append("    sellRate: ").append(toIndentedString(sellRate)).append("\n");
    sb.append("    sellIso: ").append(toIndentedString(sellIso)).append("\n");
    sb.append("    sellCode: ").append(toIndentedString(sellCode)).append("\n");
    sb.append("    buyRate: ").append(toIndentedString(buyRate)).append("\n");
    sb.append("    buyIso: ").append(toIndentedString(buyIso)).append("\n");
    sb.append("    buyCode: ").append(toIndentedString(buyCode)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

