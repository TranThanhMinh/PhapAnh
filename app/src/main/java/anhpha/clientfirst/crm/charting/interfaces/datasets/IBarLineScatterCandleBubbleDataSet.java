package anhpha.clientfirst.crm.charting.interfaces.datasets;

import anhpha.clientfirst.crm.charting.data.Entry;

/**
 * Created by philipp on 21/10/15.
 */
public interface IBarLineScatterCandleBubbleDataSet<T extends Entry> extends anhpha.clientfirst.crm.charting.interfaces.datasets.IDataSet<T> {

    /**
     * Returns the color that is used for drawing the highlight indicators.
     *
     * @return
     */
    int getHighLightColor();
}
