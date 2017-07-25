
package anhpha.clientfirst.crm.charting.data;

import android.util.Log;

import anhpha.clientfirst.crm.charting.highlight.Highlight;
import anhpha.clientfirst.crm.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Data object that allows the combination of Line-, Bar-, Scatter-, Bubble- and
 * CandleData. Used in the CombinedChart class.
 *
 * @author Philipp Jahoda
 */
public class CombinedData extends BarLineScatterCandleBubbleData<IBarLineScatterCandleBubbleDataSet<? extends anhpha.clientfirst.crm.charting.data.Entry>> {

    private anhpha.clientfirst.crm.charting.data.LineData mLineData;
    private anhpha.clientfirst.crm.charting.data.BarData mBarData;
    private anhpha.clientfirst.crm.charting.data.ScatterData mScatterData;
    private anhpha.clientfirst.crm.charting.data.CandleData mCandleData;
    private anhpha.clientfirst.crm.charting.data.BubbleData mBubbleData;

    public CombinedData() {
        super();
    }

    public void setData(anhpha.clientfirst.crm.charting.data.LineData data) {
        mLineData = data;
        notifyDataChanged();
    }

    public void setData(anhpha.clientfirst.crm.charting.data.BarData data) {
        mBarData = data;
        notifyDataChanged();
    }

    public void setData(anhpha.clientfirst.crm.charting.data.ScatterData data) {
        mScatterData = data;
        notifyDataChanged();
    }

    public void setData(anhpha.clientfirst.crm.charting.data.CandleData data) {
        mCandleData = data;
        notifyDataChanged();
    }

    public void setData(anhpha.clientfirst.crm.charting.data.BubbleData data) {
        mBubbleData = data;
        notifyDataChanged();
    }

    @Override
    public void calcMinMax() {

        if(mDataSets == null){
            mDataSets = new ArrayList<>();
        }
        mDataSets.clear();

        mYMax = -Float.MAX_VALUE;
        mYMin = Float.MAX_VALUE;
        mXMax = -Float.MAX_VALUE;
        mXMin = Float.MAX_VALUE;

        mLeftAxisMax = -Float.MAX_VALUE;
        mLeftAxisMin = Float.MAX_VALUE;
        mRightAxisMax = -Float.MAX_VALUE;
        mRightAxisMin = Float.MAX_VALUE;

        List<BarLineScatterCandleBubbleData> allData = getAllData();

        for (anhpha.clientfirst.crm.charting.data.ChartData data : allData) {

            data.calcMinMax();

            List<IBarLineScatterCandleBubbleDataSet<? extends anhpha.clientfirst.crm.charting.data.Entry>> sets = data.getDataSets();
            mDataSets.addAll(sets);

            if (data.getYMax() > mYMax)
                mYMax = data.getYMax();

            if (data.getYMin() < mYMin)
                mYMin = data.getYMin();

            if (data.getXMax() > mXMax)
                mXMax = data.getXMax();

            if (data.getXMin() < mXMin)
                mXMin = data.getXMin();

            if (data.mLeftAxisMax > mLeftAxisMax)
                mLeftAxisMax = data.mLeftAxisMax;

            if (data.mLeftAxisMin < mLeftAxisMin)
                mLeftAxisMin = data.mLeftAxisMin;

            if (data.mRightAxisMax > mRightAxisMax)
                mRightAxisMax = data.mRightAxisMax;

            if (data.mRightAxisMin < mRightAxisMin)
                mRightAxisMin = data.mRightAxisMin;

        }
    }

    public anhpha.clientfirst.crm.charting.data.BubbleData getBubbleData() {
        return mBubbleData;
    }

    public LineData getLineData() {
        return mLineData;
    }

    public BarData getBarData() {
        return mBarData;
    }

    public ScatterData getScatterData() {
        return mScatterData;
    }

    public CandleData getCandleData() {
        return mCandleData;
    }

    /**
     * Returns all data objects in row: line-bar-scatter-candle-bubble if not null.
     *
     * @return
     */
    public List<BarLineScatterCandleBubbleData> getAllData() {

        List<BarLineScatterCandleBubbleData> data = new ArrayList<BarLineScatterCandleBubbleData>();
        if (mLineData != null)
            data.add(mLineData);
        if (mBarData != null)
            data.add(mBarData);
        if (mScatterData != null)
            data.add(mScatterData);
        if (mCandleData != null)
            data.add(mCandleData);
        if (mBubbleData != null)
            data.add(mBubbleData);

        return data;
    }

    public BarLineScatterCandleBubbleData getDataByIndex(int index) {
        return getAllData().get(index);
    }

    @Override
    public void notifyDataChanged() {
        if (mLineData != null)
            mLineData.notifyDataChanged();
        if (mBarData != null)
            mBarData.notifyDataChanged();
        if (mCandleData != null)
            mCandleData.notifyDataChanged();
        if (mScatterData != null)
            mScatterData.notifyDataChanged();
        if (mBubbleData != null)
            mBubbleData.notifyDataChanged();

        calcMinMax(); // recalculate everything
    }

    /**
     * Get the Entry for a corresponding highlight object
     *
     * @param highlight
     * @return the entry that is highlighted
     */
    @Override
    public anhpha.clientfirst.crm.charting.data.Entry getEntryForHighlight(Highlight highlight) {

        List<BarLineScatterCandleBubbleData> dataObjects = getAllData();

        if (highlight.getDataIndex() >= dataObjects.size())
            return null;

        anhpha.clientfirst.crm.charting.data.ChartData data = dataObjects.get(highlight.getDataIndex());

        if (highlight.getDataSetIndex() >= data.getDataSetCount())
            return null;
        else {
            // The value of the highlighted entry could be NaN -
            //   if we are not interested in highlighting a specific value.

            List<anhpha.clientfirst.crm.charting.data.Entry> entries = data.getDataSetByIndex(highlight.getDataSetIndex())
                    .getEntriesForXValue(highlight.getX());
            for (anhpha.clientfirst.crm.charting.data.Entry entry : entries)
                if (entry.getY() == highlight.getY() ||
                        Float.isNaN(highlight.getY()))
                    return entry;

            return null;
        }
    }

    public int getDataIndex(anhpha.clientfirst.crm.charting.data.ChartData data) {
        return getAllData().indexOf(data);
    }

    @Override
    public boolean removeDataSet(IBarLineScatterCandleBubbleDataSet<? extends anhpha.clientfirst.crm.charting.data.Entry> d) {

        List<BarLineScatterCandleBubbleData> datas = getAllData();

        boolean success = false;

        for (ChartData data : datas) {

            success = data.removeDataSet(d);

            if (success) {
                break;
            }
        }

        return success;
    }

    @Deprecated
    @Override
    public boolean removeDataSet(int index) {
        Log.e("MPAndroidChart", "removeDataSet(int index) not supported for CombinedData");
        return false;
    }

    @Deprecated
    @Override
    public boolean removeEntry(Entry e, int dataSetIndex) {
        Log.e("MPAndroidChart", "removeEntry(...) not supported for CombinedData");
        return false;
    }

    @Deprecated
    @Override
    public boolean removeEntry(float xValue, int dataSetIndex) {
        Log.e("MPAndroidChart", "removeEntry(...) not supported for CombinedData");
        return false;
    }
}
