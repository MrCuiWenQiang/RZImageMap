package com.zt.map.rzimagemap.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
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
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.zt.map.rzimagemap.R;
import com.zt.map.rzimagemap.contract.MainContract;
import com.zt.map.rzimagemap.presenter.MainPresenter;
import com.zt.map.rzimagemap.util.MapUtil;
import com.zt.map.rzimagemap.view.widgh.dialog.BitmapDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import cn.faker.repaymodel.mvp.BaseMVPAcivity;
import cn.faker.repaymodel.util.LocImageUtility;
import cn.faker.repaymodel.util.LogUtil;
import cn.faker.repaymodel.util.ToastUtility;
import cn.faker.repaymodel.util.permission.PermissionUtility;

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

    private LinearLayout open_fd;//放大
    private LinearLayout open_sx;//缩小
    private LinearLayout open_xz;//旋转

    private LinearLayout right_ll_distance;//测距
    private LinearLayout right_ll_area;//测面积

    private final int right_ll_marker = 1;//画点
    private final int right_ll_line = 2;//画线
    private final int right_ll_mian = 3;//画面
    private final int right_ll_rou = 4;//画圆
    private final int right_ll_tuya = 5;//涂鸦
    private final int right_ll_clean = 6;//清除
    private final int right_ll_out = 7;//导出
    private final int right_ll_distance_key = 8;//测距
    private final int right_ll_area_key = 9;//测面积


    private GraphicsOverlay mGraphicsOverlay;
    private String wkt = "PROJCS[\"Gauss_Kruger\",GEOGCS[\"GCS_cgcs2000\",DATUM[\"D_cgcs2000\",SPHEROID[\"cgcs2000\",6378137.0,298.2572243275914]],PRIMEM[\"Greenwich\",0.0],UNIT[\"Degree\",0.0174532925199433]],PROJECTION[\"Transverse_Mercator\"],PARAMETER[\"false_easting\",500000.0],PARAMETER[\"false_northing\",0.0],PARAMETER[\"central_meridian\",114.0],PARAMETER[\"scale_factor\",1.0],PARAMETER[\"latitude_of_origin\",0.0],UNIT[\"Meter\",1.0]]";

    //点集合
