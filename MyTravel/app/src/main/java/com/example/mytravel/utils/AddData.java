package com.example.mytravel.utils;

import com.example.mytravel.MainApp;

import java.util.HashMap;

public class AddData {

    public static void addTour() {
        HashMap<String, Object> tour = new HashMap<>();
        tour.put("end_date", "");
        tour.put("schedule", "White Clothes is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\\u0027s stan");
        tour.put("money", "100");
        tour.put("nameTour", "Sầm Sơn Tour");
        tour.put("id_tour", "");
        tour.put("time", "3 days");
        tour.put("image_tour", "https://tour.dulichvietnam.com.vn/uploads/tour/thm_bien-sam-son-thanh-hoa.jpg.jpg");
        tour.put("is_love", true);
        tour.put("introduction", "White Clothes is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\\u0027s stan");
        tour.put("rate_tour", 3);
        tour.put("start_date", "");
        tour.put("traffic", "White Clothes is simply dummy text of the printing and typesetting industry");

        MainApp.getInstance().getFirebaseFireStore()
                .collection("city")
                .document("Oad4kLBC4rxPyJUG2c1A")
                .collection("explore")
                .document("wIev48W97TnKkbt7yrnp")
                .collection("tour")
                .add(tour);
    }


    public static void addPlace() {
        HashMap<String, Object> place = new HashMap<>();
        place.put("idPlace", "");
        place.put("namePlace", "Test");
        place.put("ratePlace", 3);
        place.put("lat", "");
        place.put("lng", "");
        place.put("isLove", true);
        place.put("urlImagePlace", "https://storage.qoo-app.com/game/7962/g9BjpeOBrTGUzwYfZtlaTRw8T0Ft24jJ.jpg");

        MainApp.getInstance().getFirebaseFireStore()
                .collection("city")
                .document("Oad4kLBC4rxPyJUG2c1A")
                .collection("explore")
                .document("wIev48W97TnKkbt7yrnp")
                .collection("place")
                .add(place);
    }

    public static void addPlaceHot() {
        HashMap<String, Object> placeHot = new HashMap<>();
        placeHot.put("idPlaceHot", "");
        placeHot.put("timeOpen", "");
        placeHot.put("desPlaceHot", "");
        placeHot.put("lat", "");
        placeHot.put("lng", "");
        placeHot.put("isLovePlaceHot", true);
        placeHot.put("urlImagePlaceHot", "");


        MainApp.getInstance().getFirebaseFireStore()
                .collection("city")
                .document("Oad4kLBC4rxPyJUG2c1A")
                .collection("explore")
                .document("wIev48W97TnKkbt7yrnp")
                .collection("place")
                .document("JBdbhAvtKmxBFFMQ0IJk")
                .collection("placehot")
                .add(placeHot);
    }
}
