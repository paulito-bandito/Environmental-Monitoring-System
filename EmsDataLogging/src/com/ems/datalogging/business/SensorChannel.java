package com.ems.datalogging.business;

import java.util.ArrayList;

/**
 *
 * @author Paul W Walter
 */
public class SensorChannel {
    
    private long sensorId;
    private int channelNum;
    private ArrayList<SensorRange> sensorRanges;
    private SensorMetricType sensorMetricType;
    public SensorTypeTO sensorChannelTypeTO;

    public int getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(int channelNum) {
        this.channelNum = channelNum;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public SensorMetricType getSensorMetricType() {
        return sensorMetricType;
    }

    public void setSensorMetricType(SensorMetricType sensorMetricType) {
        this.sensorMetricType = sensorMetricType;
    }

    public ArrayList<SensorRange> getSensorRanges() {
        return sensorRanges;
    }

    public void setSensorRanges(ArrayList<SensorRange> sensorRanges) {
        this.sensorRanges = sensorRanges;
    }
   
    public SensorChannel(long aSensorId, int aChannelNum, SensorMetricType aSensorMetricType, ArrayList<SensorRange>aSensorRanges, SensorTypeTO aSensorChannelTypeTo )
    {
        this.sensorId = aSensorId;
        this.channelNum = aChannelNum;
        this.sensorMetricType = aSensorMetricType;
        this.sensorRanges = aSensorRanges;
        this.sensorChannelTypeTO = aSensorChannelTypeTo;
    }
}