//    private PointCollection mPointCollection = new PointCollection(SpatialReferences.getWebMercator());
    private PointCollection mPointCollection = new PointCollection(SpatialReference.create(wkt));

    private Map<Integer, Boolean> selectStatus = new HashMap<>();


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


        open_fd = findViewById(R.id.open_fd);
        open_sx = findViewById(R.id.open_sx);
        open_xz = findViewById(R.id.open_xz);

        right_ll_distance = findViewById(R.id.right_ll_distance);
        right_ll_area = findViewById(R.id.right_ll_area);

        initMap();
    }

    private void initMap() {
/*        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 36.6685939000, 117.0836153100, 16);
        mMapView.setMap(map);*/

   /*     ArcGISTiledLayer arcGISTiledLayer = new ArcGISTiledLayer("http://192.168.1.13:6080/arcgis/rest/services/rizhaoMapService/MapServer");
        Basemap basemap = new Basemap(arcGISTiledLayer);
        ArcGISMap map = new ArcGISMap(basemap);
        mMapView.setMap(map);*/

//        ArcGISMapImageLayer mapImageLayer = new ArcGISMapImageLayer("http://192.168.1.13:6080/arcgis/rest/services/rizhaoMapService/MapServer");
//        ArcGISMapImageLayer mapImageLayer = new ArcGISMapImageLayer("http://192.168.111.253:6080/arcgis/rest/services/my96/MapServer");
        ArcGISMapImageLayer mapImageLayer = new ArcGISMapImageLayer("http://192.168.111.253:6080/arcgis/rest/services/My98/MapServer");
        ArcGISMap map = new ArcGISMap();
        map.getOperationalLayers().add(mapImageLayer);
        mMapView.setMap(map);

        mGraphicsOverlay = new GraphicsOverlay();
        mMapView.getGraphicsOverlays().add(mGraphicsOverlay);

        mMapView.setAttributionTextVisible(false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initSelectStatus(-1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtility.writeExternalStorage(this);
        }
    }

    //初始化选择器
    private void initSelectStatus(int view) {
        selectStatus.put(right_ll_distance_key, false);
        selectStatus.put(right_ll_area_key, false);
        selectStatus.put(right_ll_marker, false);
        selectStatus.put(right_ll_line, false);
        selectStatus.put(right_ll_mian, false);
        selectStatus.put(right_ll_rou, false);
        selectStatus.put(right_ll_tuya, false);
        selectStatus.put(right_ll_clean, false);
        selectStatus.put(right_ll_out, false);
        if (view !=-1 && selectStatus.containsKey(view)) {
            selectStatus.put(view, true);
        }
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



        open_fd.setOnClickListener(this);
        open_sx.setOnClickListener(this);
        open_xz.setOnClickListener(this);

//        mMapView.onTouchEvent();
        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (selectStatus.get(right_ll_distance_key)) {
                    drawPoint(e, true);
                } else if (selectStatus.get(right_ll_area_key)) {
                    drawPolygon(e, true);
                } else if (selectStatus.get(right_ll_marker)) {
                    drawMarker(e);
                } else if (selectStatus.get(right_ll_line)) {
                    drawPoint(e, false);
                } else if (selectStatus.get(right_ll_mian)) {
                    drawPolygon(e, false);
                } else if (selectStatus.get(right_ll_rou)) {

                    drawCircle(e);

                }
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onTouch(View view, MotionEvent e) {
                if (selectStatus.get(right_ll_tuya)) {
                    drawTY(e);
                    return true;
                } else {
                    return super.onTouch(view, e);
                }
            }
        });
    }

    /**
     * 涂鸦因为手势过快问题 故使用点线模式
     *
     * @param e
     */
    private void drawTY(MotionEvent e) {
        Point point = mMapView.screenToLocation(new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY())));
        point = (Point) GeometryEngine.project(point, SpatialReference.create(wkt));
        mPointCollection.add(point);
        Polyline polyline = new Polyline(mPointCollection);
        //点
        SimpleMarkerSymbol simpleMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);
        Graphic pointGraphic = new Graphic(point, simpleMarkerSymbol);
        mGraphicsOverlay.getGraphics().add(pointGraphic);
        //线
        SimpleLineSymbol simpleLineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 10);
        Graphic graphic = new Graphic(polyline, simpleLineSymbol);
        mGraphicsOverlay.getGraphics().add(graphic);
        if (e.getAction() == MotionEvent.ACTION_UP) {
            mPointCollection.clear();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_cj: {
//                dl_ll.openDrawer(Gravity.END);
                showBottomDialog();
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
                cleanTab(true);
                break;
            }
       /*     case R.id.right_ll_marker: {
                selectItem(right_ll_marker);
                break;
            }
            case R.id.right_ll_line: {
                selectItem(right_ll_line);
                break;
            }
            case R.id.right_ll_mian: {
                selectItem(right_ll_mian);
                break;
            }
            case R.id.right_ll_rou: {
                selectItem(right_ll_rou);
                break;
            }
            case R.id.right_ll_tuya: {
                selectItem(right_ll_tuya);
                break;
            }
            case R.id.right_ll_clean: {
                cleanTab(false);
                dimissRight();
                break;
            }
            case R.id.right_ll_out: {
//                selectItem(right_ll_out);
                dl_ll.closeDrawer(Gravity.END);
                toOut();
                break;
            }*/
            case R.id.open_fd: {
                double scale = mMapView.getMapScale();
                mMapView.setViewpointScaleAsync(scale * 0.5);
                break;
            }
            case R.id.open_sx: {
                double scale = mMapView.getMapScale();
                mMapView.setViewpointScaleAsync(scale * 2);
                break;
            }
            case R.id.open_xz: {
                mMapView.setViewpointRotationAsync(mMapView.getMapRotation() + 90);
                break;
            }
        }
    }

    private void toOut() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean value = PermissionUtility.writeExternalStorage(this);
            if (!value) {
                return;
            }
        }
        Bitmap bitmap = MapUtil.getMapViewBitmap(mMapView);
        if (bitmap == null) {
            ToastUtility.showToast("导出失败");
            return;
        }

        String path = LocImageUtility.saveBitmap(getContext(), bitmap);
        LocImageUtility.NotifyPhonePicture(getContext(), path);

        BitmapDialog bitmapDialog = new BitmapDialog();
        bitmapDialog.setBitmap(bitmap);
        bitmapDialog.show(getSupportFragmentManager(), "d");
    }

    private void selectItem(int v) {
        mPointCollection.clear();
        cleanMap();
        initSelectStatus(v);
        dimissRight();
    }

    private void showDistance() {
        cleanMap();
        initSelectStatus(right_ll_distance_key);
        bottom_table_distance.setVisibility(View.VISIBLE);
        bottom_table_modelname.setText("距离测量模式");
        bottom_table_distance_value.setText("0.00");
        bottom_tab_value_d.setText("米");
    }

    private void showArea() {
        cleanMap();
        initSelectStatus(right_ll_area_key);
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
    private void cleanTab(boolean cleanStatus) {
        cleanMap();
//        isArse = false;
//        isLine = false;
        if (cleanStatus) {//是否清理选择
            initSelectStatus(-1);
        }
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

    //画点
    private void drawMarker(MotionEvent e) {
        Point point = mMapView.screenToLocation(new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY())));
        point = (Point) GeometryEngine.project(point, SpatialReference.create(wkt));
        mPointCollection.add(point);
        SimpleMarkerSymbol simpleMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);
        Graphic pointGraphic = new Graphic(point, simpleMarkerSymbol);
        mGraphicsOverlay.getGraphics().add(pointGraphic);
    }

    private List<Point> mPointList = new ArrayList<>();


    private void drawCircle(MotionEvent e) {
        double radius = 0;
        Point point = mMapView.screenToLocation(new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY())));
        point = (Point) GeometryEngine.project(point, SpatialReference.create(wkt));
        mPointList.add(point);
        if (mPointList.size() == 2) {
            double x = (mPointList.get(1).getX() - mPointList.get(0).getX());
            double y = (mPointList.get(1).getY() - mPointList.get(0).getY());
            radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        }
        getCircle(mPointList.get(0), radius);
    }

    private void getCircle(Point point, double radius) {
        //        polygon.setEmpty();
        Point[] points = getPoints(point, radius);
        mPointCollection.clear();
        for (Point p : points) {
            mPointCollection.add(p);
        }

        Polygon polygon = new Polygon(mPointCollection);

        SimpleMarkerSymbol simpleMarkerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 10);
        Graphic pointGraphic = new Graphic(point, simpleMarkerSymbol);
        mGraphicsOverlay.getGraphics().add(pointGraphic);

        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.parseColor("#FC8145"), 3.0f);
        SimpleFillSymbol simpleFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.parseColor("#33e97676"), lineSymbol);

        Graphic graphic = new Graphic(polygon, simpleFillSymbol);
        if (mPointList.size() == 2) {
            mPointList.clear();
        }
        mGraphicsOverlay.getGraphics().add(graphic);
    }


    /**
     * 通过中心点和半径计算得出圆形的边线点集合
     *
     * @param center
     * @param radius
     * @return
     */
    private static Point[] getPoints(Point center, double radius) {
        Point[] points = new Point[50];
        double sin;
        double cos;
        double x;
        double y;
        for (double i = 0; i < 50; i++) {
            sin = Math.sin(Math.PI * 2 * i / 50);
            cos = Math.cos(Math.PI * 2 * i / 50);
            x = center.getX() + radius * sin;
            y = center.getY() + radius * cos;
            points[(int) i] = new Point(x, y);
        }
        return points;
    }


    /**
     * @param e
     * @param isyx 是否进行运算
     */
    private void drawPoint(MotionEvent e, boolean isyx) {
        Point point = mMapView.screenToLocation(new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY())));
        point = (Point) GeometryEngine.project(point, SpatialReference.create(wkt));
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
        if (isyx) {
            mPresenter.todistance(mPointCollection);
        }
    }


    private void drawPolygon(MotionEvent e, boolean isyx) {
        mGraphicsOverlay.getGraphics().clear();
        Point point = mMapView.screenToLocation(new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY())));
        point = (Point) GeometryEngine.project(point, SpatialReference.create(wkt));
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
        if (isyx) {
            mPresenter.toArea(mPointCollection);
        }
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
    private QMUIBottomSheet s;
    private void showBottomDialog() {
        if (s==null){
            QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(getContext());


            builder.addItem(R.mipmap.d, "画点", right_ll_marker, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
            builder.addItem(R.mipmap.x, "画线", right_ll_line, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
            builder.addItem(R.mipmap.area, "画面", right_ll_mian, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
            builder.addItem(R.mipmap.y, "画圆", right_ll_rou, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
            builder.addItem(R.mipmap.ty, "涂鸦", right_ll_tuya, QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);

            builder.addItem(R.mipmap.clone, "清除", right_ll_clean, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE);
            builder.addItem(R.mipmap.dc, "导出", right_ll_out, QMUIBottomSheet.BottomGridSheetBuilder.SECOND_LINE);

            s = builder.setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
                @Override
                public void onClick(QMUIBottomSheet dialog, View itemView) {
                    int key = (int) itemView.getTag();
                    switch (key) {
                        case right_ll_marker: {
                            cleanTab(false);

                            selectItem(right_ll_marker);
                            break;
                        }
                        case right_ll_line: {
                            cleanTab(false);

                            selectItem(right_ll_line);
                            break;
                        }
                        case right_ll_mian: {
                            cleanTab(false);

                            selectItem(right_ll_mian);
                            break;
                        }
                        case right_ll_rou: {
                            cleanTab(false);

                            selectItem(right_ll_rou);
                            break;
                        }
                        case right_ll_tuya: {
                            cleanTab(false);

                            selectItem(right_ll_tuya);
                            break;
                        }
                        case right_ll_clean: {
                            cleanTab(false);
                            break;
                        }
                        case right_ll_out: {
                            dl_ll.closeDrawer(Gravity.END);
                            toOut();
                            break;
                        }

                    }
                    dialog.dismiss();
                }
            }).build();

        }
         s.show();
    }

}
