package com.zt.map.rzimagemap.presenter;

import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.LinearUnit;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.zt.map.rzimagemap.contract.MainContract;

import cn.faker.repaymodel.mvp.BaseMVPPresenter;

public class MainPresenter extends BaseMVPPresenter<MainContract.View> implements MainContract.Presenter {

    @Override
    public void todistance(PointCollection mPointCollection) {
        if (mPointCollection.size() <= 1) {
            return;
        }
        Polyline polyline = new Polyline(mPointCollection);
        double distance = GeometryEngine.length(polyline);
        getView().setDistance(distance);
 /*       Point old_point;
        for (int i = 0; i < mPointCollection.size(); i++) {
            Point point = mPointCollection.get(i);
            if (i == 0) {
                old_point = point;
                continue;
            }


            old_point = point;
        }*/
    }

    @Override
    public void toArea(PointCollection mPointCollection) {
        if (mPointCollection.size() <= 2) {
            return;
        }
        Polygon polygon = new Polygon(mPointCollection);
        double distance = GeometryEngine.area(polygon);
        getView().setDistance(Math.abs(distance));
    }
}
