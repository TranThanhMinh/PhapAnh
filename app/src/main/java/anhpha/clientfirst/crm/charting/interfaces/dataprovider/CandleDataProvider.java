package anhpha.clientfirst.crm.charting.interfaces.dataprovider;

import anhpha.clientfirst.crm.charting.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
