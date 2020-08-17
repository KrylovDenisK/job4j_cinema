package ru.job4j.model;

import java.util.Objects;

public class Place {
    private int row;
    private int number;
    private int price;
    private boolean status;


    public Place(int row, int number) {
        this.row = row;
        this.number = number;
    }

    public Place(int row, int number, int price, boolean status) {
        this.row = row;
        this.number = number;
        this.price = price;
        this.status = status;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Place place = (Place) o;
        return row == place.row
                && number == place.number
                && price == place.price;
    }

    @Override
    public String toString() {
        return String.valueOf(row) + number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, number, price);
    }
}
