package com.george;

import com.bbn.openmap.MapBean;
import com.bbn.openmap.app.OpenMap;
import com.bbn.openmap.layer.rpf.RpfLayer;
import com.bbn.openmap.proj.Projection;

public class MapViewer extends OpenMap{
     public MapViewer() {
        super();
    }
/*
    @Override
    protected MapBean createMapModel() {
        MapBean mapBean = new MapBean(Projection.defProjectionName);
        mapBean.setUlx(-180.0f);
        mapBean.setUly(90.0f);
        mapBean.setLrx(180.0f);
        mapBean.setLry(-90.0f);

        RpfLayer rpfLayer = new RpfLayer();
        rpfLayer.setAddAsBackground(true);
        rpfLayer.setAddPolicy(ListResetUnredoContextInterface.ADD_POLICY_COPY);
        rpfLayer.setResourceLocation("/resources/rpf");
        mapBean.addLayer(rpfLayer);

        return mapBean;
    }

    public static void main(String[] args) {
        MapViewer viewer = new MapViewer();
        viewer.setVisible(true);
    }
    */
}
