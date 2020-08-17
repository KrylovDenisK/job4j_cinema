package ru.job4j.store;

import ru.job4j.model.Place;

import java.util.List;

public interface Store {
    List<Place> findAllPlacesAll();
    List<Place> findAllPlaces();
    int findPrice(String place);
    void saveTicket(String userName, String phone, String place);
    int findPlaceID(String place);

}
