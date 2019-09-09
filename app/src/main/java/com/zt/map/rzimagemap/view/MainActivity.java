package com.zt.map.rzimagemap.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.zt.map.rzimagemap.R;
import com.zt.map.rzimagemap.contract.MainContract;
import com.zt.map.rzimagemap.presenter.MainPresenter;

import cn.faker.repaymodel.mvp.BaseMVPAcivity;

public class MainActivity extends BaseMVPAcivity<MainContract.View, MainPresenter> implements MainContract.View, View.OnClickListener {
    private LinearLayout ll_cj;

    private LinearLayout bottom_table_distance;
    private TextView bottom_table_distance_value;
    private TextView bottom_table_modelname;
    private TextView bottom_tab_value_d;
    private TextView bottom_tab_close;
    private TextView bottom_tab_exit;

    private MapView mMapView;
    private DrawerLayout dl_ll;

    private LinearLayout right_ll_distance;//测距
    private LinearLayout right_ll_area;//测面积

    private GraphicsOverlay mGraphicsOverlay;
    private String wkt = "PROJCS[\"Gauss_Kruger\",GEOGCS[\"GCS_cgcs2000\",DATUM[\"D_cgcs2000\",SPHEROID[\"cgcs2000\",6378137.0,298.2572243275914]],PRIMEM[\"Greenwich\",0.0],UNIT[\"Degree\",0.0174532925199433]],PROJECTION[\"Transverse_Mercator\"],PARAMETER[\"false_easting\",500000.0],PARAMETER[\"false_northing\",0.0],PARAMETER[\"central_meridian\",114.0],PARAMETER[\"scale_factor\",1.0],PARAMETER[\"latitude_of_origin\",0.0],UNIT[\"Meter\",1.0]]";

    //点集合
//    private PointCollection mPointCollection = new PointCollection(SpatialReferences.getWebMercator());
    private PointCollection mPointCollection = new PointCollection(SpatialReference.create(wkt));

    private boolean isLine = false;//是否距离测量
    private boolean isArse = false;//是否面积测量

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initContentView() {
        isShowBackButton(false);
        isShowToolView(false);
        ll_cj = findViewById(R.id.ll_cj);
        mMapView = findViewById(R.id.mapView);
        dl_ll = findViewById(R.id.dl_ll);

        bottom_table_distance = findViewById(R.id.bottom_table_distance);
        bottom_table_modelname = findViewById(R.id.bottom_table_modelname);
        bottom_table_distance_value = findViewById(R.id.bottom_table_distance_value);
        bottom_tab_value_d = findViewById(R.id.bottom_tab_value_d);
        bottom_tab_close = findViewById(R.id.bottom_tab_close);
        bottom_tab_exit = findViewById(R.id.bottom_tab_exit);

        right_ll_distance = findViewById(R.id.right_ll_distance);
        right_ll_area = findViewById(R.id.right_ll_area);

       initMap();
    }

    private void initMap() {
   /*     ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 36.6685939000, 117.0836153100, 16);
        mMapView.setMap(map);*/

   /*     ArcGISTiledLayer arcGISTiledLayer = new ArcGISTiledLayer("http://192.168.1.13:6080/arcgis/rest/services/rizhaoMapService/MapServer");
        Basemap basemap = new Basemap(arcGISTiledLayer);
        ArcGISMap map = new ArcGISMap(basemap);
        mMapView.setMap(map);*/


        ArcGISMapImageLayer mapImageLayer = new ArcGISMapImageLayer("http://192.168.1.13:6080/arcgis/rest/services/rizhaoMapService/MapServer");
        ArcGISMap map = new ArcGISMap();
        map.getOperationalLayers().add(mapImageLayer);
        mMapView.setMap(map);

        mGraphicsOverlay = new GraphicsOverlay();
        mMapView.getGraphicsOverlays().add(mGraphicsOverlay);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initListener() {
        super.initListener();
        ll_cj.setOnClickListener(this);
        right_ll_distance.setOnClickListener(this);
        right_ll_area.setOnClickListener(this);
        bottom_tab_close.setOnClickListener(this);
        bottom_tab_exit.setOnClickListener(this);
        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (isLine) {
                    drawPoint(e);
                } else if (isArse) {
                    drawPolygon(e);
                }
                return super.onSingleTapConfirmed(e);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_cj: {
                dl_ll.openDrawer(Gravity.END);
                break;
            }
            case R.id.right_ll_distance: {
                dimissRight();
                showDistance();
                break;
            }
            case R.id.right_ll_area: {
                dimissRight();
                showArea();
                break;
            }
            case R.id.bottom_tab_close: {
                close();
                break;
            }
            case R.id.bottom_tab_exit: {
                cleanTab();
                break;
            }
        }
    }

