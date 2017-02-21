/*
* HW6
* Group34
* Sonal Kaulkar, Siddhant Jain
* */
package com.example.hw6;

/**
 * Created by skaul on 10/6/2016.
 */
public class Favorite {
    String city , country , date , temperature;
    int favorite ;

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", date='" + date + '\'' +
                ", temperature='" + temperature + '\'' +
                ", favorite=" + favorite +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Favorite.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Favorite other = (Favorite) obj;
        if (this.country.equals(other.country) && this.city.equals(other.city)) {

            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.city != null ? this.city.hashCode() : 0);
        hash = 53 * hash + (this.country != null ? this.country.hashCode() : 0);
        return hash;
    }
}
