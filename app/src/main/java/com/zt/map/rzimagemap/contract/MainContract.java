package com.zt.map.rzimagemap.contract;

import com.esri.arcgisruntime.geometry.PointCollection;

public class MainContract {
    public interface View {
        void setDistance(double distance);
        void setArea(double distance);
    }

    public interface Presenter {
        void todistance(PointCollection mPointCollection);
        void toArea(PointCollection mPointCollection);
    }

    public interface Model {

    }
}
