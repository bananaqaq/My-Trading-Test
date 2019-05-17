package newbbb.matchengine;

import newbbb.model.me.Market;
import newbbb.model.me.TradingMarket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
@org.springframework.core.annotation.Order(6)
public class METrade {

    private static HashMap<String, TradingMarket> markets;

    @Autowired
    private MEConfig meConfig;

    @Autowired
    private MEMarket meMarket;

    @PostConstruct
    private void InitTrade(){
        markets = new HashMap<>();
        for(Market market : meConfig.settings().getMarkets()){
            TradingMarket tm = meMarket.MarketCreate(market);
            markets.put(tm.getName(), tm);
        }
    }

    public TradingMarket getMarket(String name){
        return markets.get(name);
    }

}
