package com.example.hw7;

/**
 * Created by skaul on 10/6/2016.
 */
public class Favorite {
    String city , state , date , temperature;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
                ", state='" + state + '\'' +
                ", date='" + date + '\'' +
                ", temperature='" + temperature + '\'' +
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
        if (this.state.equals(other.state) && this.city.equals(other.city)) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.city != null ? this.city.hashCode() : 0);
        hash = 53 * hash + (this.state != null ? this.state.hashCode() : 0);
        return hash;
    }
}
