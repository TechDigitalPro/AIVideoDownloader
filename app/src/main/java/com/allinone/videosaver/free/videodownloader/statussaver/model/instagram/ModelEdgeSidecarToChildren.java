package com.allinone.videosaver.free.videodownloader.statussaver.model.instagram;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModelEdgeSidecarToChildren implements Serializable {

    @SerializedName("edges")
    private List<ModelEdge> modelEdges;

    public List<ModelEdge> getModelEdges() {
        return modelEdges;
    }

    public void setModelEdges(List<ModelEdge> modelEdges) {
        this.modelEdges = modelEdges;
    }
}
