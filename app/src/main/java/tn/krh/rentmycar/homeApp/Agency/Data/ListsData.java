package tn.krh.rentmycar.homeApp.Agency.Data;


import java.util.ArrayList;

import tn.krh.rentmycar.R;
import tn.krh.rentmycar.model.SpinnerItem;

public class ListsData {

    public static ArrayList<SpinnerItem> modelList = new ArrayList<>();

    public static ArrayList<SpinnerItem> initList() {
        modelList.add(new SpinnerItem("Select Model"));
        return modelList;
    }

    public static ArrayList<SpinnerItem> volkswagenList() {
        modelList.add(new SpinnerItem("golf 7"));
        modelList.add(new SpinnerItem("passat"));
        modelList.add(new SpinnerItem("jetta"));
        return modelList;
    }
    public static ArrayList<SpinnerItem> bmwList() {
        modelList.add(new SpinnerItem("X1"));
        modelList.add(new SpinnerItem("X3"));
        modelList.add(new SpinnerItem("E92"));
        return modelList;
    }
    public static ArrayList<SpinnerItem> mercedesList() {
        modelList.add(new SpinnerItem("CLASS A"));
        modelList.add(new SpinnerItem("CLASS E"));
        modelList.add(new SpinnerItem("CLA"));
        return modelList;
    }
    public static ArrayList<SpinnerItem> fiatList() {
        modelList.add(new SpinnerItem("500"));
        return modelList;
    }
    public static ArrayList<SpinnerItem> hyundaiList() {
        modelList.add(new SpinnerItem("I 10"));
        modelList.add(new SpinnerItem("I 20"));
        modelList.add(new SpinnerItem("ACCENT"));
        return modelList;
    }
    public static ArrayList<SpinnerItem> audiList() {
        modelList.add(new SpinnerItem("A 4"));
        modelList.add(new SpinnerItem("A 3"));
        modelList.add(new SpinnerItem("A 6 "));
        return modelList;
    }
    public static ArrayList<SpinnerItem> kiaList() {
        modelList.add(new SpinnerItem("SPORTAGE"));
        modelList.add(new SpinnerItem("RIO"));
        modelList.add(new SpinnerItem("PICANTO"));
        return modelList;
    }
    public static ArrayList<SpinnerItem> renaultList() {
        modelList.add(new SpinnerItem("SYMBOL"));
        modelList.add(new SpinnerItem("CLIO"));
        modelList.add(new SpinnerItem("MEGANE"));
        return modelList;
    }

}
