package anhpha.clientfirst.crm.charting.interfaces.dataprovider;

import anhpha.clientfirst.crm.charting.data.CombinedData;

/**
 * Created by philipp on 11/06/16.
 */
public interface CombinedDataProvider extends anhpha.clientfirst.crm.charting.interfaces.dataprovider.LineDataProvider, BarDataProvider, BubbleDataProvider, CandleDataProvider, ScatterDataProvider {

    CombinedData getCombinedData();
}
