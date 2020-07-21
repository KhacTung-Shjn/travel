package com.example.mytravel.utils;

import com.example.mytravel.MainApp;
import com.example.mytravel.R;
import com.example.mytravel.models.city.PlaceHot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
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
        placeHot.put("timecreate", FieldValue.serverTimestamp());
        placeHot.put("namePlaceHot", "");


        MainApp.getInstance().getFirebaseFireStore()
                .collection("city")
                .document("Oad4kLBC4rxPyJUG2c1A")
                .collection("explore")
                .document("wIev48W97TnKkbt7yrnp")
                .collection("place")
                .document("XLTz58qMjSvvY3DgjHOx")
                .collection("placehot")
                .add(placeHot);
    }

    public static void addPlaceHot(PlaceHot ph) {
        HashMap<String, Object> placeHot = new HashMap<>();
        placeHot.put("idPlaceHot", ph.getIdPlaceHot());
        placeHot.put("timeOpen", ph.getTimeOpen());
        placeHot.put("desPlaceHot", ph.getDesPlaceHot());
        placeHot.put("lat", ph.getLat());
        placeHot.put("lng", ph.getLng());
        placeHot.put("isLovePlaceHot", ph.isLovePlaceHot());
        placeHot.put("urlImagePlaceHot", ph.getUrlImagePlaceHot());
        placeHot.put("timecreate", FieldValue.serverTimestamp());
        placeHot.put("namePlaceHot", ph.getNamePlaceHot());
        placeHot.put("idPlace", "XLTz58qMjSvvY3DgjHOx");


        MainApp.getInstance().getFirebaseFireStore()
                .collection("placehot")
                .document(ph.getIdPlaceHot())
                .set(placeHot);
    }


    public static void moveDataHotPlace() {
        MainApp.getInstance().getFirebaseFireStore()
                .collection("city")
                .document("Oad4kLBC4rxPyJUG2c1A")
                .collection("explore")
                .document("wIev48W97TnKkbt7yrnp")
                .collection("place")
                .document("XLTz58qMjSvvY3DgjHOx")
                .collection("placehot")
                .orderBy("timecreate", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {

                    if (queryDocumentSnapshots != null) {
                        ArrayList<PlaceHot> listPlaceHots = new ArrayList<>();
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            String data = new Gson().toJson(snapshot.getData());
                            if (data != null) {
                                PlaceHot placeHot = new Gson().fromJson(data, PlaceHot.class);
                                addPlaceHot(placeHot);
                            }
                        }
                    }
                });
    }


    public static void addImageHot() {

        HashMap<String, Object> image = new HashMap<>();
        image.put("idExplore", "wIev48W97TnKkbt7yrnp");
        image.put("idImagehot", "");
        image.put("urlImageHot", "https://img.thuthuatphanmem.vn/uploads/2018/09/26/anh-dep-chua-tran-quoc-ha-noi_053442220.jpg");


        MainApp.getInstance().getFirebaseFireStore()
                .collection("imagehot")
                .add(image);
    }
}