    // TODO: 2019/9/5 目前只有测距和面积两个模式  多了后booleam状态控制是个问题
    private void showDistance() {
        isLine = true;
        isArse = false;
        bottom_table_distance.setVisibility(View.VISIBLE);
        bottom_table_modelname.setText("距离测量模式");
        bottom_table_distance_value.setText("0.00");
        bottom_tab_value_d.setText("米");
    }

    private void showArea() {
        isArse = true;
        isLine = false;
        bottom_table_distance.setVisibility(View.VISIBLE);
        bottom_table_modelname.setText("面积测量模式");
        bottom_table_distance_value.setText("0.00");
        bottom_tab_value_d.setText("平方米");
    }

    /**
     * 清零
     */
    private void close() {
        cleanMap();
        mGraphicsOverlay.getGraphics().clear();
        if (bottom_table_distance_value != null) {
            bottom_table_distance_value.setText("0.00");
        }
    }

    /**
     * 关闭标签
     */
    private void cleanTab() {
        cleanMap();
        isArse = false;
        isLine = false;
        bottom_table_distance.setVisibility(View.INVISIBLE);
    }

    private void dimissRight() {
        cleanMap();
        dl_ll.closeDrawer(Gravity.END);
    }

    private void cleanMap() {
        mGraphicsOverlay.getGraphics().clear();
        mPointCollection.clear();
    }

    private void drawPoint(MotionEvent e) {
        Point point = mMapView.screenToLocation(new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY())));
        point = (Point) GeometryEngine.project(point ,SpatialReference.create(wkt));
        mPointCollection.add(point);
        Polyline polyline = new Polyline(mPointCollection);
        //点
        SimpleMarkerSymbol simpleMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);
        Graphic pointGraphic = new Graphic(point, simpleMarkerSymbol);
        mGraphicsOverlay.getGraphics().add(pointGraphic);
        //线
        SimpleLineSymbol simpleLineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.parseColor("#FC8145"), 3);
        Graphic graphic = new Graphic(polyline, simpleLineSymbol);
        mGraphicsOverlay.getGraphics().add(graphic);
        mPresenter.todistance(mPointCollection);
    }


    private void drawPolygon(MotionEvent e) {
        mGraphicsOverlay.getGraphics().clear();
        Point point = mMapView.screenToLocation(new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY())));
        point = (Point) GeometryEngine.project(point ,SpatialReference.create(wkt));
        mPointCollection.add(point);

        for (Point p : mPointCollection) {
            SimpleMarkerSymbol simpleMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);
            Graphic pointGraphic = new Graphic(p, simpleMarkerSymbol);
            mGraphicsOverlay.getGraphics().add(pointGraphic);
        }
        Polygon polygon = new Polygon(mPointCollection);
        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.GREEN, 3.0f);
        SimpleFillSymbol simpleFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.parseColor("#33e97676"), lineSymbol);
        Graphic graphic = new Graphic(polygon, simpleFillSymbol);
        mGraphicsOverlay.getGraphics().add(graphic);
        mPresenter.toArea(mPointCollection);
    }

    @Override
    public void setDistance(double distance) {
        bottom_table_distance_value.setText(String.valueOf(distance));
    }

    @Override
    public void setArea(double distance) {
        bottom_table_distance_value.setText(String.valueOf(distance));
    }

    @Override
    protected void onPause() {
        mMapView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.dispose();
    }

}
